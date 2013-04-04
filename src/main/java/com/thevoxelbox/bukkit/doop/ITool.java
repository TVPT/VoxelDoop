package com.thevoxelbox.bukkit.doop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface ITool
{
    /**
     * Gets the name of this tool
     *
     * @return this tools name
     */
    String getName();
    Material getToolMaterial();
    void onUse(Block targetBlock, ItemStack itemUsed, Player player, Action action);
    void onRangedUse(Block targetBlock, ItemStack itemUsed, Player player, Action action);
}
