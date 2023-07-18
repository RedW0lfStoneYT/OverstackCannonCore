package dev.selena.overstack.config;

import dev.selena.items.ItemMapperBuilder;
import dev.selena.overstack.CannonCore;
import dev.selena.overstack.Constants;
import dev.selena.overstack.features.FeatureListeners;
import dev.selena.overstack.features.FeatureLoader;
import dev.selena.overstack.features.IFeature;
import dev.selena.overstack.features.commands.MagicSandCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MagicSand implements IFeature {

    public boolean Enabled = true;
    public String Block_Type = Material.SANDSTONE.name();
    public String Name = "&dMagic Sand";
    public boolean Glowing = true;
    public List<String> Replace = new ArrayList<String>() {{
        add(Material.WATER.name());
        add(Material.STATIONARY_WATER.name());
        add(Material.AIR.name());
    }};

    public static MagicSand get() {
        return Configs.MAGIC_SAND.getConfig();
    }


    public ItemStack getItem() {
        return new ItemMapperBuilder().itemName(Name).itemStack(Block_Type).glowing(Glowing)
                .addNBTBoolean(Constants.MAGIC_SAND, true).build().createItemStack();
    }

    @Override
    public void run() {
        if (!Enabled)
            return;
        List<Location> locations = FeatureLoader.getSandLocations();
        if (locations.isEmpty())
            return;
        locations.forEach(loc -> {
            Block blockBelow = loc.getBlock().getRelative(0, -1, 0);


            if (Replace.contains(blockBelow.getType().name()))
                Bukkit.getScheduler().runTask(CannonCore.get(), () -> blockBelow.setType(Material.SAND));
        });

    }

    @Override
    public boolean isEnabled() {
        return Enabled;
    }

    @Override
    public void loadCommands() {
        new MagicSandCommand();
    }

    public static void addSandLocation(Location loc) {
        FeatureLoader.getSandLocations().add(loc);
    }

    public static void removeSandLocation(Location loc) {
        FeatureLoader.getSandLocations().remove(loc);
    }

}
