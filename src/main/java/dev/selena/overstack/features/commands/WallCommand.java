package dev.selena.overstack.features.commands;

import dev.selena.commands.Arguments;
import dev.selena.commands.Command;
import dev.selena.commands.GlitchCommand;
import dev.selena.overstack.config.Walls;

public class WallCommand extends GlitchCommand {

    @Command(name = "wall", aliases = {"walls"}, playerOnly = true)
    public void giveItem(Arguments args) {
        args.getPlayer().setItemInHand(Walls.get().walls.get(0).getItem());
    }

}
