package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.BlockRemoveingTool;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;
import com.thevoxelbox.voxeldoop.events.DoopSpreadEvent;

public class Hammer extends BlockRemoveingTool
{
    private static int MAX_PUSH_PULL_THRESHOLD = 25;
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
            if (!this.canBreak(player, targetBlock)) return;
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
                        this.updatePlayersOfBlockChange(targetBlock);
                        this.updatePlayersOfBlockChange(targetBlock.getRelative(face.getOppositeFace()));
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
                    if (!this.canBreak(player, targetBlock.getRelative(face))) return;
                    final DoopSpreadEvent spreadEvent = new DoopSpreadEvent(targetBlock, face, player);
                    Bukkit.getPluginManager().callEvent(spreadEvent);
                    if (spreadEvent.isCancelled())
                    {
                        return;
                    }
                    targetBlock.getRelative(face).setTypeIdAndData(targetBlock.getTypeId(), targetBlock.getData(), false);
                    targetBlock.setTypeId(Material.AIR.getId(), false);
                    this.updatePlayersOfBlockChange(targetBlock);
                    this.updatePlayersOfBlockChange(targetBlock.getRelative(face));
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
        if (distence < MAX_PUSH_PULL_THRESHOLD)
        {
            if (block.getRelative(face).isEmpty())
            {
                if (!this.canBreak(player, block)) return;
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
                    this.updatePlayersOfBlockChange(block);
                    this.updatePlayersOfBlockChange(block.getRelative(face));
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

    @ConfigurationGetter("max-push-pull-distance")
    public int getMaxPushPullThreshold()
    {
        return MAX_PUSH_PULL_THRESHOLD;
    }

    @ConfigurationSetter("max-push-pull-distance")
    public void setMaxPushPullThreshold(final int MaxPushPullThreshold)
    {
        MAX_PUSH_PULL_THRESHOLD = MaxPushPullThreshold;
    }
}
