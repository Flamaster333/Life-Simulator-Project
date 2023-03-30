package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Tools.Colors;
import PO_java.LifeSymulator.Tools.Cords;

import java.awt.*;
import java.util.Random;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int age;
    protected char sign;
    protected boolean toKill;
    protected boolean isDead;
    protected String name;
    protected Cords cords;
    protected World world;
    protected final int DIRECTIONS = 4;

    //Konstruktor
    public Organism(){}

    //Settery
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void gainStrength() {this.strength += 3;}
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void getOlder() {this.age++;}
    public void setSign(char sign) {
        this.sign = sign;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setToKill(boolean toKill) {this.toKill = toKill;}
    public void setIsDead(boolean isDead) {this.isDead = isDead;}
    public void setCords(Cords cords) { this.cords = cords; }

    //Gettery
    public int getStrength() {
        return strength;
    }
    public int getInitiative() {
        return initiative;
    }
    public int getAge() {
        return age;
    }
    public char getSign() {return sign;}
    public String getName() {
        return name;
    }
    public boolean isToKill() {return toKill;}
    public boolean isItDead() {return isDead;}
    public Cords getCords() { return cords;}

    //Virtual
    public void action(){}
    public void collision(Organism occupyingOrganism){}
    public void defense(Organism attacker){}

    public Color drawOrganism(){
        if(this.sign == 'W')
            return Colors.WOLF;
        else if(this.sign == 'S')
            return Colors.SHEEP;
        else if(this.sign == 'F')
            return Colors.FOX;
        else if(this.sign == 'A')
            return Colors.ANTELOPE;
        else if(this.sign == 'T')
            return Colors.TURTLE;
        else if(this.sign == '@')
            return Colors.GUARANA;
        else if(this.sign == '%')
            return Colors.DEADLY;
        else if(this.sign == '*')
            return Colors.DANDELION;
        else if(this.sign == '#')
            return Colors.SOSNOWSKY;
        else if(this.sign == '"')
            return Colors.GRASS;
        else if(this.sign == 'H')
            return Colors.HUMAN;
        else
            return Color.DARK_GRAY;
    }

    //metoda ustawiająca kolor znaku organizmu na planszy
    public Color drawOrganismSign(){
        if(this.sign == 'W' || this.sign == '%')
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    //metoda sprawdzjąca czy ruch nei wychodzi poza planszę
    public boolean checkMove(Cords cords){
        if ((cords.getX() >= world.getWidth()) || (cords.getX() < 0) ||
                (cords.getY() >= world.getHeight()) || (cords.getY() < 0))
            return false;
        else
            return true;
    }

    //Metoda losująca nowe cordy
    public Cords randomNewCords(Cords cords, int moveRange)
    {
        Random rand = new Random();
        int direction = rand.nextInt(DIRECTIONS);
        Cords newCords = new Cords();
        newCords = switch (direction)
        {
            case 0 -> new Cords(cords.getX() + moveRange, cords.getY());
            case 1 -> new Cords(cords.getX() - moveRange, cords.getY());
            case 2 -> new Cords(cords.getX(), cords.getY() + moveRange);
            case 3 -> new Cords(cords.getX(), cords.getY() - moveRange);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
        return newCords;
    }

    //metoda obsługująca przemeiszczenie się organismu z jednego pola na drugie
    public void move(Organism organism, Cords newCords, Cords currentCords){
        organism.setCords(newCords);
        world.addOrganismToBoard(organism);
        world.cleanBoardField(currentCords);
    }

    // Metoda sprawdzająca czy wokół organizmu są jakiekowliek wolne pola
    public boolean checkFreeFieldsNear(Cords cords)
    {
        if (((cords.getX() + 1) < (world.getWidth() - 1)) &&
                (world.getOrganismsBoard()[cords.getY()][cords.getX() + 1] == null))
            return true;
        else if (((cords.getX() - 1) >= 0) &&
                (world.getOrganismsBoard()[cords.getY()][cords.getX() - 1]== null))
            return true;
        else if (((cords.getY() + 1) < (world.getHeight() - 1)) &&
                (world.getOrganismsBoard()[cords.getY() + 1][cords.getX()]== null))
            return true;
        else if (((cords.getY() - 1) >= 0) &&
                (world.getOrganismsBoard()[cords.getY() - 1][cords.getX()]== null))
            return true;
        else
            return false;
    }

    //Wylosowanie cordów obok podanych
    public Cords randomFreeFieldsNear(Cords cords)
    {
        int moveRange = 1;
        Cords newCords = new Cords(-1, -1);
        while ((!checkMove(newCords)) || (world.getOrganismsBoard()[newCords.getY()][newCords.getX()] != null))
        {
            newCords.setCords(randomNewCords(cords, moveRange));
        }
        return newCords;
    }

    //Możliwości ruchu
    public Cords movePossibilities(Cords cords, int option)
    {
        int x = cords.getX();
        int y = cords.getY();
        Cords newCords = switch (option)
        {
            case 0 -> new Cords(x + 1, y);
            case 1 -> new Cords(x - 1, y);
            case 2 -> new Cords(x, y + 1);
            case 3 -> new Cords(x, y - 1);
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };
        return newCords;
    }
}
