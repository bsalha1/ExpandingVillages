/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.Door;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class VillageHouse extends VillageStructureCuboid
{

    private final Material floorMaterial = Material.LOG;
    private final Material wallMaterial = Material.WOOD;
    private final Material ceilingMaterial = Material.STONE;

    private final ArrayList<Vector> flooring;
    private final ArrayList<Vector> walling;
    private final ArrayList<Vector> ceiling;

    public VillageHouse(Location center, int dimension, int height)
    {
        super(center, dimension, height);

        this.flooring = getFlooring(dimension);
        this.walling = getWalling(dimension, height - 1);
        this.ceiling = getCeiling(dimension, height);
    }

    public VillageHouse(int dimension, int height)
    {
        super(dimension, height);

        this.flooring = getFlooring(dimension);
        this.walling = getWalling(dimension, height - 1);
        this.ceiling = getCeiling(dimension, height);
    }

    @Override
    public void build()
    {
        List<Location> flooring = getFloorLocations();
        List<Location> walling = getWallLocations();
        List<Location> ceiling = getCeilingLocations();

        for (Location location : flooring)
        {
            location.getBlock().setType(floorMaterial);
        }
        for (Location location : walling)
        {
            location.getBlock().setType(wallMaterial);
        }
        for (Location location : ceiling)
        {
            location.getBlock().setType(ceilingMaterial);
        }

        buildDoor();
    }

    public void buildDoor()
    {
        Location lowerDoorLocation = this.center.clone().add(
                this.direction.getModX() * dimension / 2,
                this.direction.getModY() * dimension / 2 + 1,
                this.direction.getModZ() * dimension / 2);

        byte lowerData;
        byte upperData = 0x8;
        switch (direction.getOppositeFace())
        {
            case EAST:
                lowerData = 0x0;
                break;
            case SOUTH:
                lowerData = 0x1;
                break;
            case WEST:
                lowerData = 0x2;
                break;
            case NORTH:
            default:
                lowerData = 0x3;
                break;
        }
        lowerDoorLocation.getBlock().setTypeIdAndData(64, lowerData, true);
        lowerDoorLocation.clone().add(0, 1, 0).getBlock().setTypeIdAndData(64, upperData, true);
    }

    public List<Location> getFloorLocations()
    {
        ArrayList<Location> floorLocations = new ArrayList<>();
        for (Vector vector : flooring)
        {
            Location base = center.clone();
            floorLocations.add(base.add(vector));
        }
        return floorLocations;
    }

    public List<Location> getWallLocations()
    {
        ArrayList<Location> floorLocations = new ArrayList<>();
        for (Vector vector : walling)
        {
            Location base = center.clone();
            floorLocations.add(base.add(vector));
        }
        return floorLocations;
    }

    public List<Location> getCeilingLocations()
    {
        ArrayList<Location> floorLocations = new ArrayList<>();
        for (Vector vector : ceiling)
        {
            Location base = center.clone();
            floorLocations.add(base.add(vector));
        }
        return floorLocations;
    }

    private static ArrayList<Vector> getFlooring(int dimension)
    {
        ArrayList<Vector> flooring = new ArrayList<>(dimension * dimension);
        for (int i = - dimension / 2; i <= dimension / 2; i++)
            for (int j = - dimension / 2; j <= dimension / 2; j++)
            {
                flooring.add(new Vector(j, 0, i));
            }
        return flooring;
    }

    private static ArrayList<Vector> getCeiling(int dimension, int height)
    {
        ArrayList<Vector> ceiling = new ArrayList<>(dimension * dimension);
        for (int i = - dimension / 2; i <= dimension / 2; i++)
            for (int j = - dimension / 2; j <= dimension / 2; j++)
                ceiling.add(new Vector(j, height, i));
        return ceiling;
    }

    private static ArrayList<Vector> getWalling(int dimension, int height)
    {
        ArrayList<Vector> walling = new ArrayList<>(dimension * dimension - (dimension - 2) * (dimension - 2));
        for (int i = - dimension / 2; i <= dimension / 2; i++)
            for (int j = - dimension / 2; j <= dimension / 2; j++)
                for(int k = 1; k <= height; k++)
                {
                    // If on boundary
                    if (i == dimension / 2 || j == dimension / 2 ||
                            i == -dimension / 2 || j == -dimension / 2)
                    {
                        walling.add(new Vector(j, k, i));
                    }
                }
        return walling;
    }
}
