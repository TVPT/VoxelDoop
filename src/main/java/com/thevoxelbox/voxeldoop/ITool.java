package com.thevoxelbox.voxeldoop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface ITool
{
    /**
     * Gets the name of this tool. The name of the tool can not be null or
     * empty.
     * 
     * @return The tools name
     */
    String getName();

    /**
     * Gets the material that the player will use to utilize this tool. Cannot
     * return null
     * 
     * @return Tools material
     */
    Material getToolMaterial();

    /**
     * Gets called when a player uses this tool from a range and the said player has the
     * permission to use it.
     * 
     * @param targetBlock
     *            The target block that the player is looking at
     * @param face
     *            The face of the block that the player is looking at
     * @param itemUsed
     *            The item stack that is in the hand and being used at the time
     *            of the event
     * @param player
     *            The player using this tool
     * @param action
     *            The corresponding bukkit action that called this tool use
     */
    void onRangedUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action);

    /**
     * Gets called when a player uses this tool and the said player has the
     * permission to use it.
     * 
     * @param targetBlock
     *            The target block that the player is looking at
     * @param face
     *            The face of the block that the player is looking at
     * @param itemUsed
     *            The item stack that is in the players hand and being used
     *            at the time of the event
     * @param player
     *            The player using this tool
     * @param action
     *            The corresponding bukkit action that called this tool use
     */
    void onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action);
}
