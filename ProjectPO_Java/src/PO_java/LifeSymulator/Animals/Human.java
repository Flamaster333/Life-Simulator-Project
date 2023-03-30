package PO_java.LifeSymulator.Animals;

import PO_java.LifeSymulator.Animal;
import PO_java.LifeSymulator.Organism;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.World;

import java.util.Random;

public class Human extends Animal {
    private int moveRange;
    private boolean boostIsActive;
    private boolean boostIsAvailable;
    private boolean isAlive;
    private int turnsToBoost;
    private int boostTurns;
    private final int BOOST_TIME = 5;
    private final int LOWERBOOST_TIME = 3;
    private final int LUCKY = 0;

    public Human(World world, Cords cords){
        strength = 5;
        initiative = 4;
        age = 0;
        sign = 'H';
        name = "Human";
        toKill = false;
        isDead = false;
        this.cords = cords;
        this.world = world;
        moveRange = 1;
        boostIsAvailable = true;
        boostIsActive = false;
        isAlive = true;
        turnsToBoost = 0;
        boostTurns = 0;
    }

    //Settery
    public void setBoostIsActive(boolean boostIsActive) {this.boostIsActive = boostIsActive;}
    public void setBoostIsAvailable(boolean boostIsAvailable) {this.boostIsAvailable = boostIsAvailable;}
    public void setAlive(boolean alive) {isAlive = alive;}
    public void setTurnsToBoost(int turnsToBoost) {this.turnsToBoost = turnsToBoost;}
    public void setBoostTurns(int boostTurns) {this.boostTurns = boostTurns;}

    //Gettery
    public boolean isBoostIsActive() {return boostIsActive;}
    public boolean isBoostIsAvailable() {return boostIsAvailable;}
    public boolean isAlive() {return isAlive;}
    public int getTurnsToBoost() {return turnsToBoost;}
    public int getBoostTurns() {return boostTurns;}

    @Override
    public void action(){
        boostLoading();
        Cords newCords = new Cords(cords.getX(), cords.getY());
        int x = cords.getX();
        int y = cords.getY();
        switch(world.getDirection()){
            case "UP":
                newCords.setY(y - moveRange);
                break;
            case "DOWN":
                newCords.setY(y + moveRange);
                break;
            case "RIGHT":
                newCords.setX(x + moveRange);
                break;
            case "LEFT":
                newCords.setX(x - moveRange);
                break;
        }
        if(boostIsActive == true)
            boostManagement();
        if (!newCords.equals(cords) && checkMove(newCords))
        {
            if (world.isSomethingThere(newCords))
            {
                Organism occupyingOrganism = world.whatIsThere(newCords);
                collision(occupyingOrganism);
            }
            else
                move(this, newCords, this.cords);
        }
        world.setDirection("");
    }

    //Zarządzanie umiejętnością specjalną
    private void boostManagement() {
        if (boostTurns < BOOST_TIME)
        {
            if (boostTurns < LOWERBOOST_TIME)
                moveRange = 2;
            else
            {
                Random rand = new Random();
                int chance = rand.nextInt(2);
                if (chance == LUCKY)
                    moveRange = 2;
                else
                    moveRange = 1;
            }
            ++boostTurns;
        }
        else
        {
            boostIsActive = false;
            boostIsAvailable = false;
            turnsToBoost = 5;
            boostTurns = 0;
            moveRange = 1;
            //world.getGameFrame().disableActivate();
        }
    }

    //ładowanie sie specjalnej umiejętności
    private void boostLoading() {
        if (turnsToBoost > 0 && !boostIsActive)
            --turnsToBoost;
        else if (turnsToBoost == 0 && !boostIsActive) {
            boostIsAvailable = true;
            //world.getGameFrame().enableActivate();
        }
    }

    //Aktywowanie specjalnej umijętności
    public void boostActivate()
    {
        if (boostIsAvailable == true)
        {
            boostIsAvailable = false;
            boostIsActive = true;
            System.out.println("Human just activated special ability (Antelope speed)");
        }
        else
        {
            System.out.println("Human can't activate special ability! You have to wait: " + turnsToBoost + " turns");
        }
    }

}
