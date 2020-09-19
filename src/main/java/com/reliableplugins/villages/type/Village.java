/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import com.reliableplugins.villages.utils.BukkitUtil;
import com.reliableplugins.villages.utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Random;

public class Village
{
    private final Location center;
    private final HashSet<VillageStructureCuboid> structures;
    private final VillageRoad mainRoad;
    private int radius;
    private final int houseDensity; // Houses per chunk
    private final int growthRate;
    private final int growthRadius;
    private int tick;
    private static final Random random = new Random();

    public Village(Location center, int radiusInitial, int growthRate, int growthRadius, int houseDensity)
    {
        this.center = center;
        this.radius = radiusInitial;
        this.houseDensity = houseDensity;
        this.growthRate = growthRate;
        this.growthRadius = growthRadius;
        this.structures = new HashSet<>();
        this.tick = 0;

        this.center.add(0, -1, 0);

        this.mainRoad = new VillageRoad(center);
        this.initialize();
    }

    public void tick()
    {
        if(++tick >= growthRate)
        {
            tick = 0;
            mainRoad.expandCross(growthRadius, radius);

            radius += growthRadius;

            // Calculate the amount of houses that should exist due to the houseDensity
            // Add in the required amount of houses
            int chunks = 4 * radius * radius / 256; // chunks = (2*radius blocks)^2 / (16 blocks/chunk)^2
            int numHouses = houseDensity * chunks;  // houses = houses/chunk * chunks
            for(int i = structures.size(); i <= numHouses; i++)
            {
                addHouse(5, 4);
            }
        }

    }

    public void addHouse(int dimension, int height)
    {
        VillageHouse house = new VillageHouse(dimension, height);
        if(findValidSpace(house))
        {
            structures.add(house);
            house.build();
        }
    }

    public void initialize()
    {
        mainRoad.initializeCross(radius);

        VillageWell well = new VillageWell(center, 5, 5);
        well.build();
        structures.add(well);
    }

    // Returns true on the finding of a valid space for a house
    // Returns false if there is no valid space
    public boolean findValidSpace(VillageStructureCuboid house)
    {
        return findValidSpaceHelper(house, 0, 10);
    }

    public boolean findValidSpaceHelper(VillageStructureCuboid house, int numTries, int maxTries)
    {
        if(numTries >= maxTries)
        {
            return false;
        }

        boolean alongX = random.nextBoolean();  // if house is along x axis of road
        int direction = MathUtil.plusOrMinus(); // direction relative to road
        int dx, dz;

        // Place house along the X, 1 block away from the road on the Z
        if(alongX)
        {
            house.setDirection(direction == 1 ? BlockFace.NORTH : BlockFace.SOUTH);
            dx = MathUtil.plusOrMinus() * random.nextInt(radius + 1);
            dz = direction * house.getDimension();
        }
        // Place house along the Z, 1 block away from the road on the X
        else
        {
            house.setDirection(direction == 1 ? BlockFace.WEST : BlockFace.EAST);
            dx = direction * house.getDimension();
            dz = MathUtil.plusOrMinus() * random.nextInt(radius + 1);
        }

        Location possibleLocation = center.clone().add(new Vector(dx, 0, dz));
        possibleLocation = BukkitUtil.getHighestBlockAt(possibleLocation).getLocation();
        house.setCenter(possibleLocation);

        for(VillageStructureCuboid existingStructure : structures)
        {
            if (existingStructure.willOverlap(house))
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
