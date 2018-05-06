package date;

/**
 * Created by Rob on 06.05.2018.
 */
public class Meci {
    private static int idNum;
    private int id;
    private String echipa1, echipa2;
    private String rezultat = "null";
    private Double cota1, cota2, cotax;

    public Meci(String echipa1, String echipa2, Double cota1, Double cotax, Double cota2) {
        idNum++;
        this.id = idNum;
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
        this.cota1 = cota1;
        this.cota2 = cota2;
        this.cotax = cotax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEchipa1() {
        return echipa1;
    }

    public void setEchipa1(String echipa1) {
        this.echipa1 = echipa1;
    }

    public String getEchipa2() {
        return echipa2;
    }

    public void setEchipa2(String echipa2) {
        this.echipa2 = echipa2;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public Double getCota1() {
        return cota1;
    }

    public void setCota1(Double cota1) {
        this.cota1 = cota1;
    }

    public Double getCota2() {
        return cota2;
    }

    public void setCota2(Double cota2) {
        this.cota2 = cota2;
    }

    public Double getCotax() {
        return cotax;
    }

    public void setCotax(Double cotax) {
        this.cotax = cotax;
    }
}
