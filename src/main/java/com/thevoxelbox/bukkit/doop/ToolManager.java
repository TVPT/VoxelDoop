package com.thevoxelbox.bukkit.doop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ToolManager
{
    private final String RANGED_PERM_PREFIX = "voxeldoop.ranged.";
    private final String PERM_PREFIX = "voxeldoop.use.";

    private final VoxelDoop plugin;
    private final Map<Material, ITool> registeredTools = new HashMap<>();

    public ToolManager(final VoxelDoop plugin)
    {
        Validate.notNull(plugin, "Cannot run if plugin is null");
        this.plugin = plugin;
    }

    public void registerTool(final ITool newTool)
    {
        Validate.notNull(newTool, "You Cannot register null tools");
        Validate.notNull(newTool.getName(), "You Cannot register tools with no name");
        Validate.isTrue(!newTool.getName().isEmpty(), "You Cannot register tools with no name");

        this.registeredTools.put(newTool.getToolMaterial(), newTool);
        this.plugin.getLogger().info("Registered tool: " + newTool.getName());
    }

    public void onRangedUse(final Player player, final ItemStack itemUsed, Action action)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final HitBlox rangeHelper = new HitBlox(player, player.getWorld());
            final Block tarBlock = rangeHelper.getTargetBlock();
            if (tarBlock != null)
            {
                final ITool tool = this.registeredTools.get(itemUsed.getType());
                if (player.hasPermission(this.RANGED_PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
                {
                    tool.onRangedUse(tarBlock, itemUsed, player, action);
                }
            }
        }
    }

    public boolean onUse(final Player player, final ItemStack itemUsed, Action action, Block tarBlock)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final ITool tool = this.registeredTools.get(itemUsed.getType());
            if (player.hasPermission(this.PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
            {
                this.registeredTools.get(itemUsed.getType()).onUse(tarBlock, itemUsed, player, action);
                return true;
            }
        }
        return false;
    }
}
