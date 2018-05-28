package date;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
public class DataBase {
    public static ArrayList<Meci> meciuri = new ArrayList<>();
    public static ArrayList<Bilet> bilete = new ArrayList<>();
    public static Bilet biletCurent;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
}
