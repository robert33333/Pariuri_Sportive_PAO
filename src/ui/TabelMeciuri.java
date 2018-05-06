package ui;

import date.DataBase;
import date.Meci;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
public class TabelMeciuri extends AbstractTableModel {
    private final String[] nume_coloane = {"ID","Echipa 1", "Echipa 2", "1", "X", "2", "Rezultat"};

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
        return DataBase.meciuri.size();
    }

    @Override
    public String getValueAt(int row, int col){
        switch(col) {
            case 0:
                return Integer.toString(DataBase.meciuri.get(row).getId());
            case 1:
                return DataBase.meciuri.get(row).getEchipa1();
            case 2:
                return DataBase.meciuri.get(row).getEchipa2();
            case 3:
                return DataBase.meciuri.get(row).getCota1().toString();
            case 4:
                return DataBase.meciuri.get(row).getCotax().toString();
            case 5:
                return DataBase.meciuri.get(row).getCota2().toString();
            case 6:
                return DataBase.meciuri.get(row).getRezultat();
            default:
                return "null";
        }
    }

    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    public ArrayList<Meci> getList() {
        return DataBase.meciuri;

    }
}