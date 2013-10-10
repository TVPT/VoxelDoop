package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.BlockRemoveingTool;
import com.thevoxelbox.voxeldoop.events.DoopDestroyEvent;

public class Jackhammer extends BlockRemoveingTool
{
    public Jackhammer()
    {
        this.setName("Jackhammer");
        this.setToolMaterial(Material.DIAMOND_PICKAXE);
    }

    @Override
    public void onUse(Block targetBlock, final BlockFace face, ItemStack itemUsed, Player player, Action action)
    {
        final DoopDestroyEvent breakEvent = new DoopDestroyEvent(targetBlock, player);
        Bukkit.getPluginManager().callEvent(breakEvent);
        if (breakEvent.isCancelled())
        {
            return;
        }
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            targetBlock.setType(Material.AIR);
            this.updatePlayersOfBlockChange(targetBlock);
        }
        else
        {
            targetBlock.setTypeId(Material.AIR.getId(), false);
            this.updatePlayersOfBlockChange(targetBlock);
        }
    }

    @Override
    public void onRangedUse(Block targetBlock, final BlockFace face, ItemStack itemUsed, Player player, Action action)
    {
        if (player.isSneaking())
        {
            final DoopDestroyEvent breakEvent = new DoopDestroyEvent(targetBlock, player);
            Bukkit.getPluginManager().callEvent(breakEvent);
            if (breakEvent.isCancelled())
            {
                return;
            }
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
            {
                if (!this.canBreak(player, targetBlock)) return;
                targetBlock.setType(Material.AIR);
                this.updatePlayersOfBlockChange(targetBlock);
            }
            else
            {
                if (!this.canBreak(player, targetBlock)) return;
                targetBlock.setTypeId(Material.AIR.getId(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
        }
    }
}
