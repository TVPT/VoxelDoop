package com.thevoxelbox.bukkit.doop.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.thevoxelbox.bukkit.doop.ITool;


public class PaintBrush implements ITool
{
    private final String brushLore = "VoxelBox Magic Paintbrush";

    @Override
    public String getName()
    {
        return "Paint Brush";
    }

    @Override
    public Material getToolMaterial()
    {
        return Material.SLIME_BALL;
    }

    @Override
    public void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action)
    {
        if (!itemUsed.hasItemMeta())
        {
            itemUsed.setItemMeta(Bukkit.getItemFactory().getItemMeta(itemUsed.getType()));
        }

        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)
        {
            this.setBrushMeterial(itemUsed, itemUsed.getItemMeta(), targetBlock.getType(), targetBlock.getData());
        }
        else
        {
            final Material targetMat = this.getMeterialFromBrush(itemUsed.getItemMeta());
            if (targetMat != null)
            {
                final byte data = this.getDataFromBrush(itemUsed.getItemMeta());
                final BlockPlaceEvent placeEvent = new BlockPlaceEvent(targetBlock, targetBlock.getState(), null, new ItemStack(targetMat, 1), player, true);
                Bukkit.getPluginManager().callEvent(placeEvent);
                if (placeEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setTypeIdAndData(targetMat.getId(), data, false);
            }
        }
    }

    @Override
    public void onRangedUse(Block targetBlock, final BlockFace face, final ItemStack itemUsed, Player player, Action action)
    {
        if ((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) && !targetBlock.isLiquid())
        {
            this.setBrushMeterial(itemUsed, itemUsed.getItemMeta(), targetBlock.getType(), targetBlock.getData());
        }
    }

    private Material getMeterialFromBrush(final ItemMeta item)
    {
        if (item.hasLore())
        {
            List<String> lore = item.getLore();
            if (lore.size() == 3)
            {
                if (lore.get(0).equals(this.brushLore))
                {
                    return Material.getMaterial(lore.get(1));
                }
            }
        }
        return null;
    }
    private byte getDataFromBrush(final ItemMeta item)
    {
        if (item.hasLore())
        {
            List<String> lore = item.getLore();
            if (lore.size() == 3)
            {
                if (lore.get(0).equals(this.brushLore))
                {
                    return Byte.parseByte(lore.get(2));
                }
            }
        }
        return 0;
    }
    private void setBrushMeterial(final ItemStack itemApplyed, final ItemMeta item, final Material mat, final byte data)
    {
        item.setDisplayName(this.getFriendlyBlockName(mat.toString()) + "Paintbrush");
        List<String> lore = new ArrayList<>();
        lore.add(this.brushLore);
        lore.add(mat.toString());
        lore.add(String.valueOf(data));
        item.setLore(lore);
        itemApplyed.setItemMeta(item);
    }
    private String getFriendlyBlockName(final String rawName)
    {
        final String[] split = rawName.toLowerCase().split("_");
        final StringBuilder builder = new StringBuilder();
        for (String str : split)
        {
            final char[] cStr = str.toCharArray();
            if (cStr.length > 1)
            {
                cStr[0] = Character.toUpperCase(cStr[0]);
            }
            builder.append(cStr).append(" ");
        }
        return builder.toString();
    }
}
