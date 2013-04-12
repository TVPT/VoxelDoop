package com.thevoxelbox.voxeldoop.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DoopPaintEvent extends DoopEvent
{
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Material targetMaterial;
    private byte targetData;

    public DoopPaintEvent(final Block targetBlock, final Player toolUser, final Material tarMaterial, final byte tarData)
    {
        super(targetBlock, toolUser);
        if (this.targetMaterial.isBlock())
        {
            this.targetMaterial = tarMaterial;
        }
        else throw new IllegalArgumentException("Target material must be a block.");
        this.setTargetData(tarData);
    }

    public Material getTargetMaterial()
    {
        return this.targetMaterial;
    }

    public void setTargetMaterial(final Material newTarMaterial)
    {
        this.targetMaterial = newTarMaterial;
    }

    public byte getTargetData()
    {
        return this.targetData;
    }

    public void setTargetData(final byte targetData)
    {
        this.targetData = targetData;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST;
    }
}
