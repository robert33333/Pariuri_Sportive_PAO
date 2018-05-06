package date;

/**
 * Created by Rob on 06.05.2018.
 */
public class Pariu {
    private Meci meci;
    private String optiune;

    public Pariu(Meci meci, String optiune) {
        this.meci = meci;
        this.optiune = optiune;
    }

    public Meci getMeci() {
        return meci;
    }

    public void setMeci(Meci meci) {
        this.meci = meci;
    }

    public String getOptiune() {
        return optiune;
    }

    public void setOptiune(String optiune) {
        this.optiune = optiune;
    }
}
