/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.config;

import com.reliableplugins.villages.Main;
import com.reliableplugins.villages.utils.BukkitUtil;

public enum Message
{
    ERROR_NO_PERMS("err-no-perms", "&cYou do not have access to this command!"),
    ERROR_NOT_PLAYER("err-not-player", "&cOnly players may execute this command.");

    private String message;
    private final String configKey;
    private static final String header;

    static
    {
        header = Main.INSTANCE.getMessageConfig().getString("message-header", "&8(&dPrinter&8) ");
    }

    Message(String configKey, String message)
    {
        this.configKey = configKey;
        this.message = message;
    }

    public String getLoneMessage()
    {
        return message;
    }

    public String getRawMessage()
    {
        return header + message;
    }

    public String getMessage()
    {
        return BukkitUtil.color(header + message);
    }

    public void setMessage(String message)
    {
        this.message = BukkitUtil.color(message);
    }

    public String getConfigKey()
    {
        return configKey;
    }
}
