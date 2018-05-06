package ui;

/**
 * Created by Rob on 06.05.2018.
 */
class PariuriException extends Exception{
    final String mesaj;

    public PariuriException(String mesaj) {
        this.mesaj = mesaj;
    }
}
