/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.utils;

import org.bukkit.ChatColor;

public class BukkitUtil
{
    public static String color(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
