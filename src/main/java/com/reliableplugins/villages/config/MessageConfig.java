/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.config;

import com.reliableplugins.villages.utils.BukkitUtil;

public class MessageConfig extends Config
{
    public MessageConfig()
    {
        super("messages.yml");
    }

    @Override
    public void load()
    {
        for(Message message : Message.values())
        {
            message.setMessage(getString(BukkitUtil.color(message.getConfigKey()),
                    message.getLoneMessage().replace("§", "&")));
        }

        save();
    }
}
