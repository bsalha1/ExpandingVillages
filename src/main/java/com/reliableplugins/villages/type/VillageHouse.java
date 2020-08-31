/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class VillageHouse
{

    private Material floorMaterial = Material.LOG;
    private Material wallMaterial = Material.WOOD;
    private Material ceilingMaterial = Material.STONE;

    private ArrayList<Vector> flooring;
    private ArrayList<Vector> walling;
    private ArrayList<Vector> ceiling;

    private Location center;
    private int dimension;
    private int height;

    public VillageHouse(Location center, int dimension, int height)
    {
        this.center = center;
        this.dimension = dimension;
        this.height = height;

        this.flooring = getFlooring(dimension);
        this.walling = getWalling(dimension, height - 1);
        this.ceiling = getCeiling(dimension, height);
    }

    public VillageHouse(int dimension, int height)
    {
        this.dimension = dimension;
        this.height = height;

        this.flooring = getFlooring(dimension);
        this.walling = getWalling(dimension, height - 1);
        this.ceiling = getCeiling(dimension, height);
    }

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
    }

    public boolean willOverlap(VillageHouse house)
    {
        // Will floors overlap
        Location thisCenter = this.center.clone();
        Location thatCenter = house.center.clone();
        thisCenter.setY(0);
        thatCenter.setY(0);

        // Will floors or walls overlap
        if(thisCenter.distance(thatCenter) <= this.dimension + 1 || thisCenter.distance(thatCenter) <= house.dimension + 1)
        {
            return true;
        }

        // Will ceilings overlap
//        if(this.center.getY() + this.height >= house.center.getY() || house.center.getY() + house.height >= this.center.getY())
//        {
//            return true;
//        }

        return false;
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

    public int getDimension()
    {
        return dimension;
    }

    public void setCenter(Location center)
    {
        this.center = center;
    }

    public Location getCenter()
    {
        return center;
    }
}
