package com.thevoxelbox.bukkit.doop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.thevoxelbox.bukkit.doop.tools.DoopStick;
import com.thevoxelbox.bukkit.doop.tools.JackHammer;
import com.thevoxelbox.bukkit.doop.tools.PaintBrush;

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
        this.toolManager.registerTool(new JackHammer());
        this.toolManager.registerTool(new PaintBrush());
        this.toolManager.registerTool(new DoopStick());
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

    public static String getFriendlyNote(byte note)
    {
        String notestr = "";
        switch ((int) note) {
            case 0:
                notestr = "F#";
                break;
            case 1:
                notestr = "G";
                break;
            case 2:
                notestr = "G#";
                break;
            case 3:
                notestr = "A";
                break;
            case 4:
                notestr = "A#";
                break;
            case 5:
                notestr = "B";
                break;
            case 6:
                notestr = "C";
                break;
            case 7:
                notestr = "C#";
                break;
            case 8:
                notestr = "D";
                break;
            case 9:
                notestr = "D#";
                break;
            case 10:
                notestr = "E";
                break;
            case 11:
                notestr = "F";
                break;
            case 12:
                notestr = "F#";
                break;
            case 13:
                notestr = "G";
                break;
            case 14:
                notestr = "G#";
                break;
            case 15:
                notestr = "A";
                break;
            case 16:
                notestr = "A#";
                break;
            case 17:
                notestr = "B";
                break;
            case 18:
                notestr = "C";
                break;
            case 19:
                notestr = "C#";
                break;
            case 20:
                notestr = "D";
                break;
            case 21:
                notestr = "D#";
                break;
            case 22:
                notestr = "E";
                break;
            case 23:
                notestr = "F";
                break;
            case 24:
                notestr = "F#";
        }

        return notestr;
    }

    public static ItemStack doopfix(int material, byte data)
    {
        switch (material)
        {
            case 55:
                return new ItemStack(331, 64, data);
            case 59:
                return new ItemStack(295, 64, data);
            case 68:
                return new ItemStack(323, 64, data);
            case 63:
                return new ItemStack(323, 64, data);
            case 75:
                return new ItemStack(76, 64, data);
            case 64:
                return new ItemStack(324, 64, data);
            case 71:
                return new ItemStack(330, 64, data);
            case 83:
                return new ItemStack(338, 64, data);
            case 93:
                return new ItemStack(356, 64, data);
            case 94:
                return new ItemStack(356, 64, data);
            case 26:
                return new ItemStack(355, 64, data);
        }
        return null;
    }

    /**
     * @return the tool manager
     */
    public ToolManager getToolManager() {
        return toolManager;
    }
}
