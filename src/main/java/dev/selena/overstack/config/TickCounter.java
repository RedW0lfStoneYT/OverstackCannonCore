package dev.selena.overstack.config;

import dev.selena.overstack.features.IFeature;
import org.bukkit.Material;

public class TickCounter implements IFeature {

    public boolean Enabled = true;
    public String Item = Material.BLAZE_ROD.name();


    public static TickCounter get() {
        return Configs.TICK_COUNTER.getConfig();
    }
    @Override
    public void run() {

    }

    @Override
    public boolean isEnabled() {
        return Enabled;
    }

    @Override
    public void loadCommands() {

    }
}
