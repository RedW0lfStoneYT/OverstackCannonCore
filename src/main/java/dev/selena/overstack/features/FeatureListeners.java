package dev.selena.overstack.features;

import dev.selena.libs.nbtapi.NBTItem;
import dev.selena.overstack.CannonCore;
import dev.selena.overstack.Constants;
import dev.selena.overstack.config.*;
import dev.selena.overstack.events.CannonButtonEvent;
import dev.selena.overstack.events.CannonLeverEvent;
import dev.selena.overstack.player.OverstackPlayer;
import dev.selena.overstack.player.OverstackPlayerManager;
import dev.selena.text.ContentUtils;
import dev.selena.text.MessageUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Diode;

import java.util.HashSet;
import java.util.List;

public class FeatureListeners implements Listener {

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item == null || item.getType() == Material.AIR)
            return;
        NBTItem nItem = new NBTItem(item);
        if (!nItem.hasCustomNbtData() || !nItem.getBoolean(Constants.MAGIC_SAND))
            return;
        MagicSand.addSandLocation(event.getBlock().getLocation());

    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        if (!FeatureLoader.getSandLocations().contains(loc))
            return;
        MagicSand.removeSandLocation(loc);
        for (int i = loc.getBlockY() - 1; i > 0; i--) {
            Block iBlock = loc.getWorld().getBlockAt(loc.getBlockX(), i, loc.getBlockZ());
            if (iBlock.getType() == Material.SAND)
                iBlock.setType(Material.AIR);
            else break;
        }
    }

    @EventHandler
    public void sandRemover(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (event.getItem().getType() != Material.matchMaterial(SandRemover.get().ItemType))
            return;
        Block lookingAt = event.getPlayer().getTargetBlock(new HashSet<Material>(){{
            add(Material.WATER);
            add(Material.LAVA);
            add(Material.STATIONARY_LAVA);
            add(Material.STATIONARY_WATER);
            add(Material.AIR);
        }}, SandRemover.get().CastDistance);
        if (!SandRemover.get().ReplaceBlocks.contains(lookingAt.getType().name()))
            return;
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            SandRemover.get().replaceBlocks(lookingAt.getLocation(), Material.WATER);
        else
            SandRemover.get().replaceBlocks(lookingAt.getLocation(), Material.AIR);
    }

    @EventHandler
    public void itemDrop(ItemSpawnEvent event) {
        event.getEntity().remove();
        event.setCancelled(true);
    }

    @EventHandler
    public void switchWall(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        if (dropped == null)
            return;
        NBTItem nItem = new NBTItem(dropped);
        if (!nItem.hasCustomNbtData())
            return;
        if (!nItem.hasTag(Constants.WALL_TYPE))
            return;
        int index = 0;
        for (Walls.WallBlock wall : Walls.get().walls) {
            if (wall.ItemType.equalsIgnoreCase(dropped.getType().name()))
                break;
            index++;
        }
        index++;
        List<Walls.WallBlock> walls = Walls.get().walls;
        event.getPlayer().setItemInHand(walls.get(index >= walls.size() ? 0 : index).getItem());
    }

    @EventHandler
    public void placeWall(BlockPlaceEvent event) {
        if (!Walls.get().Enabled)
            return;
        if (event.getItemInHand() == null)
            return;
        NBTItem nItem = new NBTItem(event.getItemInHand());
        if (!nItem.hasCustomNbtData())
            return;
        if (!nItem.hasTag(Constants.WALL_TYPE))
            return;
        Material wallBlock = Material.matchMaterial(nItem.getString(Constants.WALL_TYPE));
        if (wallBlock == null)
            return;
        Location placeLocation = event.getBlockPlaced().getLocation();
        Walls.WallBlock wall = Walls.getWall(event.getBlockPlaced().getType().name());
        if (wall == null)
            return;
        for (int y = wall.MaxHeight; y > 0; y--) {
            placeLocation.getWorld().getBlockAt(placeLocation.getBlockX(), y, placeLocation.getBlockZ()).setType(wallBlock);
        }
        if (wall.WaterAtTop)
            placeLocation.getWorld().getBlockAt(placeLocation.getBlockX(), wall.MaxHeight + 1, placeLocation.getBlockZ()).setType(Material.WATER);
    }

    @EventHandler
    public void buttonPress(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clicked = event.getClickedBlock();
            switch (clicked.getType()) {
                case STONE_BUTTON:
                case WOOD_BUTTON:
                    CannonCore.get().getServer().getPluginManager().callEvent(new CannonButtonEvent(clicked.getLocation(), player.getUniqueId(), true));
                    break;
                case LEVER:
                    CannonCore.get().getServer().getPluginManager().callEvent(new CannonLeverEvent(clicked.getLocation(), player.getUniqueId(), true));
                    break;
                default:
                    break;
            }

        }
    }

    @EventHandler
    public void cannonButton(CannonButtonEvent event) {
        Location loc = event.getLocation();
        Player player = event.getPlayer();
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        if (oPlayer == null)
            return;
        if (oPlayer.getButtonLocation() == null || oPlayer.getButtonLocation() != loc)
            oPlayer.setButtonLocation(loc);

        Block block = event.getLocation().getBlock();
        World world = ((CraftWorld) event.getLocation().getWorld()).getHandle();
        BlockPosition blockposition = new BlockPosition(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ());
        IBlockData iblockdata = world.getType(blockposition);
        net.minecraft.server.v1_8_R3.Block minecraftBlock = iblockdata.getBlock();

        if (block.getType() != Material.STONE_BUTTON && block.getType() != Material.WOOD_BUTTON && !event.isPhysical()) {
            MessageUtils.playerSend(player, Lang.get().NO_BUTTON);
        }

        if (!event.isPhysical()) {
            BlockButtonAbstract button = (BlockButtonAbstract) minecraftBlock;
            button.interact(world, blockposition, iblockdata, null, null, 0.0F, 0.0F, 0.0F);
            MessageUtils.playerSend(player, Lang.get().FIRED);
        }

    }

    @EventHandler
    public void cannonLever(CannonLeverEvent event) {
        Location loc = event.getLocation();
        Player player = event.getPlayer();
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        if (oPlayer == null)
            return;
        if (oPlayer.getLeverLocation() == null || oPlayer.getLeverLocation() != loc)
            oPlayer.setLeverLocation(loc);

        Block block = event.getLocation().getBlock();
        World world = ((CraftWorld) event.getLocation().getWorld()).getHandle();
        BlockPosition blockposition = new BlockPosition(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ());
        IBlockData iblockdata = world.getType(blockposition);
        net.minecraft.server.v1_8_R3.Block minecraftBlock = iblockdata.getBlock();

        if (block.getType() != Material.LEVER && !event.isPhysical()) {
            MessageUtils.playerSend(player, Lang.get().NO_LEVER);
            return;
        }

        if (!event.isPhysical()) {
            BlockLever lever = (BlockLever) minecraftBlock;
            lever.interact(world, blockposition, iblockdata, null, null, 0.0F, 0.0F, 0.0F);
            MessageUtils.playerSend(player, Lang.get().FLICKED);
        }

    }

    @EventHandler
    public void tickCountEventCaller(PlayerInteractEvent event) {

        if (event.getPlayer().getItemInHand() == null) return;
        if (event.getPlayer().getItemInHand().getType() != Material.matchMaterial(TickCounter.get().Item)) return;
        event.setCancelled(true);
        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK:
                Material type = event.getClickedBlock().getType();
                if (type == Material.DIODE_BLOCK_OFF || type == Material.DIODE_BLOCK_ON || type == Material.REDSTONE_TORCH_OFF || type == Material.REDSTONE_TORCH_ON  || type == Material.REDSTONE_COMPARATOR_ON  || type == Material.REDSTONE_COMPARATOR_OFF) {
                    OverstackPlayer player = OverstackPlayerManager.getOPlayer(event.getPlayer().getUniqueId());
                    int redstone_ticks = 1;
                    if (type == Material.DIODE_BLOCK_OFF || type == Material.DIODE_BLOCK_ON) {
                        Diode diode = new Diode(type, event.getClickedBlock().getData());
                        redstone_ticks = diode.getDelay();

                    }
                    if (player == null) return;
                    if (event.getPlayer().isSneaking()) {

                        subtractTicks(event.getPlayer(), redstone_ticks);
                        return;
                    }
                    addTicks(event.getPlayer(), redstone_ticks);
                }
                break;
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                resetTicks(event.getPlayer());
                break;
            default:
                event.setCancelled(false);
                break;
        }

    }

    private void subtractTicks(Player player, int amount) {
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        oPlayer.setRedstoneTicks(oPlayer.getRedstoneTicks() - amount);
        MessageUtils.playerSend(player, Lang.get().TICKS_SUBTRACTED.replace("%REDSTONE_TICK_COUNT%", amount + "")
                .replace("%GAME_TICK_COUNT%", amount * 2 + "")
                .replace("%TOTAL_GAME_TICKS%", oPlayer.getRedstoneTicks() * 2 + "")
                .replace("%TOTAL_REDSTONE_TICKS%", oPlayer.getRedstoneTicks() + ""));
    }

    private void addTicks(Player player, int amount) {
        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        oPlayer.addRedStoneTicks(amount);
        MessageUtils.playerSend(player, Lang.get().TICKS_ADD.replace("%REDSTONE_TICK_COUNT%", amount + "")
                .replace("%GAME_TICK_COUNT%", amount * 2 + "")
                .replace("%TOTAL_GAME_TICKS%", oPlayer.getRedstoneTicks() * 2 + "")
                .replace("%TOTAL_REDSTONE_TICKS%", oPlayer.getRedstoneTicks() + ""));
    }

    private void resetTicks(Player player) {

        OverstackPlayer oPlayer = OverstackPlayerManager.getOPlayer(player.getUniqueId());
        MessageUtils.playerSend(player, Lang.get().TICKS_RESET
                .replace("%TOTAL_GAME_TICKS%", oPlayer.getRedstoneTicks() * 2 + "")
                .replace("%TOTAL_REDSTONE_TICKS%", oPlayer.getRedstoneTicks() + ""));
        oPlayer.setRedstoneTicks(0);
    }


}
