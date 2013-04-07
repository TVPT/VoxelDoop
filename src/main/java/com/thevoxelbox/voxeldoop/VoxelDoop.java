package com.thevoxelbox.voxeldoop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.thevoxelbox.voxeldoop.tools.DataSpanner;
import com.thevoxelbox.voxeldoop.tools.DoopStick;
import com.thevoxelbox.voxeldoop.tools.Hammer;
import com.thevoxelbox.voxeldoop.tools.Jackhammer;
import com.thevoxelbox.voxeldoop.tools.PaintBrush;
import com.thevoxelbox.voxeldoop.tools.Pliers;

public class VoxelDoop extends JavaPlugin
{
    private final ToolManager toolManager;
    private final DoopListener listener;

    public VoxelDoop()
    {
        this.toolManager = new ToolManager(this);
        this.listener = new DoopListener(this);
    }

    @Override
    public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(this.listener, this);
        this.toolManager.registerTool(new Jackhammer());
        this.toolManager.registerTool(new PaintBrush());
        this.toolManager.registerTool(new DoopStick());
        this.toolManager.registerTool(new Hammer());
        this.toolManager.registerTool(new Pliers());
        this.toolManager.registerTool(new DataSpanner());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        if (commandName.equalsIgnoreCase("godsays")) {
            if (args.length < 1) {
                return false;
            }

            String godmsg = "";

            for (String str : args) {
                godmsg = (godmsg + str + " ");
            }

            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[GOD] " + godmsg.toUpperCase());

            return true;
        }
        if (commandName.equalsIgnoreCase("evilsays")) {
            if (args.length < 1) {
                return false;
            }

            String godmsg = "";

            for (String str : args) {
                godmsg = (godmsg + str + " ");
            }

            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[EVIL] " + godmsg);

            return true;
        }
        if (commandName.equalsIgnoreCase("scisays"))
        {
            if (args.length < 1)
            {
                return false;
            }

            String godmsg = "";

            for (String str : args)
            {
                godmsg = (godmsg + str + " ");
            }

            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[SCIENCE] " + godmsg);

            return true;
        }
        if (commandName.equalsIgnoreCase("phisays")) {
            if (args.length < 1) {
                return false;
            }

            String godmsg = "";

            for (String str : args) {
                godmsg = (godmsg + str + " ");
            }

            Bukkit.broadcastMessage(ChatColor.AQUA + "[PHILOSOPHY] " + godmsg);

            return true;
        }
        if (commandName.equalsIgnoreCase("ignsays")) {
            if (args.length < 1) {
                return false;
            }

            String godmsg = "";

            for (String str : args) {
                godmsg = (godmsg + str + " ");
            }

            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[IGNORANCE] " + godmsg);

            return true;
        }
        return false;
    }

    /**
     * @return the tool manager
     */
    public ToolManager getToolManager() {
        return toolManager;
    }
}
