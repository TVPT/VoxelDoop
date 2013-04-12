package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.AbstractTool;


public class DoopStick extends AbstractTool
{
    public DoopStick()
    {
        this.setName("Dooplicator");
        this.setToolMaterial(Material.STONE_AXE);
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            return;
        }
        else
        {
            final ItemStack addedItem = new ItemStack(targetBlock.getType(), 64, (short) targetBlock.getData());
            player.getInventory().addItem(addedItem);
            player.updateInventory();
        }
    }
}
