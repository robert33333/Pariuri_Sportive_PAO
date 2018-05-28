package ui;

import javax.swing.*;

public class MainMenuUI {
    public static JFrame frame;
    public static JTable tabel;
    public static JMenu competitii;
    public static JMenu bilet;
    public static JMenu raport;

    public static void interfata() {
        //afisare
        frame = new JFrame();
        JMenuBar menubar = new JMenuBar();

        tabel = new JTable(new TabelMeciuri());
        frame.setContentPane(new JPanel());
        JScrollPane scroll = new JScrollPane(tabel);
        frame.getContentPane().add(scroll);

        frame.setBounds(100,100,500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        competitii = new JMenu("Competitii");
        bilet = new JMenu("Bilet");
        raport = new JMenu("Raport");

        frame.setJMenuBar(menubar);
        menubar.add(competitii);
        menubar.add(bilet);
        menubar.add(raport);
    }
}
