package com.thevoxelbox.voxeldoop.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;
import com.thevoxelbox.voxeldoop.events.DoopDestroyEvent;

public class Jackhammer extends AbstractTool
{
    private Set<Material> unbreakableBlocks = new HashSet<>();

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
            targetBlock.setTypeId(Material.AIR.getId(), true);
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
                if (this.unbreakableBlocks.contains(targetBlock.getType())) return;
                targetBlock.setTypeId(Material.AIR.getId(), true);
                this.updatePlayersOfBlockChange(targetBlock);
            }
            else
            {
                if (this.unbreakableBlocks.contains(targetBlock.getType())) return;
                targetBlock.setTypeId(Material.AIR.getId(), false);
                this.updatePlayersOfBlockChange(targetBlock);
            }
        }
    }

    @ConfigurationGetter("unbreakable-blocks")
    public int[] getUnbreakable()
    {
        final int[] unbreakIDs = new int[unbreakableBlocks.size()];
        final Material[] unbreakMats = unbreakableBlocks.toArray(new Material[unbreakableBlocks.size()]);
        for (int i = 0; i < unbreakMats.length; i++)
        {
            unbreakIDs[i] = unbreakMats[i].getId();
        }
        Arrays.sort(unbreakIDs);
        return unbreakIDs;
    }

    @ConfigurationSetter("unbreakable-blocks")
    public void setUnbreakable(final int[] unbreakableIds)
    {
        for (int unbreakId : unbreakableIds)
        {
            final Material unbreakMat = Material.getMaterial(unbreakId);
            if (unbreakMat != null)
            {
                if (unbreakMat.isBlock())
                {
                    this.unbreakableBlocks.add(unbreakMat);
                }
            }
        }
    }
}
