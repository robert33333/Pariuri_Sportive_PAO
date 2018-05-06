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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pariu pariu = (Pariu) o;

        if (meci != null ? !meci.equals(pariu.meci) : pariu.meci != null) return false;
        return optiune != null ? optiune.equals(pariu.optiune) : pariu.optiune == null;
    }

    @Override
    public int hashCode() {
        int result = meci != null ? meci.hashCode() : 0;
        result = 31 * result + (optiune != null ? optiune.hashCode() : 0);
        return result;
    }
}
