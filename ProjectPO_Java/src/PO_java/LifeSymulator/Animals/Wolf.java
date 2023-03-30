package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class Wolf extends Animal {
    public Wolf(World world, Cords cords){
        strength = 9;
        initiative = 5;
        age = 0;
        sign = 'W';
        name = "Wolf";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }
}
