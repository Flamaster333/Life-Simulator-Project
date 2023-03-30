package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

import java.util.Random;

public class Antelope extends Animal {
    private final int CHANCES = 2;
    private final int LUCKY = 0;
    public Antelope(World world, Cords cords)
    {
        strength = 4;
        initiative = 4;
        age = 0;
        sign = 'A';
        name = "Antelope";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void action(){
        int moveRange = 2; // zmiana zasiegu ruchu na 2
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
                collision(occupyingOrganism);
            }
            else
                move(this, newCords, currentCords);
        }
    }

    @Override
    public void defense(Organism attacker){
        Random rand = new Random();
        int chance = rand.nextInt(CHANCES);
        if(chance == LUCKY && checkFreeFieldsNear(this.cords)){
            Cords newCords = randomFreeFieldsNear(this.cords);
            move(this, newCords, this.cords);
            System.out.println("Antelope escaped from " + attacker.getName());
        }
        else{
            world.addOrganismToKill((this));
            move(attacker, this.cords, attacker.getCords());
            System.out.println(attacker.getName() + " ate Antelope");
        }
    }
}
