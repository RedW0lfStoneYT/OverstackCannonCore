package dev.selena.overstack.features.commands;

import dev.selena.commands.Arguments;
import dev.selena.commands.Command;
import dev.selena.commands.GlitchCommand;
import dev.selena.overstack.CannonCore;
import dev.selena.overstack.config.Lang;
import dev.selena.overstack.events.CannonButtonEvent;
import dev.selena.overstack.events.CannonLeverEvent;
import dev.selena.overstack.player.OverstackPlayer;
import dev.selena.overstack.player.OverstackPlayerManager;
import dev.selena.text.MessageUtils;
import org.bukkit.entity.Player;

public class FireCommands extends GlitchCommand {

    @Command(name = "fire", playerOnly = true)
    public void fire(Arguments args) {
        Player player = args.getPlayer();
        System.out.println("TEST");
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        if (oPlayer == null)
            return;
        System.out.println("TEST1");
        if (oPlayer.getButtonLocation() == null) {
            MessageUtils.playerSend(player, Lang.get().NOT_PRESSED_BUTTON);
            return;

        }
        System.out.println("TEST2");
        CannonCore.get().getServer().getPluginManager().callEvent(new CannonButtonEvent(oPlayer.getButtonLocation(), player.getUniqueId(), false));
    }

    @Command(name = "flick", playerOnly = true)
    public void flick(Arguments args) {
        Player player = args.getPlayer();
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        if (oPlayer == null)
            return;
        if (oPlayer.getLeverLocation() == null) {
            MessageUtils.playerSend(player, Lang.get().NOT_FLICKED_LEVER);
            return;
        }
        CannonCore.get().getServer().getPluginManager().callEvent(new CannonLeverEvent(oPlayer.getLeverLocation(), player.getUniqueId(), false));
    }
}
