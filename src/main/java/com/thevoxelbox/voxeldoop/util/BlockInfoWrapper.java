package com.thevoxelbox.voxeldoop.util;

import org.bukkit.Material;

public class BlockInfoWrapper
{
    private final Material material;
    private final byte data;

    public BlockInfoWrapper(final Material material, final byte data)
    {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial()
    {
        return this.material;
    }

    public int getMaterialId()
    {
        return this.material.getId();
    }

    public byte getData()
    {
        return this.data;
    }
}
