package PO_java.LifeSymulator.Plants;

import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Plant;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class DeadlyNightshade extends Plant {
    public DeadlyNightshade(World world, Cords cords)
    {
        strength = 99;
        age = 0;
        sign = '%';
        name = "DeadlyNightshade";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void collision(Organism occupyingOrganism){
        world.addOrganismToKill(this);
        world.addOrganismToKill(occupyingOrganism);
        world.cleanBoardField(this.cords);
        world.cleanBoardField(occupyingOrganism.getCords());
        System.out.println(this.name + " killed " + occupyingOrganism.getName() + " while they was trying to eat it");
    }
}
