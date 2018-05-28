package ui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import date.Bilet;
import date.Comanda;
import date.DataBase;
import date.Meci;
import date.Pariu;

public class RaportUI {
    private static final JFrame frame = MainMenuUI.frame;

    public static void interfata() {
        JMenu raport = MainMenuUI.raport;
        JMenuItem genereazaRaport= new JMenuItem("Genereaza raport");
        raport.add(genereazaRaport);
        raport.setMnemonic(KeyEvent.VK_R);

        genereazaRaport.addActionListener(actionEvent -> {
            JDialog dialogRaport = new JDialog(frame, "Generare raport", true);
            dialogRaport.setLayout(new GridLayout(2,2));
            JTextField idMeci = new JTextField();
            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancel");
            dialogRaport.add(new JLabel("ID Meci"));
            dialogRaport.add(idMeci);
            dialogRaport.add(ok);
            dialogRaport.add(cancel);

            ok.addActionListener(actionEvent1 -> {
                // Refresh meciuri
                try {
                    DataBase.oos.writeObject(new Comanda("refresh", null));
                    DataBase.meciuri = (ArrayList<Meci>) DataBase.ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // Verificare existenta id
                boolean gasit = false;
                for (Meci meci : DataBase.meciuri) {
                    if (meci.getId() == Integer.parseInt(idMeci.getText())) {
                        gasit = true;
                        break;
                    }
                }
                if (!gasit) {
                    JOptionPane.showMessageDialog(null, "ID meci nu exista!");
                }
                else {
                    // Refresh bilete
                    try {
                        DataBase.oos.writeObject(new Comanda("refresh bilet", null));
                        DataBase.bilete = (ArrayList<Bilet>) DataBase.ois.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    // Generare raport
                    int castigate = 0, pierdute = 0;
                    for(Bilet b : DataBase.bilete) {
                        for(Pariu p : b.getPariuri()) {
                            if(p.getMeci().getId() == Integer.parseInt(idMeci.getText())) {
                                if(p.getOptiune().equals(p.getMeci().getRezultat())) {
                                    castigate++;
                                }
                                else {
                                    pierdute++;
                                }
                                break;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, castigate + " pariuri castigate si " + pierdute + " pariuri pierdute.");
                    dialogRaport.dispose();
                }
            });

            cancel.addActionListener(e -> dialogRaport.dispose());

            dialogRaport.setBounds(400,400,300,150);
            dialogRaport.setResizable(false);
            dialogRaport.setVisible(true);
        });


    }
}