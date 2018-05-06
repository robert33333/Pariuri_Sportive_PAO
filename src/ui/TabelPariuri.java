package ui;

import date.DataBase;
import date.Pariu;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
class TabelPariuri extends AbstractTableModel {
    private final String[] nume_coloane = {"ID Meci","Optiune","Cota"};

    @Override
    public int getColumnCount() {
        return nume_coloane.length;
    }

    @Override
    public String getColumnName(int index) {
        return nume_coloane[index];
    }

    @Override
    public int getRowCount() {
        return DataBase.biletCurent.getPariuri().size();
    }

    @Override
    public String getValueAt(int row, int col){
        switch(col) {
            case 0:
                return Integer.toString(DataBase.biletCurent.getPariuri().get(row).getMeci().getId());
            case 1:
                return DataBase.biletCurent.getPariuri().get(row).getOptiune();
            case 2:
                switch (DataBase.biletCurent.getPariuri().get(row).getOptiune()) {
                    case "1" :
                        return DataBase.biletCurent.getPariuri().get(row).getMeci().getCota1().toString();
                    case "x" :
                        return DataBase.biletCurent.getPariuri().get(row).getMeci().getCotax().toString();
                    case "2" :
                        return DataBase.biletCurent.getPariuri().get(row).getMeci().getCota2().toString();
                }
            default:
                return "null";
        }

    }

    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    public ArrayList<Pariu> getList() {
        return DataBase.biletCurent.getPariuri();

    }
}
