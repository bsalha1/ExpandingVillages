/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import com.reliableplugins.villages.utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Random;

public class Village {

    private Location center;
    private HashSet<VillageHouse> houses;
    private int radius;
    private int houseDensity; // Houses per chunk
    private int growthRate;
    private int growthRadius;
    private int tick;
    private static Random random = new Random();

    public Village(Location center, int radiusInitial, int growthRate, int growthRadius, int houseDensity)
    {
        this.center = center;
        this.radius = radiusInitial;
        this.houseDensity = houseDensity;
        this.growthRate = growthRate;
        this.growthRadius = growthRadius;
        this.houses = new HashSet<>();
        this.tick = 0;

        this.center.add(0, -1, 0);

        this.center.getBlock().setType(Material.GOLD_BLOCK);
        this.buildInitialRoad();
    }

    public void tick()
    {
        if(++tick >= growthRate)
        {
            tick = 0;
            expandRoad();

            radius += growthRadius;
            int chunks = 4 * radius * radius / 256;
            int numHouses = houseDensity * chunks;
            for(int i = houses.size(); i <= numHouses; i++)
            {
                addHouse(5, 4);
            }
        }

    }

    public void addHouse(Location center, int dimension, int height)
    {
        VillageHouse house = new VillageHouse(center, dimension, height);
        houses.add(house);
        house.build();
    }

    public void addHouse(int dimension, int height)
    {
        VillageHouse house = new VillageHouse(dimension, height);
        if(findValidSpace(house))
        {
            houses.add(house);
            house.build();
        }
    }

    public void expandRoad()
    {
        for(int i = 0; i <= growthRadius; i++)
        {
            center.clone().add(radius + i, 0, -1).getBlock().setType(Material.GRAVEL);
            center.clone().add(radius + i, 0,  0).getBlock().setType(Material.GRAVEL);
            center.clone().add(radius + i, 0,  1).getBlock().setType(Material.GRAVEL);
            center.clone().add(-(radius + i), 0, -1).getBlock().setType(Material.GRAVEL);
            center.clone().add(-(radius + i), 0,  0).getBlock().setType(Material.GRAVEL);
            center.clone().add(-(radius + i), 0,  1).getBlock().setType(Material.GRAVEL);

            center.clone().add(-1, 0, radius + i).getBlock().setType(Material.GRAVEL);
            center.clone().add( 0, 0, radius + i).getBlock().setType(Material.GRAVEL);
            center.clone().add( 1, 0, radius + i).getBlock().setType(Material.GRAVEL);
            center.clone().add(-1, 0, -(radius + i)).getBlock().setType(Material.GRAVEL);
            center.clone().add( 0, 0, -(radius + i)).getBlock().setType(Material.GRAVEL);
            center.clone().add( 1, 0, -(radius + i)).getBlock().setType(Material.GRAVEL);
        }
    }

    public void buildInitialRoad()
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

    public boolean findValidSpace(VillageHouse house)
    {
        return findValidSpaceHelper(house, 0, 10);
    }

    public boolean findValidSpaceHelper(VillageHouse house, int numTries, int maxTries)
    {
        if(numTries >= maxTries)
        {
            return false;
        }
        Vector possiblePosition;

        // Place house along the X, 1 block away from the road on the Z
        if(random.nextBoolean())
        {
            possiblePosition = new Vector(
                    MathUtil.plusOrMinus() * random.nextInt(radius + 1),
                    0,
                    MathUtil.plusOrMinus() * house.getDimension());
        }
        // Place house along the Z, 1 block away from the road on the X
        else
        {
            possiblePosition = new Vector(
                    MathUtil.plusOrMinus() * house.getDimension(),
                    0,
                    MathUtil.plusOrMinus() * random.nextInt(radius + 1));
        }

        Location possibleLocation = center.clone().add(possiblePosition);
        possibleLocation = possibleLocation.getWorld().getHighestBlockAt(possibleLocation).getLocation();

        house.setCenter(possibleLocation);

        for(VillageHouse existingHouse : houses)
        {
            if (existingHouse.willOverlap(house))
            {
                return findValidSpaceHelper(house, numTries + 1, maxTries);
            }
        }
        return true;
    }


    public Location getCenter()
    {
        return center;
    }
}
