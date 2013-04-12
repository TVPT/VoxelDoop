package com.thevoxelbox.voxeldoop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.util.BlockRangeHelper;

/**
 * Manages all tool registration, use, and permissions.
 *
 * @author TheCryoknight
 */
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

    @SuppressWarnings("deprecation")
    public void onRangedUse(final Player player, final ItemStack itemUsed, Action action)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final BlockRangeHelper rangeHelper = new BlockRangeHelper(player, player.getWorld());
            final Block tarBlock = rangeHelper.getTargetBlock();
            final Block previousBlock = rangeHelper.getLastBlock();
            if (tarBlock != null && previousBlock != null)
            {
                final BlockFace tarFace = tarBlock.getFace(previousBlock);
                final ITool tool = this.registeredTools.get(itemUsed.getType());
                if (player.hasPermission(ToolManager.RANGED_PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
                {
                    try
                    {
                        tool.onRangedUse(tarBlock, tarFace, itemUsed, player, action);
                        itemUsed.setDurability((short) 0);
                        player.updateInventory();
                        
                    }
                    catch (final Exception e)
                    {
                        this.plugin.getLogger().severe("Tool Error: Could not pass ranged tool use to " + tool.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public boolean onUse(final Player player, final ItemStack itemUsed, Action action, Block tarBlock, BlockFace tarFace)
    {
        if (this.registeredTools.containsKey(itemUsed.getType()))
        {
            final ITool tool = this.registeredTools.get(itemUsed.getType());
            if (player.hasPermission(ToolManager.PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase()))
            {
                try
                {
                    Validate.notNull(tarFace);
                    this.registeredTools.get(itemUsed.getType()).onUse(tarBlock, tarFace, itemUsed, player, action);
                    itemUsed.setDurability((short) 0);
                    player.updateInventory();
                }
                catch (final Exception e)
                {
                    this.plugin.getLogger().severe("Tool Error: Could not pass tool use to " + tool.getName());
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
