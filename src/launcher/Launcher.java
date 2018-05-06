package launcher;

import date.DataBase;
import date.Meci;
import ui.Competitie;
import ui.TabelMeciuri;

import javax.swing.*;

/**
 * Created by Rob on 06.05.2018.
 */
public class Launcher {
    public static JFrame frame;
    public static JMenuBar menuBar;
    public static JTable tabel;

    public static void main(String[] args) {
        //test afisare meci
        Meci meci = new Meci("Barca","Real", 1.02, 5.0, 9.0);
        DataBase.meciuri.add(meci);


        //afisare
        frame = new JFrame();
        menuBar = new JMenuBar();

        tabel = new JTable(new TabelMeciuri());
        frame.setContentPane(new JPanel());
        JScrollPane scroll = new JScrollPane(tabel);
        frame.getContentPane().add(scroll);

        frame.setBounds(100,100,500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Competitie.interfata();
    }
}
