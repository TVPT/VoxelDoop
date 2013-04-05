package com.thevoxelbox.bukkit.doop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.bukkit.doop.util.HitBlox;


public class ToolManager
{
    private static final String RANGED_PERM_PREFIX = "voxeldoop.ranged.";
    private static final String PERM_PREFIX = "voxeldoop.use.";

    private final VoxelDoop plugin;
    private final Map<Material, ITool> registeredTools = new HashMap<>();

    public ToolManager(final VoxelDoop plugin)
    {
        Validate.notNull(plugin, "Cannot run if plugin is null");
        this.plugin = plugin;
    }

    public void registerTool(final ITool newTool)
    {
        Validate.notNull(newTool, "You cannot register null tools");
        Validate.notNull(newTool.getToolMaterial(), "You cannot register tools without a tool material");
        Validate.notNull(newTool.getName(), "You cannot register tools with no name");
        Validate.isTrue(!newTool.getName().isEmpty(), "You cannot register tools with no name");
        Validate.isTrue(!this.registeredTools.containsKey(newTool.getToolMaterial()), "You cannot register multiple tools with the same material");
        
        this.registeredTools.put(newTool.getToolMaterial(), newTool);
        this.plugin.getLogger().info("Registered tool: " + newTool.getName());
    }

    public void onRangedUse(final Player player, final ItemStack itemUsed, Action action)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final HitBlox rangeHelper = new HitBlox(player, player.getWorld());
            final Block tarBlock = rangeHelper.getTargetBlock();
            final BlockFace tarFace = rangeHelper.getFaceBlock().getFace(tarBlock);
            if (tarBlock != null)
            {
                Validate.notNull(tarFace);
                final ITool tool = this.registeredTools.get(itemUsed.getType());
                if (player.hasPermission(ToolManager.RANGED_PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
                {
                    tool.onRangedUse(tarBlock, tarFace, itemUsed, player, action);
                }
            }
        }
    }

    public boolean onUse(final Player player, final ItemStack itemUsed, Action action, Block tarBlock, BlockFace tarFace)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final ITool tool = this.registeredTools.get(itemUsed.getType());
            if (player.hasPermission(ToolManager.PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
            {
                Validate.notNull(tarFace);
                this.registeredTools.get(itemUsed.getType()).onUse(tarBlock, tarFace, itemUsed, player, action);
                return true;
            }
        }
        return false;
    }
}
