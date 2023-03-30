package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Animals.*;
import PO_java.LifeSymulator.Plants.*;
import PO_java.LifeSymulator.Tools.Cords;
import PO_java.LifeSymulator.Tools.OrganismsComparator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class World {
    private Organism[][] organismsBoard;
    private Organism[] organismsToKill;
    private ArrayList<Organism> organismsList;
    private MainGameFrame gameFrame;
    private Human humanPlayer;
    private int height;
    private int width;
    private int turnNum;
    private int population;
    private int deathsNumber;
    private String direction;
    private final int SPECIES = 10;

    //Konstruktor bezparametrowy
    public World()
    {
        this.height = 0;
        this.width = 0;
        this.turnNum = 0;
        this.population = 0;
        this.deathsNumber = 0;
        this.direction = "";
    }
    //Zainicjowanie tablicy organizmów
    public void createOrganismsBoard(int x, int y){
        this.organismsBoard = new Organism[y][x];
        this.organismsToKill = new Organism[y*x];
    }
    //Zainicjowanie listy organizmów
    public void createOrganismsList(){
        this.organismsList = new ArrayList<>();
    }

    //Settery
    public void setHeight(int height) {this.height = height;}
    public void setWidth(int width) {this.width = width;}
    public void setTurnNum(int turnNum) {this.turnNum = turnNum;}
    public void setPopulation(int population) {this.population = population;}
    public void setDeathsNumber(int deathsNumber) {this.deathsNumber = deathsNumber;}
    public void setDirection(String direction) {this.direction = direction;}

    //Gettery
    public Organism[][] getOrganismsBoard() {return organismsBoard;}
    public Organism[] getOrganismsToKill() {return organismsToKill;}
    public ArrayList<Organism> getOrganismsList() {return organismsList;}
    public MainGameFrame getGameFrame() {return gameFrame;}
    public int getHeight() {return height;}
    public int getWidth() {return width;}
    public int getTurnNum() {return turnNum;}
    public int getPopulation() {return population;}
    public int getDeathsNumber() { return deathsNumber;}
    public String getDirection() {return direction;}
    public Human getHumanPlayer() {return humanPlayer;}

    //inicjalizacja świata
    public void initSimulation(){
        createOrganismsBoard(width, height);
        createOrganismsList();
        gameFrame = new MainGameFrame(this);
        startingHumanPosition();
        startingOrganismsPositions();
        drawWorld();
    }

    //Metoda wykonująca rundę
    public void doRound(){
        turnNum++;
        gameFrame.getTurnLabel().setText("Turn: "+ turnNum);
        for(int i = 0; i < organismsList.size(); i++) {
            Organism tmp = organismsList.get(i);
            if(!tmp.isToKill())
            {
                tmp.getOlder();
                tmp.action();
            }
        }
        killOrganisms();
        drawWorld();
    }

    //Metoda rysująca świat
    public void drawWorld(){
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(this.organismsBoard[i][j] == null) {
                    gameFrame.getBoard()[i][j].setBackground(Color.DARK_GRAY);
                    gameFrame.getBoard()[i][j].setText("");
                }
                else
                {
                    Color color = organismsBoard[i][j].drawOrganism();
                    gameFrame.getBoard()[i][j].setBackground(color);
                    char sign = organismsBoard[i][j].getSign();
                    gameFrame.getBoard()[i][j].setText(String.valueOf(sign));
                    Color signColor = organismsBoard[i][j].drawOrganismSign();
                    gameFrame.getBoard()[i][j].setForeground(signColor);
                }
            }
        }
    }

    //Metoda zabijająca organizmy z tablicy ToKill
    public void killOrganisms()
    {
        for(int i = 0; i < deathsNumber; i++)
        {
            Organism tmp = organismsToKill[i];
            organismsList.remove(getIndex(tmp.getCords(), tmp.getName()));
            organismsToKill[i] = null;
        }
        deathsNumber = 0;
    }

    //Metoda tworząca nowy organism na podstawie nazwy i lokalizacji
    public Organism createOrganism(String name, Cords cords){
        if (name == "Wolf")
            return new Wolf(this, cords);
        else if (name == "Sheep")
            return new Sheep(this, cords);
        else if (name == "Fox")
            return new Fox(this, cords);
        else if (name == "Turtle")
            return new Turtle(this, cords);
        else if (name == "Antelope")
            return new Antelope(this, cords);
        else if (name == "Grass")
            return new Grass(this, cords);
        else if (name == "Dandelion")
            return new Dandelion(this, cords);
        else if (name == "Guarana")
            return new Guarana(this, cords);
        else if (name == "DeadlyNightshade")
            return new DeadlyNightshade(this, cords);
        else if (name == "SosnowskyHogweed")
            return new SosnowskyHogweed(this, cords);
        else if (name == "Human")
            return new Human(this, cords);
        else
            return new Sheep(this, cords);
    }

    //Metoda tworząca nowy organism na podstawie liczby i lokalizacji
    public Organism createOrganism(int number, Cords cords){
        if (number % SPECIES == 0)
            return new Wolf(this, cords);
        else if (number % SPECIES == 1)
            return new Sheep(this, cords);
        else if (number % SPECIES == 2)
            return new Fox(this, cords);
        else if (number % SPECIES == 3)
            return new Turtle(this, cords);
        else if (number % SPECIES == 4)
            return new Antelope(this, cords);
        else if (number % SPECIES == 5)
            return new Grass(this, cords);
        else if (number % SPECIES == 6)
            return new Dandelion(this, cords);
        else if (number % SPECIES == 7)
            return new Guarana(this, cords);
        else if (number % SPECIES == 8)
            return new DeadlyNightshade(this, cords);
        else if (number % SPECIES == 9)
            return new SosnowskyHogweed(this, cords);
        else
            return new Grass(this, cords); //XD
    }

    //Metoda tworząca nowy organism na podstawie znaku i lokalizacji
    public Organism createOrganism(char name, Cords cords){
        if (name == 'W')
            return new Wolf(this, cords);
        else if (name == 'S')
            return new Sheep(this, cords);
        else if (name == 'F')
            return new Fox(this, cords);
        else if (name == 'T')
            return new Turtle(this, cords);
        else if (name == 'A')
            return new Antelope(this, cords);
        else if (name == '"')
            return new Grass(this, cords);
        else if (name == '*')
            return new Dandelion(this, cords);
        else if (name == '@')
            return new Guarana(this, cords);
        else if (name == '%')
            return new DeadlyNightshade(this, cords);
        else if (name == '#')
            return new SosnowskyHogweed(this, cords);
        else if (name == 'H')
            return new Human(this, cords);
        else
            return new Sheep(this, cords);
    }

    //Metoda ustawiająca startowe pozycje organismów
    public void startingOrganismsPositions(){
        for(int i = 0; i < this.population; i++)
        {
            Cords tmpCords = startingCords();
            Organism tmpOrganism = createOrganism(i, tmpCords);
            addOrganismToBoard(tmpOrganism);
            addOrganismToList(tmpOrganism);
            System.out.println("New " + tmpOrganism.getName() + " was born on field ("
                    + tmpCords.getX() + ", " + tmpCords.getY() + ")");
        }
    }

    //Metoda ustawiająca startową pozycję człowieka
    public void startingHumanPosition(){
        Cords tmpCords = startingCords();
        humanPlayer = (Human) createOrganism("Human", tmpCords);
        addOrganismToBoard(humanPlayer);
        addOrganismToList(humanPlayer);
        System.out.println("Human was born on field ("
                + tmpCords.getX() + ", " + tmpCords.getY() + ")");
    }

    //metoda losująca puste cordy
    public Cords startingCords() {
        while(true){
            Random rand = new Random();
            int x = rand.nextInt(this.width);
            int y = rand.nextInt(this.height);
            if(this.organismsBoard[y][x] == null)
                return new Cords(x, y);
        }
    }

    //Metoda dodająca organizm do planszy
    public void addOrganismToBoard(Organism organism){
        organismsBoard[organism.getCords().getY()][organism.getCords().getX()] = organism;
    }

    //Metoda dodająca organism do tablicy ToKill
    public void addOrganismToKill(Organism organism){
        organism.setToKill(true);
        organismsToKill[deathsNumber] = organism;
        deathsNumber++;
    }

    //Metoda dodająca organism do listy
    public void addOrganismToList(Organism organism){
        organismsList.add(organism);
        organismsList.sort(new OrganismsComparator());
    }

    //Metoda czyszcząca pole planszy
    public void cleanBoardField(Cords cords){
        organismsBoard[cords.getY()][cords.getX()] = null;
    }

    //Metoda sprawdzająca czy pole jest puste czy zajęte przez jakis organizm
    public boolean isSomethingThere(Cords cords) {
        if (organismsBoard[cords.getY()][cords.getX()] == null)
            return false;
        else
            return true;
    }

    //Metoda sprawdzająca jaki organizm jest na danym polu
    public Organism whatIsThere(Cords cords) {
        return organismsBoard[cords.getY()][cords.getX()];
    }

    public int getIndex(Cords cords, String name){
        int index = 0;
        for(Organism organism : organismsList) {
            if(organism.getCords() == cords && organism.isToKill() &&
            organism.isItDead() && organism.getName() == name) {
                organism.isItDead();
                return index;
            }
            index++;
        }
        return 0;
    }

    //Metoda odpowiadająca za zapisywanie obecnej symulacji do pliku tekstowego
    public void saveToFile(JTextField saveName){
        String name = saveName.getText();
        name += ".txt";

        try{
            FileWriter file = new FileWriter(name);
            file.write(width + " " + height + " "+ turnNum + '\n');

            for (int i = 0; i < organismsBoard.length; i++) {
                for(int j =0; j < organismsBoard[0].length; j++) {
                    if(organismsBoard[i][j] != null) {
                        Organism organism = organismsBoard[i][j];
                        file.write(organism.getSign() + " " + organism.getCords().getX()
                                + " " + organism.getCords().getY() + " " + organism.getStrength() + " " + organism.getAge());
                        if (organism.getName() == "Human")
                            file.write(" " + humanPlayer.getTurnsToBoost() + " " + humanPlayer.getBoostTurns());
                        file.write('\n');
                    }
                }
            }
            file.close();
            System.out.println("Saving the world to the file has been completed successfully!!!");


        }catch (Exception e){
            System.out.println("An error during creating a file");
        }
    }

    //Metoda odpowiadająca za załadowanie symulacji z podanego pliku tekstowego
    public void loadFromFile(String name){
        try{
            File file = new File(name + ".txt");
            Scanner reader = new Scanner(file);
            String data = reader.nextLine();
            String[] strArr = data.split(" ", 0);

            width = Integer.parseInt(strArr[0]);
            height = Integer.parseInt(strArr[1]);
            turnNum = Integer.parseInt(strArr[2]);
            createOrganismsBoard(width, height);
            createOrganismsList();
            loadOrganisms(reader);
            reader.close();
            gameFrame = new MainGameFrame(this);
            drawWorld();
            System.out.println("The World from file was loaded: " + width + " " + height + " " + turnNum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Metoda odpowiadająca za załadowanie poszczególnych organizmów z pliku
    public void loadOrganisms(Scanner reader){
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] strArr = data.split(" ", 0);

            char sign = strArr[0].charAt(0);
            int x = Integer.parseInt(strArr[1]);
            int y = Integer.parseInt(strArr[2]);
            int strength = Integer.parseInt(strArr[3]);
            int age = Integer.parseInt(strArr[4]);

            if (sign == 'H') {
                humanPlayer = (Human) createOrganism("Human", new Cords(x, y));
                humanPlayer.setStrength(strength);
                humanPlayer.setAge(age);
                addOrganismToList(humanPlayer);
                addOrganismToBoard(humanPlayer);
                int turnsToBoost = Integer.parseInt(strArr[5]);
                int boostTurns = Integer.parseInt(strArr[6]);
                humanPlayer.setTurnsToBoost(turnsToBoost);
                humanPlayer.setBoostTurns(boostTurns);
                if (humanPlayer.getBoostTurns() != 0) {
                    humanPlayer.setBoostIsActive(true);
                    humanPlayer.setBoostIsAvailable(false);
                }
            } else {
                Organism tmp = createOrganism(sign, new Cords(x, y));
                tmp.setStrength(strength);
                tmp.setAge(age);
                addOrganismToList(tmp);
                addOrganismToBoard(tmp);
            }
        }
    }
}
