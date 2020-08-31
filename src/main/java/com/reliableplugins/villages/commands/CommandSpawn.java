/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.commands;

import com.reliableplugins.villages.Main;
import com.reliableplugins.villages.annotation.CommandBuilder;
import com.reliableplugins.villages.type.Village;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "spawn", permission = "villages.spawn", playerRequired = true, description = "Spawns a new village")
public class CommandSpawn extends Command{
    @Override
    public void execute(CommandSender executor, String[] args) {
        Player player = (Player) executor;
        Village village = new Village(player.getLocation(), 16, 80, 2, 2);
        Main.INSTANCE.addVillage(village);
    }
}
