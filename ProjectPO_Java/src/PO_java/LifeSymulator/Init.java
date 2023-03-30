package PO_java.LifeSymulator;

import PO_java.LifeSymulator.Tools.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Init implements ActionListener {
    private World world;
    private JFrame frame;
    private JFrame ngFrame;

    public Init(World world)
    {
        this.world = world;
    }

    //Inicjalizacja świata symulującego życie
    public void initWorld ()
    {
        frame = new JFrame("Start Life Simulation");
        JButton newGameBtn = new JButton("New Game");
        JButton gameFromFile = new JButton("Load Game From File");
        JLabel title = new JLabel("LIFE SIMULATOR");

        title.setBounds(95, 20, 240, 20);
        title.setForeground(Color.DARK_GRAY);
        newGameBtn.setBackground(Colors.BUTTON);
        newGameBtn.setBounds(20, 50, 240, 85);
        newGameBtn.addActionListener(this);
        gameFromFile.setBackground(Colors.BUTTON);
        gameFromFile.setBounds(20, 155, 240, 85);
        gameFromFile.addActionListener(this);

        frame.add(title);
        frame.add(newGameBtn);
        frame.add(gameFromFile);
        frame.setSize(300, 320);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //Wyświetlenie okna odpowiedizalnego za wpisanie parametrów nowej gry
    public void newGameWindow(){
        ngFrame = new JFrame("Enter New Simulation Parameters");
        JLabel enter = new JLabel("ENTER BOARD DIMENSIONS ");
        JLabel wWidth = new JLabel("Width:");
        JLabel wHeight = new JLabel("Height:");
        JLabel wPercent = new JLabel("Size of the population as a");
        JLabel wPercent2 = new JLabel("percentage of the board:");
        ArrayList<JTextField> textFields = new ArrayList<>();
        JTextField width = new JTextField();
        textFields.add(width);
        JTextField height = new JTextField();
        textFields.add(height);
        JTextField percent = new JTextField();
        textFields.add(percent);
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loadNewWorld(textFields, ngFrame);
            }
        });

        enter.setForeground(Color.DARK_GRAY);
        enter.setBounds(65, 10, 240, 20);
        wWidth.setForeground(Color.DARK_GRAY);
        wWidth.setBounds(120, 40, 100, 20);
        width.setBounds(30, 60, 220, 20);
        wHeight.setForeground(Color.DARK_GRAY);
        wHeight.setBounds(120, 90, 100, 20);
        height.setBounds(30, 110, 220, 20);
        wPercent.setForeground(Color.DARK_GRAY);
        wPercent.setBounds(65, 140, 200, 20);
        wPercent2.setForeground(Color.DARK_GRAY);
        wPercent2.setBounds(70, 160, 200, 20);
        percent.setBounds(30, 180, 220, 20);
        save.setBackground(Colors.BUTTON);
        save.setBounds(90, 220, 100, 40);

        ngFrame.add(enter);
        ngFrame.add(wWidth);
        ngFrame.add(width);
        ngFrame.add(wHeight);
        ngFrame.add(height);
        ngFrame.add(wPercent);
        ngFrame.add(wPercent2);
        ngFrame.add(percent);
        ngFrame.add(save);
        ngFrame.setSize(300, 320);
        ngFrame.setLayout(null);
        ngFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ngFrame.setVisible(true);
    }

    //Metoda odpowiadająca za załadowanie nowego świata z wpisanymi parametrami
    private void loadNewWorld(ArrayList<JTextField> textFields, JFrame ngFrame) {
        JTextField tfwidth = textFields.get(0);
        JTextField tfheight = textFields.get(1);
        JTextField tfpercent = textFields.get(2);
        int w, h, p;

        if(!tfwidth.getText().equals("") && !tfheight.getText().equals("")
                && !tfpercent.getText().equals("")) {
            try{
                w = Integer.parseInt(tfwidth.getText());
                h = Integer.parseInt(tfheight.getText());
                p = Integer.parseInt(tfpercent.getText());
            } catch(Exception e){
            errorLabel("Wrong values: only numbers are acceptable!");
            throw new IllegalArgumentException("Wrong values: only numbers are acceptable!");
            }
            if(w > 0 && h > 0 && p > 0 && p <= 100)
            {
                world.setWidth(w);
                world.setHeight(h);
                world.setPopulation((p*w*h)/100);
                world.initSimulation();
                ngFrame.setVisible(false);
                ngFrame.dispose();
            }
            else
                errorLabel("Wrong numbers!");
        }
        else
            errorLabel("There Are some empty fields!");

    }

    //Metod aodpowiedzialna za wyswietlanie okienka z błędem
    private void errorLabel(String s) {
        JFrame errorFrame = new JFrame("Error");
        int length = s.length();
        JLabel error= new JLabel(s);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setForeground(new Color(153, 0, 0));

        error.setFont(new Font("Calibri", Font.BOLD, 18));

        errorFrame.add(error);
        errorFrame.setSize(length*10, 100);
        errorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        errorFrame.setVisible(true);
    }

    //Metoda wyświetlająca okno do wpisania nazwy pliku z którego będziemy wczytywać świat
    public void loadFromFileWindow(){
        JFrame loadingFrame = new JFrame("Loading Game From File");
        JLabel info= new JLabel("Enter file name: ");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField enterSave = new JTextField();
        JButton save = new JButton("Load");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                world.loadFromFile(enterSave.getText());
                loadingFrame.dispatchEvent(new WindowEvent(loadingFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        info.setBounds(90, 20, 100, 20);
        enterSave.setBounds(90, 50, 100, 30);
        save.setBackground(new Color(0, 102, 102));
        save.setBounds(90, 90, 100, 40);

        loadingFrame.add(info);
        loadingFrame.add(enterSave);
        loadingFrame.add(save);
        loadingFrame.setSize(300, 200);
        loadingFrame.setLayout(null);
        loadingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loadingFrame.setVisible(true);
    }

    //Metoda odpowiadająca za ActionEventy przy inicjalizacji świata
    @Override
    public void actionPerformed(ActionEvent e) {
        if("New Game".equals(e.getActionCommand()))
        {
            frame.setVisible(false);
            frame.dispose();
            newGameWindow();
        }
        else if("Load Game From File".equals(e.getActionCommand()))
        {
            loadFromFileWindow();
        }
    }
}
