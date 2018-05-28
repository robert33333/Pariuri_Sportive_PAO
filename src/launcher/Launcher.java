package launcher;

import date.DataBase;
import date.Meci;
import ui.BiletUI;
import ui.CompetitieUI;
import ui.MainMenuUI;
import ui.RaportUI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
class Launcher {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket s = new Socket("localhost", 9090);
        DataBase.ois = new ObjectInputStream(s.getInputStream());
        DataBase.oos = new ObjectOutputStream(s.getOutputStream());
        DataBase.meciuri = (ArrayList<Meci>) DataBase.ois.readObject();

        MainMenuUI.interfata();
        CompetitieUI.interfata();
        BiletUI.interfata();
        RaportUI.interfata();
    }
}
