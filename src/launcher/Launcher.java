package launcher;

import date.DataBase;
import date.Meci;
import ui.BiletUI;
import ui.CompetitieUI;
import ui.MainMenuUI;

/**
 * Created by Rob on 06.05.2018.
 */
class Launcher {

    public static void main(String[] args) {
        //test afisare meci
        Meci meci = new Meci("Barca","Real", 1.02, 5.0, 9.0);
        DataBase.meciuri.add(meci);


        MainMenuUI.interfata();
        CompetitieUI.interfata();
        BiletUI.interfata();
    }
}
