package com.thevoxelbox.voxeldoop.tools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thevoxelbox.voxeldoop.AbstractTool;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationGetter;
import com.thevoxelbox.voxeldoop.configuration.ConfigurationSetter;

public class Watch extends AbstractTool
{
    private int TIME_DAY = 1000;
    private int TIME_NIGHT = 14000;

    public Watch()
    {
        this.setName("Watch");
        this.setToolMaterial(Material.WATCH);
    }

    @Override
    public void onRangedUse(final Block targetBlock, final BlockFace face,
            final ItemStack itemUsed, final Player player, final Action action)
    {
        if (player.isSneaking())
        {
            player.resetPlayerTime();
            return;
        }
        final int timeToSet;
        switch (action)
        {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                timeToSet = this.TIME_DAY;
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                timeToSet = this.TIME_NIGHT;
                break;
            default:
                return;
        }
        player.setPlayerTime(timeToSet, false);
    }

    @Override
    public void onUse(Block targetBlock, BlockFace face, ItemStack itemUsed,
            Player player, Action action)
    {
        this.onRangedUse(targetBlock, face, itemUsed, player, action);
    }

    @ConfigurationGetter("daytime")
    public int getDayTime()
    {
        return TIME_DAY;
    }

    @ConfigurationSetter("daytime")
    public void setDayTime(int dayTime)
    {
        TIME_DAY = dayTime;
    }

    @ConfigurationGetter("nighttime")
    public int getNightTime()
    {
        return TIME_NIGHT;
    }

    @ConfigurationSetter("nighttime")
    public void setNightTime(int nightTime)
    {
        TIME_NIGHT = nightTime;
    }
}
