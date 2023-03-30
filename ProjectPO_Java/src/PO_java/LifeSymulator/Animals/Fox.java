package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

public class Fox extends Animal {
    public Fox(World world, Cords cords)
    {
        strength = 3;
        initiative = 7;
        age = 0;
        sign = 'F';
        name = "Fox";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void action(){
        int moveRange = 1;
        Cords currentCords = this.cords;
        Cords newCords;
        newCords = new Cords(-1, -1);
        while (!checkMove(newCords))
            newCords.setCords(randomNewCords(currentCords, moveRange));
        if (checkMove(newCords))
        {
            if (world.isSomethingThere(newCords))
            {
                Organism occupyingOrganism = world.whatIsThere(newCords);
                if(occupyingOrganism.getStrength() <= this.strength)
                    collision(occupyingOrganism);
            }
            else
                move(this, newCords, currentCords);
        }
    }
}
