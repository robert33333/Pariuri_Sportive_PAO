package date;

import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
public class Bilet {
    private static int idNum;
    private int id;
    private ArrayList<Pariu> pariuri = new ArrayList<>();
    private double bani;

    public ArrayList<Pariu> getPariuri() {
        return pariuri;
    }

    public void setPariuri(ArrayList<Pariu> pariuri) {
        this.pariuri = pariuri;
    }

    public Bilet(ArrayList<Pariu> pariuri) {
        idNum++;
        this.id = idNum;
        this.pariuri = pariuri;
        this.bani = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBani() {
        return bani;
    }

    public void setBani(double bani) {
        this.bani = bani;
    }

    public double calculeazaCastigPotential() {
        double castig = bani;
        for (Pariu pariu : pariuri) {
            switch (pariu.getOptiune()) {
                case "1" :
                    castig *= pariu.getMeci().getCota1();
                    break;
                case "x" :
                    castig *= pariu.getMeci().getCotax();
                    break;
                case "2" :
                    castig *= pariu.getMeci().getCota2();
                    break;
            }
        }
        return castig;
    }

    public boolean verificaBilet() {
        for (Pariu pariu : pariuri) {
            if (!pariu.getOptiune().equals(pariu.getMeci().getRezultat()))
                return false;
        }
        return true;
    }
}
