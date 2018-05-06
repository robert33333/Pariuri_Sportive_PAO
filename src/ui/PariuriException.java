package ui;

/**
 * Created by Rob on 06.05.2018.
 */
public class PariuriException extends Exception{
    String mesaj;

    public PariuriException(String mesaj) {
        this.mesaj = mesaj;
    }
}
