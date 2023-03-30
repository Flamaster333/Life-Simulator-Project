package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Tools.Colors;
import PO_java.LifeSymulator.Tools.Cords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGameFrame extends JFrame implements ActionListener, KeyListener {
    private final int MARGIN = 10;
    private final int CELL_SIZE = 45;
    private final int SIDE_BAR = 185;
    private final int TOP = 40;
    private JPanel boardPanel;
    private JPanel sidePanel;
    private World world;
    private JButton[][] board;
    private JLabel boardSize, turn;
    private JButton activate;

    public MainGameFrame(World w){
        this.world = w;
        int boardWidth = world.getWidth()*CELL_SIZE;
        int boardHeight = world.getHeight()*CELL_SIZE;
        int frameWidth = boardWidth + 4*MARGIN + SIDE_BAR + 5;
        int frameHeight = boardHeight + 2*MARGIN + TOP;
        this.board = new JButton[world.getHeight()][world.getWidth()];
        initBoard();


        boardPanel = new JPanel();
        boardPanel.setBounds(MARGIN, MARGIN, boardWidth, boardHeight);
        boardPanel.setLayout(new GridLayout(world.getHeight(),world.getWidth()));
        drawBoard();
        sidePanel = new JPanel();
        sidePanel.setBounds(2*MARGIN + boardWidth, MARGIN, SIDE_BAR, boardHeight);
        sidePanel.setLayout(null);
        sideLabels();
        sideButtons();

        add(sidePanel);
        add(boardPanel);
        setSize(frameWidth, frameHeight);
        setTitle("LIFE SYMULATOR");
        getContentPane().setBackground(new Color(224, 224, 224));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setFocusable(true);
        addKeyListener(this);
    }

    //Inicjalizacja planszy przycisków
    public void initBoard(){
        for(int i = 0; i < world.getHeight(); i++)
        {
            for(int j = 0; j < world.getWidth(); j++)
            {
                board[i][j] = new JButton("");
                board[i][j].setFont(new Font("Calibri", Font.BOLD, 11));
                board[i][j].setBackground(Color.darkGray);
                int finalI = i;
                int finalJ = j;
                board[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if(world.getOrganismsBoard()[finalI][finalJ] == null)
                        showOrganismsToChoose(finalJ, finalI);
                    }
                });
                board[i][j].addKeyListener(this);
            }
        }
    }

    //Gettery
    public JButton[][] getBoard() { return board;}
    public JLabel getTurnLabel() { return turn;}

    //Metoda dodająca przyciski do planszy
    public void drawBoard(){
        for(int i = 0; i < world.getHeight(); i++)
        {
            for(int j = 0; j < world.getWidth(); j++)
            {
                boardPanel.add(board[i][j]);
            }
        }
    }

    //Metoda rysująca etykiety na bocznym panelu
    public void sideLabels(){
        JLabel name = new JLabel("Justyna Dąbrowska");
        JLabel index = new JLabel("s185872");
        boardSize = new JLabel("Board Size: " + world.getWidth() + "x" + world.getHeight());
        turn = new JLabel("Turn: ");
        JLabel info = new JLabel("Human moves - Arrows");
        JLabel info2 = new JLabel("Moving human also");
        JLabel info3 = new JLabel("causes next turn");

        name.setBounds(35, 10, 180, 20);
        name.setForeground(Color.DARK_GRAY);
        index.setBounds(65, 30, 50, 20);
        index.setForeground(Color.DARK_GRAY);
        boardSize.setBounds(40, 180, 180, 20);
        boardSize.setForeground(Color.DARK_GRAY);
        turn.setBounds(70, 210, 180, 20);
        turn.setForeground(Color.DARK_GRAY);
        info.setBounds(25, 240, 180, 20);
        info.setForeground(Color.DARK_GRAY);
        info2.setBounds(35, 270, 180, 20);
        info2.setForeground(Color.DARK_GRAY);
        info3.setBounds(40, 290, 180, 20);
        info3.setForeground(Color.DARK_GRAY);

        sidePanel.add(turn);
        sidePanel.add(name);
        sidePanel.add(index);
        sidePanel.add(boardSize);
        sidePanel.add(info);
        sidePanel.add(info2);
        sidePanel.add(info3);
    }

    //Metoda rysująca przyciski na bocznym panelu
    public void sideButtons(){
        JButton nextTurn = new JButton("Next Turn");
        nextTurn.addActionListener(this);
        nextTurn.addKeyListener(this);
        JButton save = new JButton("Save Game");
        save.addActionListener(this);
        activate = new JButton("Activate Ability");
        activate.addActionListener(this);
        activate.addKeyListener(this);

        nextTurn.setBounds(5, 60, 175, 30);
        nextTurn.setBackground(Colors.BUTTON);
        save.setBounds(5, 100, 175, 30);
        save.setBackground(Colors.BUTTON);
        activate.setBounds(5, 140, 175, 30);
        activate.setBackground(Colors.BUTTON);

        sidePanel.add(nextTurn);
        sidePanel.add(save);
        sidePanel.add(activate);
    }

    //Okno do zapisywanie pliku ze stanem gry
    public void saveWorldWindow(){
        JFrame savingFrame = new JFrame("Saving To File");
        JLabel info= new JLabel("Enter file name: ");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField enterSave = new JTextField();
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                world.saveToFile(enterSave);
                savingFrame.dispatchEvent(new WindowEvent(savingFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        save.addKeyListener(this);

        info.setBounds(90, 20, 100, 20);
        enterSave.setBounds(90, 50, 100, 30);
        save.setBackground(Colors.BUTTON);
        save.setBounds(90, 90, 100, 40);

        savingFrame.add(info);
        savingFrame.add(enterSave);
        savingFrame.add(save);
        savingFrame.setSize(300, 200);
        savingFrame.setLayout(null);
        savingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        savingFrame.setVisible(true);
    }

    //Metoda pokazująca okienko do dodania wybranego organizmu
    public void showOrganismsToChoose(int x, int y){
        JFrame organismsFrame = new JFrame();
        JButton[] org = new JButton[10];

        for(int i = 0; i < 10; i++){
            org[i] = new JButton();
            int finalI = i;
            org[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Organism tmpOrganism = world.createOrganism(finalI, new Cords(x, y));
                    world.addOrganismToBoard(tmpOrganism);
                    world.addOrganismToList(tmpOrganism);
                    System.out.println("New offspring of " + tmpOrganism.getName() + " was born on field ("
                            + x + ", " + y + ")");
                    world.drawWorld();
                    organismsFrame.dispatchEvent(new WindowEvent(organismsFrame, WindowEvent.WINDOW_CLOSING));
                }
            });
            org[i].setBounds(10, 10 + i*35, 170, 30);
            addTextAndColorToButton(org[i], i);
            organismsFrame.add(org[i]);
        }

        organismsFrame.setSize(210, 400);
        organismsFrame.setLayout(null);
        organismsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        organismsFrame.setVisible(true);
    }

    //Metoda dodająca tekst oraz kolor przyskom zwierząt do dodania na planszę
    public void addTextAndColorToButton(JButton org, int index){
        switch(index){
            case 0: {
                org.setText("Wolf - W");
                org.setBackground(Colors.WOLF);
                org.setForeground(Color.WHITE);
            } break;
            case 1: {
                org.setText("Sheep - S");
                org.setBackground(Colors.SHEEP);
            } break;
            case 2: {
                org.setText("Fox - F");
                org.setBackground(Colors.FOX);
            } break;
            case 3: {
                org.setText("Turtle - T");
                org.setBackground(Colors.TURTLE);
            } break;
            case 4: {
                org.setText("Antelope - A");
                org.setBackground(Colors.ANTELOPE);
            } break;
            case 5: {
                org.setText("Grass - \"");
                org.setBackground(Colors.GRASS);
            } break;
            case 6: {
                org.setText("Dandelion - *");
                org.setBackground(Colors.DANDELION);
            } break;
            case 7: {
                org.setText("Guarana - @");
                org.setBackground(Colors.GUARANA);
            } break;
            case 8: {
                org.setText("DeadlyNightshade - %");
                org.setBackground(Colors.DEADLY);
                org.setForeground(Color.WHITE);
            } break;
            case 9: {
                org.setText("SosnowskyHogweed - #");
                org.setBackground(Colors.SOSNOWSKY);
            } break;
        }
    }

    //Metoda obsługująca ActionListener głównego okna
    @Override
    public void actionPerformed(ActionEvent e) {
        if("Next Turn".equals(e.getActionCommand())) {
            world.doRound();
            if(world.getHumanPlayer() != null){
                if(!world.getHumanPlayer().isBoostIsAvailable())
                    activate.setEnabled(false);
                else if(world.getHumanPlayer().getTurnsToBoost() == 0)
                    activate.setEnabled(true);
            }
        }
        else if("Save Game".equals(e.getActionCommand())){
            saveWorldWindow();
        }
        else if("Activate Ability".equals(e.getActionCommand())) {
            if(world.getHumanPlayer() != null){
                world.getHumanPlayer().boostActivate();
                world.doRound();
                activate.setEnabled(false);
            }
        }
    }

    //metody obsługujące key listener głównego okna
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
        {
            String direction = switch(keyCode){
                case KeyEvent.VK_UP -> "UP";
                case KeyEvent.VK_DOWN -> "DOWN";
                case KeyEvent.VK_RIGHT -> "RIGHT";
                case KeyEvent.VK_LEFT -> "LEFT";
                default -> "";
            };
            world.setDirection(direction);
            world.doRound();
            if(world.getHumanPlayer() != null){
                if(world.getHumanPlayer().getTurnsToBoost() > 0)
                    activate.setEnabled(false);
                else if(world.getHumanPlayer().getTurnsToBoost() == 0)
                    activate.setEnabled(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
