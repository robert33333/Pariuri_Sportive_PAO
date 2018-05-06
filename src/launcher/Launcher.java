package launcher;

import date.DataBase;
import date.Meci;
import ui.BiletUI;
import ui.CompetitieUI;
import ui.MainMenuUI;
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


        MainMenuUI.interfata();
        CompetitieUI.interfata();
        BiletUI.interfata();
    }
}
