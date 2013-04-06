package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.ITool;


public class Hammer implements ITool
{

    @Override
    public String getName()
    {
        return "Hammer";
    }

    @Override
    public Material getToolMaterial()
    {
        return Material.GOLD_PICKAXE;
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face,
            final ItemStack itemUsed, final Player player, final Action action)
    {
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            if (player.isSneaking())
            {
                this.moveBlock(targetBlock, face.getOppositeFace(), 0, player);
            }
            else
            {
                if (targetBlock.getRelative(face).isEmpty())
                {
                    final BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock.getRelative(face), targetBlock.getRelative(face).getState(), null, new ItemStack(targetBlock.getRelative(face).getType(), 1), player, true);
                    Bukkit.getPluginManager().callEvent(placeEvent);
                    if (placeEvent.isCancelled())
                    {
                        return;
                    }
                    targetBlock.getRelative(face.getOppositeFace()).setTypeIdAndData(targetBlock.getTypeId(), targetBlock.getData(), false);
                    targetBlock.setTypeId(Material.AIR.getId(), false);
                }
            }
        }
        else
        {
            if (player.isSneaking())
            {
                this.moveBlock(targetBlock, face, 0, player);
            }
            else
            {
                if (targetBlock.getRelative(face).isEmpty())
                {
                    targetBlock.getRelative(face).setTypeIdAndData(targetBlock.getTypeId(), targetBlock.getData(), false);
                    targetBlock.setTypeId(Material.AIR.getId(), false);
                }
            }
        }
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face,
            final ItemStack itemUsed, final Player player, final Action action)
    {
        this.onUse(targetBlock, face, itemUsed, player, action);
    }

    private void moveBlock(final Block block, final BlockFace face,
            final int distence, final Player player)
    {
        final Material mat = block.getType();
        final byte data = block.getData();
        if (distence < 10)
        {
            if (block.getRelative(face).isEmpty())
            {
                final BlockPlaceEvent placeEvent = new BlockPlaceEvent(block.getRelative(face), block.getRelative(face).getState(), null, new ItemStack(block.getRelative(face).getType(), 1), player, true);
                Bukkit.getPluginManager().callEvent(placeEvent);
                if (placeEvent.isCancelled())
                {
                    return;
                }
                block.getRelative(face).setTypeIdAndData(mat.getId(), data, false);
                block.setTypeId(Material.AIR.getId(), false);
            }
            else
            {
                this.moveBlock(block.getRelative(face), face, distence + 1, player);
                if (block.getRelative(face).isEmpty())
                {
                    this.moveBlock(block, face, distence, player);
                }
            }
        }
    }
}
