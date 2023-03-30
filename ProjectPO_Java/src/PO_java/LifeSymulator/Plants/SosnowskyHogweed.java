package PO_java.LifeSymulator.Plants;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Plant;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

import java.util.Random;

public class SosnowskyHogweed extends Plant {
    private final int POSSIBLEMOVES = 4;
    public SosnowskyHogweed(World world, Cords cords)
    {
        strength = 10;
        age = 0;
        sign = '#';
        name = "SosnowskyHogweed";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void action(){
        Random rand = new Random();
        int chance = rand.nextInt(100/PROBABILITY);
        if(chance == LUCKY){
            Cords seedCords = new Cords(-1, -1);
            if (checkFreeFieldsNear(this.cords))
                seedCords.setCords(randomFreeFieldsNear(this.cords));
            if((seedCords.getX() != -1 && (seedCords.getY() != -1)))
            {
                Organism tmp = world.createOrganism(this.name, seedCords);
                world.addOrganismToBoard(tmp);
                world.addOrganismToList(tmp);
                System.out.println("New " + tmp.getName() + " seed was sown on field ("
                        + seedCords.getX() + ", " + seedCords.getY() + ")");
            }
        }
        killingNear();
    }

    @Override
    public void collision(Organism occupyingOrganism){
        world.addOrganismToKill(this);
        world.addOrganismToKill(occupyingOrganism);
        world.cleanBoardField(this.cords);
        world.cleanBoardField(occupyingOrganism.getCords());
        System.out.println(this.name + " killed " + occupyingOrganism.getName() + " while they was trying to eat it");
    }

    public void killingNear(){
        int x = cords.getX();
        int y = cords.getY();
        for (int i = 0; i < POSSIBLEMOVES; i++)
        {
            Cords possMove = movePossibilities(cords, i);
            if (checkMove(possMove) && world.isSomethingThere(possMove))
            {

                if (world.whatIsThere(possMove) instanceof Animal)
                {
                    Organism organism = world.whatIsThere(possMove);
                    world.addOrganismToKill(organism);
                    world.cleanBoardField(organism.getCords());
                    if(world.getOrganismsBoard()[possMove.getY()][possMove.getY()] == null)
                        System.out.println(organism.getName() + " was killed by SosnowskyHogweed!!!!!!!!!!!!!!! " + possMove.getX() + ", " + possMove.getY());
                }
            }
        }
    }

}
