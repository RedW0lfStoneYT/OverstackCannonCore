package dev.selena.overstack.features;

import dev.selena.overstack.CannonCore;
import dev.selena.overstack.config.Configs;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FeatureLoader {


    public List<Location> sandLocations;
    public static FeatureLoader instance;

    public FeatureLoader() {
        load();
        instance = this;
        sandLocations = new ArrayList<>();
        CannonCore.get().getServer().getPluginManager().registerEvents(new FeatureListeners(), CannonCore.get());

    }

    public void load() {

        EnumSet.allOf(Configs.class).forEach(configFiles -> {
            Object config = configFiles.getConfig();
            if (config instanceof IFeature) {
                IFeature feature = (IFeature) config;
                if (feature.isEnabled())
                    feature.loadCommands();
            }

        });
        runnable().runTaskTimerAsynchronously(CannonCore.get(), 0, 1);
    }

    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                EnumSet.allOf(Configs.class).forEach(config -> {
                    if (config.getConfig() instanceof IFeature) {
                        IFeature feature = config.getConfig();
                        feature.run();
                    }
                });
            }
        };
    }

    public static List<Location> getSandLocations() {
        return get().sandLocations;
    }



    public static FeatureLoader get() {
        return instance;
    }
}
