package com.thevoxelbox.voxeldoop.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.BlockRemoveingTool;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;
import com.thevoxelbox.voxeldoop.events.DoopDestroyEvent;

public class Chainsaw extends BlockRemoveingTool
{
    private static final Material[] DEFAULT_MASK = { Material.LOG, Material.LEAVES };

    private final Set<Material> maskedBlocks = new HashSet<>();

    public Chainsaw()
    {
        this.maskedBlocks.addAll(Arrays.asList(Chainsaw.DEFAULT_MASK));
        this.setName("Chainsaw");
        this.setToolMaterial(Material.GOLD_AXE);
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        final int tarX = targetBlock.getX();
        final int tarY = targetBlock.getY();
        final int tarZ = targetBlock.getZ();
        final World tarWorld = targetBlock.getWorld();
        if (action.equals(Action.LEFT_CLICK_BLOCK))
        {
            for (int ix = tarX - 1; ix < (tarX + 2); ix++)
            {
                for (int iy = tarY + 1; iy > (tarY - 2); iy--)
                {
                    for (int iz = tarZ - 1; iz < (tarZ + 2); iz++)
                    {
                        final Block curBlock = tarWorld.getBlockAt(ix, iy, iz);
                        if (this.isInMask(curBlock.getType()))
                        {
                            if (!this.canBreak(player, curBlock)) continue;
                            final DoopDestroyEvent breakEvent = new DoopDestroyEvent(curBlock, player);
                            Bukkit.getPluginManager().callEvent(breakEvent);
                            if (breakEvent.isCancelled())
                            {
                                continue;
                            }
                            curBlock.setType(Material.AIR);
                        }
                    }
                }
            }
        }
        if (action.equals(Action.RIGHT_CLICK_BLOCK))
        {
            for (int ix = tarX - 1; ix < (tarX + 2); ix++)
            {
                for (int iz = tarZ - 1; iz < (tarZ + 2); iz++)
                {
                    for (int iy = tarY + 2; iy > (tarY - 3); iy--)
                    {
                        final Block curBlock = tarWorld.getBlockAt(ix, iy, iz);
                        if (this.isInMask(curBlock.getType()))
                        {
                            if (!this.canBreak(player, curBlock)) continue;
                            final DoopDestroyEvent breakEvent = new DoopDestroyEvent(curBlock, player);
                            Bukkit.getPluginManager().callEvent(breakEvent);
                            if (breakEvent.isCancelled())
                            {
                                continue;
                            }
                            curBlock.setType(Material.AIR);
                        }
                    }
                }
            }
            for (int ix = tarX - 2; ix < (tarX + 3); ix = ix + 4)
            {
                for (int iz = tarZ - 1; iz < (tarZ + 2); iz++)
                {
                    for (int iy = tarY + 1; iy > (tarY - 2); iy--)
                    {
                        final Block curBlock = tarWorld.getBlockAt(ix, iy, iz);
                        if (this.isInMask(curBlock.getType()))
                        {
                            if (!this.canBreak(player, curBlock)) continue;
                            final DoopDestroyEvent breakEvent = new DoopDestroyEvent(curBlock, player);
                            Bukkit.getPluginManager().callEvent(breakEvent);
                            if (breakEvent.isCancelled())
                            {
                                continue;
                            }
                            curBlock.setType(Material.AIR);
                        }
                    }
                }
            }
            for (int ix = tarX - 1; ix < (tarX + 2); ix++)
            {
                for (int iz = tarZ - 2; iz < (tarZ + 3); iz = iz + 4)
                {
                    for (int iy = tarY + 1; iy > (tarY - 2); iy--)
                    {
                        final Block curBlock = tarWorld.getBlockAt(ix, iy, iz);
                        if (this.isInMask(curBlock.getType()))
                        {
                            if (!this.canBreak(player, curBlock)) continue;
                            final DoopDestroyEvent breakEvent = new DoopDestroyEvent(curBlock, player);
                            Bukkit.getPluginManager().callEvent(breakEvent);
                            if (breakEvent.isCancelled())
                            {
                                continue;
                            }
                            curBlock.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }

    private boolean isInMask(final Material material)
    {
        return this.maskedBlocks.contains(material);
    }

    @ConfigurationGetter("terrain-mask")
    public int[] getMask()
    {
        final int[] maskIDs = new int[this.maskedBlocks.size()];
        final Material[] maskMats = this.maskedBlocks.toArray(new Material[this.maskedBlocks.size()]);
        for (int i = 0; i < maskMats.length; i++)
        {
            maskIDs[i] = maskMats[i].getId();
        }
        Arrays.sort(maskIDs);
        return maskIDs;
    }

    @ConfigurationSetter("terrain-mask")
    public void setMask(final int[] newMask)
    {
        for (final int maskID : newMask)
        {
            final Material maskedMat = Material.getMaterial(maskID);
            if (maskedMat != null)
            {
                if (maskedMat.isBlock())
                {
                    this.maskedBlocks.add(maskedMat);
                }
            }
        }
    }
}