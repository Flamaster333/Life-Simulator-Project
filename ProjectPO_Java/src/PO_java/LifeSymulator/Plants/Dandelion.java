package PO_java.LifeSymulator.Plants;

import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Plant;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

import java.util.Random;

public class Dandelion extends Plant {
    public Dandelion(World world, Cords cords)
    {
        strength = 0;
        age = 0;
        sign = '*';
        name = "Dandelion";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void action(){
        int chance = -1;
        int count = 0;
        Random rand = new Random();
        while(chance != 0 && count != 3){
            chance = rand.nextInt(100/PROBABILITY);
            count++;
        }
        if(chance == LUCKY)
        {
            Cords seedCords = new Cords(-1, -1);
            if (checkFreeFieldsNear(this.cords))
                seedCords.setCords(randomFreeFieldsNear(this.cords));
            if((seedCords.getX() != -1 && (seedCords.getY() != -1))) {
                Organism tmp = world.createOrganism(this.name, seedCords);
                world.addOrganismToBoard(tmp);
                world.addOrganismToList(tmp);
                System.out.println("New " + tmp.getName() + " seed was sown on field ("
                        + seedCords.getX() + ", " + seedCords.getY() + ")");
            }
        }
    }
}
