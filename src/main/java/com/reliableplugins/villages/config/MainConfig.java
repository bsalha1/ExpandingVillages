/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.config;

public class MainConfig extends Config
{
    public MainConfig()
    {
        super("config.yml");
    }

    @Override
    public void load()
    {

        save();
    }
}
