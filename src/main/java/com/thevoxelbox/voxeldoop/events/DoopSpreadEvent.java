package com.thevoxelbox.voxeldoop.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DoopSpreadEvent extends DoopEvent
{
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final BlockFace targetFace;

    public DoopSpreadEvent(final Block targetBlock, final BlockFace targetFace, final Player toolUser)
    {
        super(targetBlock, toolUser);
        this.targetFace = targetFace;
    }

    public BlockFace getTargetFace()
    {
        return targetFace;
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
