package ui;

import date.DataBase;
import date.Meci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Rob on 06.05.2018.
 */
public class CompetitieUI {
    public static void interfata() {
        JTable tabel = MainMenuUI.tabel;
        JFrame frame = MainMenuUI.frame;
        //JMenuBar menubar = MainMenuUI.menubar;
        JMenu competitii = MainMenuUI.competitii;
        JMenuItem adaugareMeci = new JMenuItem("Adaugare meci");
        JMenuItem adaugareRezultat = new JMenuItem("Adaugare rezultat");
        competitii.add(adaugareMeci);
        competitii.add(adaugareRezultat);
        competitii.setMnemonic(KeyEvent.VK_C);

        //raport.setMnemonic(KeyEvent.VK_R);

        adaugareMeci.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JDialog dialog = new JDialog(frame, "Adaugare meci", true);
                JTextField echipa1= new JTextField();
                JTextField echipa2 = new JTextField();
                JTextField cota1 = new JTextField();
                JTextField cotax = new JTextField();
                JTextField cota2 = new JTextField();
                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Cancel");
                dialog.setLayout(new GridLayout(6,2));
                dialog.add(new JLabel("Echipa 1"));
                dialog.add(echipa1);
                dialog.add(new JLabel("Echipa 2"));
                dialog.add(echipa2);
                dialog.add(new JLabel("Cota 1"));
                dialog.add(cota1);
                dialog.add(new JLabel("Cota X"));
                dialog.add(cotax);
                dialog.add(new JLabel("Cota 2"));
                dialog.add(cota2);
                dialog.add(ok);
                dialog.add(cancel);

                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Meci meci = new Meci(echipa1.getText(), echipa2.getText(), Double.parseDouble(cota1.getText()), Double.parseDouble(cotax.getText())
                                    , Double.parseDouble(cota2.getText()));
                            DataBase.meciuri.add(meci);
                            tabel.updateUI();
                            dialog.dispose();
                        }
                        catch (NumberFormatException exception) {
                            JOptionPane.showMessageDialog(null, "Completeza corect datele!");
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

                dialog.setBounds(400,400,300,150);
                dialog.setVisible(true);
            }});

        adaugareRezultat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = new JDialog(frame, "Adaugare rezultat", true);
                JTextField idMeci = new JTextField();
                JTextField rezultat = new JTextField();
                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Cancel");
                dialog.setLayout(new GridLayout(3,2));
                dialog.add(new JLabel("ID Meci"));
                dialog.add(idMeci);
                dialog.add(new JLabel("Rezultat"));
                dialog.add(rezultat);
                dialog.add(ok);
                dialog.add(cancel);

                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!rezultat.getText().equals("1"))
                                if (!rezultat.getText().equals("2"))
                                    if (!rezultat.getText().equals("X"))
                                        if (!rezultat.getText().equals("x"))
                                            throw new PariuriException("Completati corect datele!");
                            boolean gasit = false;
                            for (Meci meci : DataBase.meciuri) {
                                if (meci.getId() == Integer.parseInt(idMeci.getText())) {
                                    meci.setRezultat(rezultat.getText());
                                    gasit = true;
                                    break;
                                }
                            }
                            if (!gasit) {
                                throw new PariuriException("ID Meci nu exista!");
                            }
                            tabel.updateUI();
                            dialog.dispose();
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

                dialog.setBounds(400,400,300,150);
                dialog.setVisible(true);
            }});
    }
}
