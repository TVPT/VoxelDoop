package com.thevoxelbox.voxeldoop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DoopListener implements Listener
{
    private final VoxelDoop plugin;

    public DoopListener(final VoxelDoop plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        ItemStack placedItem = event.getItemInHand();
        if (placedItem != null)
        {
            if (placedItem.getAmount() == 32 && placedItem.getType().isBlock())
            {
                placedItem.setAmount(64);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.hasItem())
        {
            if (event.getClickedBlock() == null)
            {
                this.plugin.getToolManager().onRangedUse(event.getPlayer(), event.getItem(), event.getAction());
            }
            else
            {
                if (this.plugin.getToolManager().onUse(event.getPlayer(), event.getItem(), event.getAction(), event.getClickedBlock(), event.getBlockFace()))
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
