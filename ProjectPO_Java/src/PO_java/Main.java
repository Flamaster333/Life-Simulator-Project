package PO_java;

import PO_java.LifeSymulator.Init;
import PO_java.LifeSymulator.World;

public class Main {

    public static void main(String[] args) {

        World world = new World();
        Init simulator = new Init(world);
        simulator.initWorld();
    }
}
