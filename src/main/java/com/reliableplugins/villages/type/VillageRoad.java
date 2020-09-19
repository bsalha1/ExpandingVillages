/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import org.bukkit.Location;
import org.bukkit.Material;

public class VillageRoad
{
    private Location center;

    public VillageRoad(Location center)
    {
        this.center = center;
    }

    public void initializeCross(int radius)
    {
        for(int i = -radius; i <= radius; i++)
        {
            center.clone().add(i, 0, -1).getBlock().setType(Material.GRAVEL);
            center.clone().add(i, 0,  0).getBlock().setType(Material.GRAVEL);
            center.clone().add(i, 0,  1).getBlock().setType(Material.GRAVEL);

            center.clone().add(-1, 0, i).getBlock().setType(Material.GRAVEL);
            center.clone().add( 0, 0, i).getBlock().setType(Material.GRAVEL);
            center.clone().add( 1, 0, i).getBlock().setType(Material.GRAVEL);
        }
    }

    public void expandCross(int growthRadius, int radius)
    {
        for(int i = 0; i <= growthRadius; i++)
        {
            center.getWorld().getHighestBlockAt(center.clone().add(radius + i, 0, -1))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(radius + i, 0,  0))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(radius + i, 0,  1))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(-(radius + i), 0, -1))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(-(radius + i), 0,  0))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(-(radius + i), 0,  1))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);

            center.getWorld().getHighestBlockAt(center.clone().add(-1, 0, radius + i))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add( 0, 0, radius + i))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add( 1, 0, radius + i))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add(-1, 0, -(radius + i)))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add( 0, 0, -(radius + i)))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
            center.getWorld().getHighestBlockAt(center.clone().add( 1, 0, -(radius + i)))
                    .getLocation().add(0, -1, 0).getBlock().setType(Material.GRAVEL);
        }
    }

}
