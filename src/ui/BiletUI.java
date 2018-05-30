package ui;

import date.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import static ui.MainMenuUI.tabel;

/**
 * Created by Rob on 06.05.2018.
 */
public class BiletUI {
    private static final JFrame frame = MainMenuUI.frame;

    private static void updateCastigMiza(JLabel miza, JLabel castig) {
        miza.setText(Double.toString(DataBase.biletCurent.getBani()));
        castig.setText(Double.toString(DataBase.biletCurent.calculeazaCastigPotential()));
    }

    public static void interfata() {
        //JMenuBar menubar = MainMenuUI.menubar;
        JMenu bilet = MainMenuUI.bilet;
        JMenuItem inregistrareSimplu = new JMenuItem("Inregistrare bilet simplu");
        JMenuItem inregistrareCompus = new JMenuItem("Inregistrare bilet compus");
        JMenuItem validareBilet = new JMenuItem("Validare bilet");

        bilet.add(inregistrareSimplu);
        bilet.add(inregistrareCompus);
        bilet.add(validareBilet);
        bilet.setMnemonic(KeyEvent.VK_B);
        inregistrareCompus.addActionListener(actionEvent -> interfataAdaugareBilet("Compus"));

        inregistrareSimplu.addActionListener(actionEvent -> interfataAdaugareBilet("Simplu"));

        validareBilet.addActionListener(actionEvent -> {
            JDialog dialogValidare = new JDialog(frame,"Validare bilet", true);
            dialogValidare.setLayout(new GridLayout(2,2));
            JTextField idBilet = new JTextField();
            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancel");
            dialogValidare.add(new JLabel("ID Bilet"));
            dialogValidare.add(idBilet);
            dialogValidare.add(ok);
            dialogValidare.add(cancel);

            ok.addActionListener(actionEvent1 -> {
                try {
                    DataBase.oos.writeObject(new Comanda("refresh", null));
                    DataBase.meciuri = (ArrayList<Meci>) DataBase.ois.readObject();
                    tabel.updateUI();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    DataBase.oos.writeObject(new Comanda("refresh bilet", null));
                    DataBase.bilete = (ArrayList<Bilet>) DataBase.ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                for (Bilet bilet1 : DataBase.bilete) {
                    if (bilet1.getId() == Integer.parseInt(idBilet.getText())) {
                        if (bilet1.verificaBilet()) {
                            if (!bilet1.isDejaValidat()) {
                                JOptionPane.showMessageDialog(null, "Biletul este valid!");
                                bilet1.setDejaValidat();
                                dialogValidare.dispose();
                                try {
                                    DataBase.oos.writeObject(new Comanda("validare bilet", bilet1.getId()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Biletul a fost deja validat!");
                                dialogValidare.dispose();
                            }
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Biletul NU este valid!");
                dialogValidare.dispose();
            });

            cancel.addActionListener(e -> dialogValidare.dispose());

            dialogValidare.setBounds(400,400,300,150);
            dialogValidare.setResizable(false);
            dialogValidare.setVisible(true);
        });
    }

    private static void interfataAdaugareBilet(String tip) {
        //creere bilet
        ArrayList<Pariu> pariuriCurente = new ArrayList<>();
        DataBase.biletCurent = new Bilet(0, pariuriCurente, 0);

        //afisare
        JDialog dialogPariuri = new JDialog(frame,"Compozitie bilet",true);
        dialogPariuri.setLayout(new GridLayout(2,2));
        JTable tabelPariuri = new JTable(new TabelPariuri());
        dialogPariuri.setContentPane(new JPanel());
        JScrollPane scroll = new JScrollPane(tabelPariuri);
        dialogPariuri.getContentPane().add(scroll);

        JMenuBar menubarPariuri = new JMenuBar();
        dialogPariuri.setJMenuBar(menubarPariuri);
        JMenu optiuni = new JMenu("Optiuni");
        JMenuItem adaugaPariu = new JMenuItem("Adaugare pariu");
        JMenuItem stergePariu = new JMenuItem("Stergere pariu");
        JMenuItem seteazaMiza = new JMenuItem("Seteaza miza");
        menubarPariuri.add(optiuni);
        optiuni.add(adaugaPariu);
        optiuni.add(stergePariu);
        optiuni.add(seteazaMiza);

        JLabel miza = new JLabel("0.0");
        JLabel castig = new JLabel("0.0");

        dialogPariuri.add(new JLabel("Miza: "));
        dialogPariuri.add(miza);
        dialogPariuri.add(new JLabel("Castig potential:"));
        dialogPariuri.add(castig);

        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        dialogPariuri.add(ok);
        dialogPariuri.add(cancel);



        adaugaPariu.addActionListener(actionEvent -> {
            JDialog dialog = new JDialog(frame, "Adaugare pariu", true);
            dialog.setLayout(new GridLayout(3,2));
            JTextField meciID = new JTextField();
            JTextField optiune = new JTextField();
            JButton ok13 = new JButton("Ok");
            JButton cancel13 = new JButton("Cancel");

            dialog.add(new JLabel("ID Meci"));
            dialog.add(meciID);
            dialog.add(new JLabel("Optiune"));
            dialog.add(optiune);
            dialog.add(ok13);
            dialog.add(cancel13);

            ok13.addActionListener(e -> {
                try {
                    if (optiune.getText().equals("X")) {
                        optiune.setText("x");
                    }
                    if (tip.equals("Simplu"))
                        if (DataBase.biletCurent.getPariuri().size() > 0)
                            throw new PariuriException("Pentru mai multe meciuri selectati bilet compus!");
                    if (!optiune.getText().equals("1"))
                        if (!optiune.getText().equals("2"))
                            if (!optiune.getText().equals("x"))
                                throw new PariuriException("Completati corect datele!");
                    boolean gasit = false;
                    for (Meci meci : DataBase.meciuri) {
                        if (meci.getId() == Integer.parseInt(meciID.getText())) {
                            Pariu pariu = new Pariu(meci, optiune.getText());
                            //verificare id exista deja
                            for (Pariu pariu1 : DataBase.biletCurent.getPariuri()) {
                                if (pariu.getMeci().getId() == pariu1.getMeci().getId())
                                    throw new PariuriException("Meci deja selectat!");
                            }
                            if (meci != null && meci.getRezultat() != null)
                                if (!meci.getRezultat().equals("null")) {
                                    throw new PariuriException("Meciul a fost deja jucat!");
                                }
                            DataBase.biletCurent.getPariuri().add(pariu);
                            updateCastigMiza(miza, castig);
                            tabelPariuri.updateUI();
                            dialog.dispose();
                            gasit = true;
                            break;
                        }
                    }
                    if (!gasit) {
                        throw new PariuriException("ID Meci nu exista!");
                    }
                } catch (PariuriException exception) {
                    JOptionPane.showMessageDialog(null, exception.mesaj);
                } catch (Exception exceptionGeneric) {
                    exceptionGeneric.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Completeaza corect datele!");
                }
            });

            cancel13.addActionListener(e -> dialog.dispose());

            dialog.setBounds(500,500,300,150);
            dialog.setVisible(true);

        });

        stergePariu.addActionListener(actionEvent -> {
            JDialog dialog = new JDialog(frame, "Stergere pariu", true);
            dialog.setLayout(new GridLayout(2,2));
            JTextField meciID = new JTextField();
            JButton ok12 = new JButton("Ok");
            JButton cancel12 = new JButton("Cancel");

            dialog.add(new JLabel("ID Meci"));
            dialog.add(meciID);
            dialog.add(ok12);
            dialog.add(cancel12);

            ok12.addActionListener(actionEvent12 -> {
                try {
                    boolean gasit = false;
                    for (Pariu pariu : DataBase.biletCurent.getPariuri()) {
                        if (pariu.getMeci().getId() == Integer.parseInt(meciID.getText())) {
                            DataBase.biletCurent.getPariuri().remove(pariu);
                            gasit = true;
                            dialog.dispose();
                            updateCastigMiza(miza, castig);
                            tabelPariuri.updateUI();
                            break;
                        }
                    }
                    if (!gasit) {
                        throw new PariuriException("ID Meci nu a fost gaist");
                    }
                } catch (PariuriException exception) {
                    JOptionPane.showMessageDialog(null, exception.mesaj);
                } catch (Exception exceptionGeneric) {
                    JOptionPane.showMessageDialog(null, "Completeaza corect datele!");
                }

            });

            cancel12.addActionListener(e -> dialog.dispose());

            dialog.setBounds(500,500,300,150);
            dialog.setVisible(true);
        });

        cancel.addActionListener(e -> dialogPariuri.dispose());

        seteazaMiza.addActionListener(actionEvent -> {
            JDialog dialog = new JDialog(frame, "Seteaza Miza", true);
            dialog.setLayout(new GridLayout(2,2));
            JTextField mizaText = new JTextField();
            JButton ok1 = new JButton("Ok");
            JButton cancel1 = new JButton("Cancel");

            dialog.add(new JLabel("Miza"));
            dialog.add(mizaText);
            dialog.add(ok1);
            dialog.add(cancel1);

            ok1.addActionListener(actionEvent1 -> {
                try {
                    DataBase.biletCurent.setBani(Double.parseDouble(mizaText.getText()));
                    updateCastigMiza(miza, castig);
                    dialog.dispose();
                } catch (Exception exceptionGeneric) {
                    JOptionPane.showMessageDialog(null, "Completeaza corect datele!");
                }
            });

            cancel1.addActionListener(e -> dialog.dispose());

            dialog.setBounds(500,500,300,150);
            dialog.setVisible(true);
        });

        ok.addActionListener(actionEvent -> {
            if (DataBase.biletCurent.getPariuri().size() == 0) {
                JOptionPane.showMessageDialog(null, "Selecteaza cel putin un meci!");
                return;
            }
            if (DataBase.biletCurent.getBani() == 0) {
                JOptionPane.showMessageDialog(null, "Miza nu poate fi 0!");
                return;
            }
            try {
                try {
                    DataBase.oos.writeObject(new Comanda("refresh", null));
                    DataBase.meciuri = (ArrayList<Meci>) DataBase.ois.readObject();
                    tabel.updateUI();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                DataBase.oos.writeObject(new Comanda("insert bilet", DataBase.biletCurent));
                DataBase.bilete = (ArrayList<Bilet>) DataBase.ois.readObject();
                int id = DataBase.bilete.get(DataBase.bilete.size()-1).getId();
                //int id = 0;
                JOptionPane.showMessageDialog(null, "Biletul cu ID " + id + " a fost inregistrat!");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            DataBase.biletCurent = null;
            dialogPariuri.dispose();
        });

        cancel.addActionListener(e -> dialogPariuri.dispose());

        dialogPariuri.setBounds(400,400,500,550);
        dialogPariuri.setResizable(false);
        dialogPariuri.setVisible(true);
    }
}
