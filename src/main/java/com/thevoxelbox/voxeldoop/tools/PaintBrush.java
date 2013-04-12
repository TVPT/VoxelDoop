package com.thevoxelbox.voxeldoop.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.events.DoopPaintEvent;
import com.thevoxelbox.voxeldoop.util.BlockInfoWrapper;

public class PaintBrush extends AbstractTool
{
    private final String brushLore = "VoxelBox Magic Paintbrush";

    public PaintBrush()
    {
        this.setName("Paint Brush");
        this.setToolMaterial(Material.SLIME_BALL);
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
            final BlockInfoWrapper wrapper = this.getMeterialAndDataFromBrush(itemUsed.getItemMeta());
            if (wrapper.getMaterial() != null)
            {
                final DoopPaintEvent paintEvent = new DoopPaintEvent(targetBlock, player, wrapper.getMaterial(), wrapper.getData());
                Bukkit.getPluginManager().callEvent(paintEvent);
                if (paintEvent.isCancelled())
                {
                    return;
                }
                targetBlock.setTypeIdAndData(paintEvent.getTargetMaterial().getId(), paintEvent.getTargetData(), false);
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

    private BlockInfoWrapper getMeterialAndDataFromBrush(final ItemMeta item)
    {
        if (item.hasLore())
        {
            List<String> lore = item.getLore();
            if (lore.size() == 3)
            {
                if (lore.get(0).equals(this.brushLore))
                {
                    return new BlockInfoWrapper(Material.getMaterial(lore.get(1)), Byte.parseByte(lore.get(2)));
                }
            }
        }
        return null;
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
