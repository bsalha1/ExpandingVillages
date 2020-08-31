/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.utils;

import java.util.Random;

public class MathUtil
{
    private static Random random = new Random();

    public static int plusOrMinus()
    {
        return random.nextBoolean() ? 1 : -1;
    }
}
