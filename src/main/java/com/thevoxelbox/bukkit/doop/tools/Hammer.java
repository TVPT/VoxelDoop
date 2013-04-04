package com.thevoxelbox.bukkit.doop.tools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.bukkit.doop.ITool;

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
    public void onUse(Block targetBlock, ItemStack itemUsed, Player player, Action action)
    {
        
    }

    @Override
    public void onRangedUse(Block targetBlock, ItemStack itemUsed, Player player, Action action)
    {
        
    }

}
