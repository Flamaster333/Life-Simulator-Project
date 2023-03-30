package PO_java.LifeSymulator.Tools;

import PO_java.LifeSymulator.Organism;

import java.util.Comparator;

public class OrganismsComparator implements Comparator<Organism> {

    @Override
    public int compare(Organism o1, Organism o2) {
        if(o1.getInitiative() != o2.getInitiative())
            return o1.getInitiative() - o2.getInitiative();
        else
            return o1.getAge() - o2.getAge();
    }
}
