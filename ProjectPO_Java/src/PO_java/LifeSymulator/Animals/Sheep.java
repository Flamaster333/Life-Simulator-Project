package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class Sheep extends Animal {
    public Sheep(World world, Cords cords)
    {
        strength = 4;
        initiative = 4;
        age = 0;
        sign = 'S';
        name = "Sheep";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }
}
