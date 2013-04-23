package com.thevoxelbox.voxeldoop.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public abstract class DoopEvent extends BlockEvent implements Cancellable
{
    private boolean cancelled = false;
    private final Player toolUser;
    private static final HandlerList HANDLER_LIST2 = new HandlerList();

    public DoopEvent(final Block targetBlock, final Player toolUser)
    {
        super(targetBlock);
        this.toolUser = toolUser;
    }

    public Player getPlayer()
    {
        return toolUser;
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLER_LIST2;
    }
}
