package com.thevoxelbox.voxeldoop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractTool implements ITool
{
    private String toolName = "Default Tool Name (Yell at the developer if you see this)";
    private Material toolMaterial = Material.AIR;
    
    @Override
    public final String getName()
    {
        return this.toolName;
    }
    public final void setName(final String newName)
    {
        this.toolName = newName;
    }

    @Override
    public final Material getToolMaterial()
    {
        return this.toolMaterial;
    }

    @Override
    public final void setToolMaterial(final Material newToolMat)
    {
        this.toolMaterial = newToolMat;
    }

    @Override
    public abstract void onRangedUse(Block targetBlock, BlockFace face,  ItemStack itemUsed, Player player, Action action);

    @Override
    public abstract void onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action);
}
