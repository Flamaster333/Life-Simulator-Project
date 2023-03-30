package PO_java.LifeSymulator.Plants;

import PO_java.LifeSymulator.Plant;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class Grass extends Plant {
    public Grass(World world, Cords cords)
    {
        strength = 0;
        age = 0;
        sign = '"';
        name = "Grass";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }
}
