package dev.selena.overstack.config;

import dev.selena.overstack.features.IFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SandRemover implements IFeature {


    public boolean Enabled = true;
    public String ItemType = Material.BONE.name();
    public int CastDistance = 50;
    public List<String> ReplaceBlocks = new ArrayList<String>() {{
       add(Material.SAND.name());
       add(Material.GRAVEL.name());
    }};

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

    public static SandRemover get() {
        return Configs.SAND_REMOVER.getConfig();
    }

    public void replaceBlocks(Location loc, Material replace) {
        for (int y = 256; y > 0; y--) {
            Block blockAtLoc = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ());
            if (ReplaceBlocks.contains(blockAtLoc.getType().name()))
                blockAtLoc.setType(replace);
        }
    }
}
