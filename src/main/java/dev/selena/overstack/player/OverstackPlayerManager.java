package dev.selena.overstack.player;

import java.util.TreeMap;
import java.util.UUID;

public class OverstackPlayerManager {

    private TreeMap<UUID, OverstackPlayer> players;

    private static OverstackPlayerManager inst;

    public OverstackPlayerManager() {
        players = new TreeMap<>();
        inst = this;
    }

    public static OverstackPlayerManager get() {
        return inst;
    }

    public static OverstackPlayer getOPlayer(UUID uuid) {
        if (!get().players.containsKey(uuid))
            addPlayer(uuid, new OverstackPlayer());
        return get().players.get(uuid);
    }

    public static void addPlayer(UUID uuid, OverstackPlayer player) {
        get().players.put(uuid, player);
    }



}
