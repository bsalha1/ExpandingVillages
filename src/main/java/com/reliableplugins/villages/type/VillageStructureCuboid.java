/*
 * Project: PluginTemplate
 * Copyright (C) 2020 Bilal Salha <bsalha1@gmail.com>
 * GNU GPLv3 <https://www.gnu.org/licenses/gpl-3.0.en.html>
 */

package com.reliableplugins.villages.type;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public abstract class VillageStructureCuboid
{
    protected Location center;
    protected final int dimension;
    protected final int height;
    protected BlockFace direction;

    protected VillageStructureCuboid(Location center, int dimension, int height)
    {
        this.center = center;
        this.dimension = dimension;
        this.height = height;
    }

    protected VillageStructureCuboid(int dimension, int height)
    {
        this.dimension = dimension;
        this.height = height;
    }

    public abstract void build();

    public boolean willOverlap(VillageStructureCuboid structure)
    {
        // Will floors overlap
        Location thisCenter = getCenter().clone();
        Location thatCenter = structure.getCenter().clone();
        thisCenter.setY(0);
        thatCenter.setY(0);

        // Will floors or walls overlap
        return thisCenter.distance(thatCenter) <= this.dimension + 1 || thisCenter.distance(thatCenter) <= structure.dimension + 1;
    }

    public void setDirection(BlockFace direction)
    {
        this.direction = direction;
    }

    public BlockFace getDirection()
    {
        return direction;
    }

    public int getHeight()
    {
        return height;
    }

    public int getDimension()
    {
        return dimension;
    }

    public Location getCenter()
    {
        return center;
    }

    public void setCenter(Location center)
    {
        this.center = center;
    }
}
