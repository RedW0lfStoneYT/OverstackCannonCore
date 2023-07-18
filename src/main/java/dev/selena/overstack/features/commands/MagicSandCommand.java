package dev.selena.overstack.features.commands;

import dev.selena.commands.Arguments;
import dev.selena.commands.Command;
import dev.selena.commands.GlitchCommand;
import dev.selena.overstack.config.MagicSand;

public class MagicSandCommand extends GlitchCommand {

    @Command(name = "magicsand", playerOnly = true)
    public void give(Arguments args) {
        args.getPlayer().getInventory().addItem(MagicSand.get().getItem());
    }

}
