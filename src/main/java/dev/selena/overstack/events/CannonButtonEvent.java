package dev.selena.overstack.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class CannonButtonEvent extends Event {

    private final Location location;
    private final UUID firerUUID;
    private final boolean physical;

    public CannonButtonEvent(Location levelLoc, UUID uuid, boolean physical) {
        location = levelLoc;
        firerUUID = uuid;
        this.physical = physical;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getFirerUUID() {
        return firerUUID;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(firerUUID);
    }

    public boolean isPhysical() {
        return physical;
    }

    private static final HandlerList handlerList = new HandlerList();
    public static HandlerList getHandlerList() {
        return handlerList;
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
