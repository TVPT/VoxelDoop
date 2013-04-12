package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.events.DoopSpreadEvent;

public class Hammer extends AbstractTool
{
    public Hammer()
    {
        this.setName("Hammer");
        this.setToolMaterial(Material.GOLD_PICKAXE);
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            if (player.isSneaking())
            {
                this.moveBlock(targetBlock, face.getOppositeFace(), 0, player);
            }
            else
            {
                if (targetBlock.getRelative(face.getOppositeFace()).isEmpty())
                {

                    if (targetBlock.getType().isSolid())
                    {
                        final DoopSpreadEvent spreadEvent = new DoopSpreadEvent(targetBlock, face.getOppositeFace(), player);
                        Bukkit.getPluginManager().callEvent(spreadEvent);
                        if (spreadEvent.isCancelled())
                        {
                            return;
                        }
                        targetBlock.getRelative(face.getOppositeFace()).setTypeIdAndData(targetBlock.getTypeId(), targetBlock.getData(), false);
                        targetBlock.setTypeId(Material.AIR.getId(), false);
                    }

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
                    final DoopSpreadEvent spreadEvent = new DoopSpreadEvent(targetBlock, face, player);
                    Bukkit.getPluginManager().callEvent(spreadEvent);
                    if (spreadEvent.isCancelled())
                    {
                        return;
                    }
                    targetBlock.getRelative(face).setTypeIdAndData(targetBlock.getTypeId(), targetBlock.getData(), false);
                    targetBlock.setTypeId(Material.AIR.getId(), false);
                }
            }
        }
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        if (face != null)
        {
            this.onUse(targetBlock, face, itemUsed, player, action);
        }
    }

    private void moveBlock(final Block block, final BlockFace face, final int distence, final Player player)
    {
        final Material mat = block.getType();
        final byte data = block.getData();
        if (distence < 25)
        {
            if (block.getRelative(face).isEmpty())
            {
                final DoopSpreadEvent spreadEvent = new DoopSpreadEvent(block, face, player);
                Bukkit.getPluginManager().callEvent(spreadEvent);
                if (spreadEvent.isCancelled())
                {
                    return;
                }
                if (mat.isSolid())
                {
                    block.getRelative(face).setTypeIdAndData(mat.getId(), data, false);
                    block.setTypeId(Material.AIR.getId(), false);
                }
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
