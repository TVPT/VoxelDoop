package com.thevoxelbox.bukkit.doop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.bukkit.doop.ITool;

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
    public void onRangedUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action)
    {
        final BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock, targetBlock.getState(), null, new ItemStack(1, 1), player, true);
        Bukkit.getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled())
        {
            return;
        }
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            final short maxDura = targetBlock.getType().getMaxDurability();
            if (targetBlock.getData() == 0)
            {
                targetBlock.setData((byte) maxDura, false);
            }
            else if (targetBlock.getData() - 1 <= 0)
            {
                targetBlock.setData((byte) (targetBlock.getData() - 1), false);
            }
        }
        else
        {
            final short maxDura = targetBlock.getType().getMaxDurability();
            if (targetBlock.getData() == maxDura)
            {
                targetBlock.setData((byte) 0, false);
            }
            else if (targetBlock.getData() + 1 <= maxDura)
            {
                targetBlock.setData((byte) (targetBlock.getData() + 1), false);
            }
        }
    }

    @Override
    public void onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }
}
