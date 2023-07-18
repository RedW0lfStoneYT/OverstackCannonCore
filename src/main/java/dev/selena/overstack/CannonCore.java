package dev.selena.overstack;

import dev.selena.GlitchAPI;
import dev.selena.overstack.config.Configs;
import dev.selena.overstack.features.FeatureLoader;
import dev.selena.overstack.player.OverstackPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;


public class CannonCore extends JavaPlugin {

    private static CannonCore instance;


    @Override
    public void onEnable() {
        GlitchAPI.setPlugin(this);
        instance = this;
        new FeatureLoader();
        new OverstackPlayerManager();
    }

    public static CannonCore get() {
        return instance;
    }
}


