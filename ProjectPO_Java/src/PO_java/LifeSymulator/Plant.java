package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Tools.Cords;

import java.util.Random;

public class Plant extends Organism {
    protected final int PROBABILITY = 15;
    protected final int LUCKY = 0;

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
    }

    @Override
    public void collision(Organism occupyingOrganism){}

}
