/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages;

import com.reliableplugins.villages.commands.CommandHandler;
import com.reliableplugins.villages.commands.CommandSpawn;
import com.reliableplugins.villages.config.FileManager;
import com.reliableplugins.villages.config.MainConfig;
import com.reliableplugins.villages.config.MessageConfig;
import com.reliableplugins.villages.nms.*;
import com.reliableplugins.villages.task.VillageTicker;
import com.reliableplugins.villages.type.Village;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.logging.Level;

public class Main extends JavaPlugin implements Listener
{
    public static Main INSTANCE;
    private String version;

    private CommandHandler commandHandler;
    private INMSHandler nmsHandler;

    private FileManager fileManager;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    private HashSet<Village> villages;
    private VillageTicker villageTicker;

    @Override
    public void onEnable()
    {
        Main.INSTANCE = this;
        version = getDescription().getVersion();

        try
        {
            villages = new HashSet<>();
            fileManager = setupConfigs();
            nmsHandler = setupNMS();
            commandHandler = setupCommands("villages");
            setupTasks();
            setupListeners();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        getLogger().log(Level.INFO, this.getDescription().getName() + " v" + version + " has been loaded");
    }

    @Override
    public void onDisable()
    {
        getLogger().log(Level.INFO, this.getDescription().getName() + " v" + this.getDescription().getVersion() + " has been unloaded");
    }

    private FileManager setupConfigs()
    {
        FileManager fileManager = new FileManager();
        fileManager.addFile(mainConfig = new MainConfig());
        fileManager.addFile(messageConfig = new MessageConfig());

        return fileManager;
    }

    private INMSHandler setupNMS()
    {
        String nmsVersion = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch(nmsVersion)
        {
            case "v1_8_R2":
                return new Version_1_8_R2();
            case "v1_8_R3":
                return new Version_1_8_R3();
            case "v_1_9_R1":
                return new Version_1_9_R1();
            case "v_1_9_R2":
                return new Version_1_9_R2();
            case "v_1_10_R1":
                return new Version_1_10_R1();
            case "v1_11_R1":
                return new Version_1_11_R1();
            case "v1_12_R1":
                return new Version_1_12_R1();
            case "v1_13_R1":
                return new Version_1_13_R1();
            case "v1_13_R2":
                return new Version_1_13_R2();
            case "v1_14_R1":
                return new Version_1_14_R1();
            case "v1_15_R1":
                return new Version_1_15_R1();
            case "v1_16_R1":
            default:
                return new Version_1_16_R1();
        }
    }

    private void setupListeners()
    {
    }

    private void setupTasks()
    {
        villageTicker = new VillageTicker(0L, 1L);
    }

    private CommandHandler setupCommands(String label)
    {
        CommandHandler commandHandler = new CommandHandler(label);
        commandHandler.addCommand(new CommandSpawn());
        return commandHandler;
    }

    public void addVillage(Village village)
    {
        this.villages.add(village);
    }

    public HashSet<Village> getVillages()
    {
        return villages;
    }

    public String getVersion()
    {
        return version;
    }

    public void reloadConfigs()
    {
        fileManager = setupConfigs();
    }

    public MainConfig getMainConfig()
    {
        return mainConfig;
    }

    public MessageConfig getMessageConfig()
    {
        return messageConfig;
    }

    public INMSHandler getNmsHandler()
    {
        return nmsHandler;
    }
}
