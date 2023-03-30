package PO_java.LifeSymulator.Plants;

import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Plant;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class Guarana extends Plant {
    public Guarana(World world, Cords cords)
    {
        strength = 0;
        age = 0;
        sign = '@';
        name = "Guarana";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void collision(Organism occupyingOrganism){
        occupyingOrganism.gainStrength();
        world.addOrganismToKill(this);
        move(occupyingOrganism, this.cords, occupyingOrganism.getCords());
        System.out.println(occupyingOrganism.getName() + " gained +3 strength from " + this.name);
    }
}
