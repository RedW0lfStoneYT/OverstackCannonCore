package dev.selena.overstack.config;

import dev.selena.overstack.features.IFeature;
import dev.selena.overstack.features.commands.FireCommands;

public class Fire implements IFeature {

    public boolean Enabled = true;


    public Fire get() {
        return Configs.FIRE.getConfig();
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
        new FireCommands();
    }
}
