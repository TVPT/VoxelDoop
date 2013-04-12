package com.thevoxelbox.voxeldoop.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class DoopDestroyEvent extends DoopEvent
{
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public DoopDestroyEvent(final Block targetBlock, final Player toolUser)
    {
        super(targetBlock, toolUser);
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
