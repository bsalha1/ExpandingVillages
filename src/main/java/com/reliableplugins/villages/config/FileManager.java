/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.config;

import com.reliableplugins.villages.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileManager
{
    private final List<Config> files = new ArrayList<>();

    public FileManager()
    {
        if(!Main.INSTANCE.getDataFolder().exists() && !Main.INSTANCE.getDataFolder().mkdir())
        {
            Main.INSTANCE.getLogger().log(Level.SEVERE, "Failed to create plugins/" + Main.INSTANCE.getDescription().getName());
        }
    }

    public void addFile(Config file)
    {
        files.add(file);
        Main.INSTANCE.getLogger().log(Level.INFO, file.getConfigFile().getName() + " has initialized.");
        file.load();
    }

    public List<Config> getFiles()
    {
        return files;
    }

    public void loadAll()
    {
        for(Config file : files)
        {
            file.load();
        }
    }

    public void saveAll()
    {
        for(Config file : files)
        {
            file.save();
        }
    }
}
