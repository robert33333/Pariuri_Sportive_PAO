package ui;

import date.Bilet;
import date.DataBase;
import date.Meci;
import date.Pariu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Rob on 06.05.2018.
 */
public class BiletUI {
    public static void interfata() {
        JFrame frame = MainMenuUI.frame;
        JMenuBar menubar = MainMenuUI.menubar;
        JMenu bilet = MainMenuUI.bilet;
        JMenuItem inregistrareSimplu = new JMenuItem("Inregistrare bilet simplu");
        JMenuItem inregistrareCompus = new JMenuItem("Inregistrare bilet compus");
        JMenuItem validareBilet = new JMenuItem("Validare bilet");

        bilet.add(inregistrareSimplu);
        bilet.add(inregistrareCompus);
        bilet.add(validareBilet);
        bilet.setMnemonic(KeyEvent.VK_B);
        inregistrareCompus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //creere bilet
                ArrayList<Pariu> pariuriCurente = new ArrayList<>();
                Bilet bilet1 = new Bilet(pariuriCurente,0.0);
                DataBase.biletCurent = bilet1;

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

                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Cancel");
                dialogPariuri.add(ok);
                dialogPariuri.add(cancel);

                adaugaPariu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        JDialog dialog = new JDialog(frame, "Adaugare pariu", true);
                        dialog.setLayout(new GridLayout(3,2));
                        JTextField meciID = new JTextField();
                        JTextField optiune = new JTextField();
                        JButton ok = new JButton("Ok");
                        JButton cancel = new JButton("Cancel");

                        dialog.add(new JLabel("ID Meci"));
                        dialog.add(meciID);
                        dialog.add(new JLabel("Optiune"));
                        dialog.add(optiune);
                        dialog.add(ok);
                        dialog.add(cancel);

                        ok.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    if (!optiune.getText().equals("1"))
                                        if (!optiune.getText().equals("2"))
                                            if (!optiune.getText().equals("X"))
                                                if (!optiune.getText().equals("x"))
                                                    throw new PariuriException("Completati corect datele!");
                                    boolean gasit = false;
                                    for (Meci meci : DataBase.meciuri) {
                                        if (meci.getId() == Integer.parseInt(meciID.getText())) {
                                            Pariu pariu = new Pariu(meci, optiune.getText());
                                            //verificare id exista deja
                                            for (Pariu pariu1 : DataBase.biletCurent.getPariuri()) {
                                                if (pariu.getMeci().getId()== pariu1.getMeci().getId())
                                                    throw new PariuriException("Meci deja selectat!");
                                            }
                                            DataBase.biletCurent.getPariuri().add(pariu);
                                            tabelPariuri.updateUI();
                                            dialog.dispose();
                                            gasit = true;
                                            break;
                                        }
                                    }
                                    if (!gasit) {
                                        throw new PariuriException("ID Meci nu exista!");
                                    }
                                }
                                catch (PariuriException exception) {
                                        JOptionPane.showMessageDialog(null, exception.mesaj);
                                    }
                                catch (Exception exceptionGeneric) {
                                    JOptionPane.showMessageDialog(null, "Completeaza corect datele!");
                                }
                            }

                        });

                        cancel.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                        dialog.setBounds(500,500,300,150);
                        dialog.setVisible(true);

                    }
                });

                stergePariu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        JDialog dialog = new JDialog(frame, "Stergere pariu", true);
                        dialog.setLayout(new GridLayout(2,2));
                        JTextField meciID = new JTextField();
                        JButton ok = new JButton("Ok");
                        JButton cancel = new JButton("Cancel");

                        dialog.add(new JLabel("ID Meci"));
                        dialog.add(meciID);
                        dialog.add(ok);
                        dialog.add(cancel);

                        ok.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) {
                                try{
                                    boolean gasit = false;
                                    for(Pariu pariu : DataBase.biletCurent.getPariuri()) {
                                        if(pariu.getMeci().getId() == Integer.parseInt(meciID.getText())) {
                                            DataBase.biletCurent.getPariuri().remove(pariu);
                                            gasit = true;
                                            dialog.dispose();
                                            tabelPariuri.updateUI();
                                            break;
                                        }
                                    }
                                    if(!gasit){
                                        throw new PariuriException("ID Meci nu a fost gaist");
                                    }
                                }
                                catch(PariuriException exception){
                                    JOptionPane.showMessageDialog(null, exception.mesaj);
                                }
                                catch (Exception exceptionGeneric) {
                                    JOptionPane.showMessageDialog(null, "Completeaza corect datele!");
                                }

                            }

                        });

                        cancel.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                        dialog.setBounds(500,500,300,150);
                        dialog.setVisible(true);
                    }
                });

                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialogPariuri.dispose();
                    }
                });

                dialogPariuri.setBounds(400,400,500,550);
                dialogPariuri.setResizable(false);
                dialogPariuri.setVisible(true);

            }
        });
    }
}