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
        inregistrareCompus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                interfataAdaugareBilet("Compus");
            }
        });

        inregistrareSimplu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                interfataAdaugareBilet("Simplu");
            }
        });

        validareBilet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialogValidare = new JDialog(frame,"Validare bilet", true);
                dialogValidare.setLayout(new GridLayout(2,2));
                JTextField idBilet = new JTextField();
                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Cancel");
                dialogValidare.add(new JLabel("ID Bilet"));
                dialogValidare.add(idBilet);
                dialogValidare.add(ok);
                dialogValidare.add(cancel);

                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        for (Bilet bilet1 : DataBase.bilete) {
                            if (bilet1.getId() == Integer.parseInt(idBilet.getText())) {
                                if (bilet1.verificaBilet()) {
                                    if (!bilet1.isDejaValidat()) {
                                        JOptionPane.showMessageDialog(null, "Biletul este valid!");
                                        bilet1.setDejaValidat();
                                        dialogValidare.dispose();
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(null, "Biletul a fost deja validat!");
                                        dialogValidare.dispose();
                                    }
                                    return;
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Biletul NU este valid!");
                        dialogValidare.dispose();
                    }
                });

                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialogValidare.dispose();
                    }
                });

                dialogValidare.setBounds(400,400,300,150);
                dialogValidare.setResizable(false);
                dialogValidare.setVisible(true);
            }
        });
    }

    private static void interfataAdaugareBilet(String tip) {
        //creere bilet
        ArrayList<Pariu> pariuriCurente = new ArrayList<>();
        DataBase.biletCurent = new Bilet(pariuriCurente);

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
                            if (tip.equals("Simplu"))
                                if (DataBase.biletCurent.getPariuri().size() > 0)
                                    throw new PariuriException("Pentru mai multe meciuri selectati bilet compus!");
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
                                    updateCastigMiza(miza,castig);
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
                                    updateCastigMiza(miza,castig);
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

        seteazaMiza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = new JDialog(frame, "Seteaza Miza", true);
                dialog.setLayout(new GridLayout(2,2));
                JTextField mizaText = new JTextField();
                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Cancel");

                dialog.add(new JLabel("Miza"));
                dialog.add(mizaText);
                dialog.add(ok);
                dialog.add(cancel);

                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            DataBase.biletCurent.setBani(Double.parseDouble(mizaText.getText()));
                            updateCastigMiza(miza,castig);
                            dialog.dispose();
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

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (DataBase.biletCurent.getPariuri().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Selecteaza cel putin un meci!");
                    return;
                }
                if (DataBase.biletCurent.getBani() == 0) {
                    JOptionPane.showMessageDialog(null, "Miza nu poate fi 0!");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Biletul cu ID " + DataBase.biletCurent.getId() + " a fost inregistrat!");
                DataBase.bilete.add(DataBase.biletCurent);
                DataBase.biletCurent = null;
                dialogPariuri.dispose();
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
}
