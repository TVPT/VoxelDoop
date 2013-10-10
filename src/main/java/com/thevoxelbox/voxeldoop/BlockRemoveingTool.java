package com.thevoxelbox.voxeldoop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;

public abstract class BlockRemoveingTool extends AbstractTool
{
    private static final String PERM_PREFIX = "voxeldoop.override.";

    private final Set<Material> unbreakableBlocks = new HashSet<>();

    public boolean canBreak(final Player player, final Block block)
    {
        if(unbreakableBlocks.contains(block.getType()))
        {
            if(!player.hasPermission(PERM_PREFIX + this.getName().replaceAll(" ", "").toLowerCase())) return false;
        }
        return true;
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
