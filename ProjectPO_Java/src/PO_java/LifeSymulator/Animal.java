package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Tools.Cords;

public class Animal extends Organism{
    @Override
    public void action() {
        int moveRange = 1;
        Cords currentCords = new Cords();
        currentCords = this.cords;
        Cords newCords = new Cords(-1, -1);
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
                move(this, newCords, currentCords); //przesuń na puste pole
        }
    }

    @Override
    public void collision(Organism occupyingOrganism) {
        if(occupyingOrganism.getName() != this.getName()) //jeśli organismy nie są takie same - będą walczyć
        {
            if(occupyingOrganism.getName() == "DeadlyNightshade")
                occupyingOrganism.collision(this);
            else if(occupyingOrganism.strength <= this.strength)
            {
                if(occupyingOrganism.getName() == "Turtle")
                    occupyingOrganism.defense(this);
                else if(occupyingOrganism.getName() == "Antelope")
                    occupyingOrganism.defense(this);
                else if(occupyingOrganism.getName() == "Guarana")
                    occupyingOrganism.collision(this);
                else if(occupyingOrganism.getName() == "SosnowskyHogweed")
                    occupyingOrganism.collision(this);
                else {
                    world.addOrganismToKill(occupyingOrganism);
                    move(this, occupyingOrganism.getCords(), this.getCords());
                    System.out.println(this.name + " ate " + occupyingOrganism.getName());
                }
            }
            else
            {
                world.addOrganismToKill(this);
                world.cleanBoardField(this.getCords());
                System.out.println(occupyingOrganism.getName() + " ate " + this.name);
            }
        }
        else
            breeding(this, occupyingOrganism.getCords());
    }

    private void breeding(Organism organism, Cords breederCords) {
        Cords offspringCords = new Cords(-1,-1);
        if (checkFreeFieldsNear(breederCords))
            offspringCords.setCords(randomFreeFieldsNear(breederCords));
        else if(checkFreeFieldsNear(organism.getCords()))
            offspringCords.setCords(randomFreeFieldsNear(organism.getCords()));

        if((offspringCords.getX()!= -1) && (offspringCords.getY() != -1))
        {
            Organism tmpOrganism = world.createOrganism(organism.getName(), offspringCords);
            world.addOrganismToBoard(tmpOrganism);
            world.addOrganismToList(tmpOrganism);
            System.out.println("New offspring of " + tmpOrganism.getName() + " was born on field ("
                    + offspringCords.getX() + ", " + offspringCords.getY() + ")");
        }
    }

    @Override
    public void defense(Organism attacker){}
}
