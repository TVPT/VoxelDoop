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

public class DataSpanner implements ITool
{
    @Override
    public String getName()
    {
        return "Data Spanner";
    }

    @Override
    public Material getToolMaterial()
    {
        return Material.BONE;
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        final BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock, targetBlock.getState(), null, new ItemStack(1, 1), player, true);
        Bukkit.getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled())
        {
            return;
        }
        final byte maxDura = 15; // Constant because sometimes funky data values are useful
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            if (targetBlock.getData() == 0)
            {
                targetBlock.setData(maxDura, false);
            }
            else
            {
                targetBlock.setData((byte) (targetBlock.getData() - 1), false);
            }
        }
        else
        {
            if (targetBlock.getData() == maxDura)
            {
                targetBlock.setData((byte) 0, false);
            }
            else
            {
                targetBlock.setData((byte) (targetBlock.getData() + 1), false);
            }
        }
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }
}
