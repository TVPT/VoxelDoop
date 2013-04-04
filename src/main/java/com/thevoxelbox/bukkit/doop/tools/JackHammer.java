package com.thevoxelbox.bukkit.doop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.bukkit.doop.ITool;

public class JackHammer implements ITool
{
    @Override
    public String getName()
    {
        return "Jackhammer";
    }

    @Override
    public Material getToolMaterial()
    {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    public void onUse(Block targetBlock, ItemStack itemUsed, Player player, Action action)
    {
        BlockBreakEvent breakEvent = new BlockBreakEvent(targetBlock, player);
        Bukkit.getPluginManager().callEvent(breakEvent);
        if (breakEvent.isCancelled())
        {
            return;
        }
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            targetBlock.setTypeId(Material.AIR.getId(), true);
        }
        else
        {
            targetBlock.setTypeId(Material.AIR.getId(), false);
        }
    }

    @Override
    public void onRangedUse(Block targetBlock, ItemStack itemUsed, Player player, Action action)
    {
        BlockBreakEvent breakEvent = new BlockBreakEvent(targetBlock, player);
        Bukkit.getPluginManager().callEvent(breakEvent);
        if (breakEvent.isCancelled())
        {
            return;
        }
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            targetBlock.setTypeId(Material.AIR.getId(), true);
        }
        else
        {
            targetBlock.setTypeId(Material.AIR.getId(), false);
        }
    }
}
