package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

import java.util.Random;

public class Turtle extends Animal {
    private final int CHANCES = 4;
    private final int LUCKY = 0;
    private final int DEFENSE = 5;
    public Turtle(World world, Cords cords)
    {
        strength = 2;
        initiative = 1;
        age = 0;
        sign = 'T';
        name = "Turtle";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
    }

    @Override
    public void action(){
        Random rand = new Random();
        int moveOrNot = rand.nextInt(CHANCES);
        if(moveOrNot == LUCKY){
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
                    collision(occupyingOrganism);
                }
                else
                    move(this, newCords, currentCords);
            }
        }
    }

    @Override
    public void defense(Organism attacker){
        if(attacker.getStrength() < DEFENSE)
        {
            System.out.println("Turtle repelled the attack from " + attacker.getName());
        }
        else
        {
            world.addOrganismToKill(this);
            move(attacker, this.cords, attacker.getCords());
            System.out.println(attacker.getName() + " ate Turtle");
        }
    }
}
