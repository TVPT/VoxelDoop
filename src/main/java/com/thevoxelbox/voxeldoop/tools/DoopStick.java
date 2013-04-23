package com.thevoxelbox.voxeldoop.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;


public class DoopStick extends AbstractTool
{
    private final Set<Material> unDoopable = new HashSet<>();

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
            if (unDoopable.contains(targetBlock.getType())) return;
            final ItemStack addedItem = new ItemStack(targetBlock.getType(), 64, (short) targetBlock.getData());
            player.getInventory().addItem(addedItem);
            player.updateInventory();
        }
    }

    @ConfigurationGetter("undoopable-blocks")
    public int[] getUndoopable()
    {
        final int[] unDoopIDs = new int[unDoopable.size()];
        final Material[] unDoopMats = unDoopable.toArray(new Material[unDoopable.size()]);
        for (int i = 0; i < unDoopMats.length; i++)
        {
            unDoopIDs[i] = unDoopMats[i].getId();
        }
        Arrays.sort(unDoopIDs);
        return unDoopIDs;
    }

    @ConfigurationSetter("undoopable-blocks")
    public void setUndoopable(final int[] bannedIds)
    {
        for (int bannedId : bannedIds)
        {
            final Material bannedMat = Material.getMaterial(bannedId);
            if (bannedMat != null)
            {
                if (bannedMat.isBlock())
                {
                    this.unDoopable.add(bannedMat);
                }
            }
        }
    }
}
