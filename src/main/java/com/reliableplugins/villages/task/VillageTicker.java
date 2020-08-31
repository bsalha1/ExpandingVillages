/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.task;

import com.reliableplugins.villages.Main;
import com.reliableplugins.villages.type.Village;

public class VillageTicker extends BukkitTask
{
    public VillageTicker(long delay, long period)
    {
        super(delay, period);
    }

    @Override
    public void run()
    {
        for(Village village : Main.INSTANCE.getVillages())
        {
            village.tick();
        }
    }
}
