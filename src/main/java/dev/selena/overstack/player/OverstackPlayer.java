package dev.selena.overstack.player;

import org.bukkit.Location;

public class OverstackPlayer {

    private Location buttonLocation;
    private Location leverLocation;
    private int redstoneTicks;


    public OverstackPlayer() {
        buttonLocation = null;
        leverLocation = null;
        redstoneTicks = 0;
    }

    public Location getButtonLocation() {
        return buttonLocation;
    }

    public Location getLeverLocation() {
        return leverLocation;
    }

    public void setButtonLocation(Location buttonLocation) {
        this.buttonLocation = buttonLocation;
    }

    public void setLeverLocation(Location leverLocation) {
        this.leverLocation = leverLocation;
    }

    public int getRedstoneTicks() {
        return redstoneTicks;
    }

    public int setRedstoneTicks(int redstoneTicks) {
        this.redstoneTicks = Math.max(redstoneTicks, 0);
        return redstoneTicks;
    }

    public  int addRedStoneTicks(int amount) {
        return setRedstoneTicks(getRedstoneTicks() + amount);
    }
}
