package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.events.DoopPaintEvent;

public class DataSpanner extends AbstractTool
{
    public DataSpanner()
    {
        this.setName("Data Spanner");
        this.setToolMaterial(Material.BONE);
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        final byte maxDura = 15; // Constant because sometimes funky data values are useful
        final byte targetData;
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            if (targetBlock.getData() == 0)
            {
                targetData = maxDura;
                final DoopPaintEvent paintEvent = new DoopPaintEvent(targetBlock, player, targetBlock.getType(), targetData);
                Bukkit.getPluginManager().callEvent(paintEvent);
                if (paintEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setData(paintEvent.getTargetData(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
            else
            {
                targetData = (byte) (targetBlock.getData() - 1);
                final DoopPaintEvent paintEvent = new DoopPaintEvent(targetBlock, player, targetBlock.getType(), targetData);
                Bukkit.getPluginManager().callEvent(paintEvent);
                if (paintEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setData(paintEvent.getTargetData(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
        }
        else
        {
            if (targetBlock.getData() == maxDura)
            {
                targetData = 0;
                final DoopPaintEvent paintEvent = new DoopPaintEvent(targetBlock, player, targetBlock.getType(), targetData);
                Bukkit.getPluginManager().callEvent(paintEvent);
                if (paintEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setData(paintEvent.getTargetData(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
            else
            {
                targetData = (byte) (targetBlock.getData() + 1);
                final DoopPaintEvent paintEvent = new DoopPaintEvent(targetBlock, player, targetBlock.getType(), targetData);
                Bukkit.getPluginManager().callEvent(paintEvent);
                if (paintEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setData(paintEvent.getTargetData(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
        }
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }
}
