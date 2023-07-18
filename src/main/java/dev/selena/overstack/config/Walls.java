package dev.selena.overstack.config;

import dev.selena.items.ItemMapperBuilder;
import dev.selena.overstack.Constants;
import dev.selena.overstack.features.IFeature;
import dev.selena.overstack.features.commands.WallCommand;
import dev.selena.text.ContentUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Walls implements IFeature {


    public boolean Enabled = true;

    public String Name = "&b%type% Wall";
    public String[] Lore = {
            "&d Drop to switch wall type"
    };

    public List<WallBlock> walls = new ArrayList<WallBlock>() {{
        add(new WallBlock(Material.OBSIDIAN));
        add(new WallBlock(Material.COBBLESTONE));
        add(new WallBlock(Material.SAND));
        add(new WallBlock(Material.STONE, Material.LAVA, 255, false));
    }};


    public static class WallBlock {
        public String ItemType;
        public String BlockType;
        public int MaxHeight;
        public boolean WaterAtTop;

        public WallBlock(Material item, Material block, int height, boolean water) {
            ItemType = item.name();
            BlockType = block.name();
            MaxHeight = height;
            WaterAtTop = water;
        }

        public WallBlock(Material type) {
            this(type, type, 254, true);
        }

        public ItemStack getItem() {
            return new ItemMapperBuilder().itemStack(ItemType).loreLines(Walls.get().Lore).addNBTString(Constants.WALL_TYPE, BlockType)
                    .itemName(get().Name.replace("%type%", ContentUtils.capsFirst(BlockType.toLowerCase()))).build().createItemStack();
        }

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
        new WallCommand();
    }

    public static Walls get() {
        return Configs.WALLS.getConfig();
    }

    public static WallBlock getWall(String type) {
        for (Walls.WallBlock wall : get().walls) {
            if (wall.ItemType.equalsIgnoreCase(type))
                return wall;
        }
        return null;
    }
}
