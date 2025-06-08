import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI {
    private JPanel main;
    private JTextField szukajField;
    private JButton szukajButton;
    private JButton wyczyśćButton;
    private JTextField tytulField;
    private JTextField autorField;
    private JTextField rokField;
    private JButton dodajKsiążkęButton;
    private JButton wypożyczZwrotButton;
    private JButton usuńZaznaczonąButton;
    private JScrollPane tableScrollPane;
    private JLabel tytułLabel;
    private JLabel autorLabel;
    private JLabel rokLabel;
    private static Biblioteka biblioteka;
    private static int selectedCzytelnik;
    private static int[] selectedCzytelnikKsiazki;
    private static boolean widokCzytelnikow = false;
    private static boolean widokKsiazek = false;

    public GUI() throws FileNotFoundException {
        if(biblioteka == null) {
            return;
        }
        if(widokKsiazek) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tytuł");
            model.addColumn("Autor");
            model.addColumn("Rok");
            model.addColumn("Status");
            for (int i = 0; i < biblioteka.getKsiazkaSize(); i++) {
                Książka k1 = biblioteka.getKsiazka(i);
                model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
            }
            JTable jTable = new JTable(model);
            tableScrollPane.setViewportView(jTable);
            dodajKsiążkęButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tytulField.getText().isEmpty() || autorField.getText().isEmpty() || rokField.getText().isEmpty()) {
                        return;
                    }
                    String tytul = tytulField.getText().toString().trim();
                    String autor = autorField.getText().toString().trim();
                    int rok = Integer.parseInt(rokField.getText().toString().trim());
                    Książka k1 = new Książka(tytul, autor, rok, "Dostępna");
                    try {
                        biblioteka.addKsiazka(k1);
                        model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            szukajButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String autorTytul = szukajField.getText().toString().trim();
                    for (int i = 0; i < model.getRowCount(); ) {
                        model.removeRow(i);
                    }
                    for (int i = 0; i < biblioteka.getKsiazkaSize(); i++) {
                        Książka k1 = biblioteka.getKsiazka(i);
                        if (k1.getTytul().equals(autorTytul) || k1.getAutor().equals(autorTytul)) {
                            model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
                        }
                    }
                }
            });
            wypożyczZwrotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedCzytelnik == 0) {
                        return;
                    }
                    if (biblioteka.getKsiazka(jTable.getSelectedRow()).getStatus().equals("Dostępna")) {
                        model.setValueAt("Wypożyczona", jTable.getSelectedRow(), 4);
                        biblioteka.changeKsiazkaStatusFromAvailable(selectedCzytelnik, jTable.getSelectedRow());
                    } else if (biblioteka.getKsiazka(jTable.getSelectedRow()).getStatus().equals("Wypożyczona")) {
                        for(int j = 0; j < selectedCzytelnikKsiazki.length; j++) {
                            if (selectedCzytelnikKsiazki[j] == biblioteka.getKsiazka(jTable.getSelectedRow()).getId()) {
                                model.setValueAt("Dostępna", jTable.getSelectedRow(), 4);
                                biblioteka.changeKsiazkaStatusFromBorrowed(selectedCzytelnik, jTable.getSelectedRow());
                                return;
                            }
                        }
                    }
                }
            });
            usuńZaznaczonąButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        biblioteka.removeKsiazka(jTable.getSelectedRow());
                        model.removeRow(jTable.getSelectedRow());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            wyczyśćButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    szukajField.setText("");
                    for (int i = 0; i < model.getRowCount(); ) {
                        model.removeRow(i);
                    }
                    for (int i = 0; i < biblioteka.getKsiazkaSize(); i++) {
                        Książka k1 = biblioteka.getKsiazka(i);
                        model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
                    }
                }
            });
        }
        if(widokCzytelnikow) {
            tytułLabel.setText("");
            autorLabel.setText("Imie");
            rokLabel.setText("Nazwisko");
            tytulField.setVisible(false);
            dodajKsiążkęButton.setText("Dodaj czytelnika");
            wypożyczZwrotButton.setText("Wybierz czytelnika");
            usuńZaznaczonąButton.setText("Usuń zaznaczonego");
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Imie");
            model.addColumn("Nazwisko");
            model.addColumn("ID Wypozyczonych ksiazek");
            for (int i = 0; i < biblioteka.getCzytelnikSize(); i++) {
                Czytelnik c1 = biblioteka.getCzytelnik(i);
                String wypozyczoneKsiazki = "";
                for(int j = 0; j < biblioteka.getWypozyczeniaSize(); j++) {
                    if (c1.getId() == biblioteka.getWypozyczenia(j).getCzytelnik_ID()) {
                        wypozyczoneKsiazki += biblioteka.getWypozyczenia(j).getKsiazka_ID() + " ";
                    }
                }
                model.addRow(new Object[]{c1.getId(), c1.getImie(), c1.getNazwisko(), wypozyczoneKsiazki});
            }
            JTable jTable = new JTable(model);
            tableScrollPane.setViewportView(jTable);
            dodajKsiążkęButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (autorField.getText().isEmpty() || rokField.getText().isEmpty()) {
                        return;
                    }
                    String imie = autorField.getText().toString().trim();
                    String nazwisko = rokField.getText().toString().trim();
                    Czytelnik c1 = new Czytelnik(imie,nazwisko);
                    try {
                        biblioteka.addCzytelnik(c1);
                        model.addRow(new Object[]{c1.getId(), c1.getImie(), c1.getNazwisko()});
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            szukajButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String imieNazwisko = szukajField.getText().toString().trim();
                    for (int i = 0; i < model.getRowCount(); ) {
                        model.removeRow(i);
                    }
                    for (int i = 0; i < biblioteka.getCzytelnikSize(); i++) {
                        Czytelnik c1 = biblioteka.getCzytelnik(i);
                        if (c1.getImie().equals(imieNazwisko) || c1.getNazwisko().equals(imieNazwisko)) {
                            String wypozyczoneKsiazki = "";
                            for(int j = 0; j < biblioteka.getWypozyczeniaSize(); j++) {
                                if (c1.getId() == biblioteka.getWypozyczenia(j).getCzytelnik_ID()) {
                                    wypozyczoneKsiazki += biblioteka.getWypozyczenia(j).getKsiazka_ID() + " ";
                                }
                            }
                            model.addRow(new Object[]{c1.getId(), c1.getImie(), c1.getNazwisko(), wypozyczoneKsiazki});
                        }
                    }
                }
            });
            wypożyczZwrotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedCzytelnik = biblioteka.getCzytelnik(jTable.getSelectedRow()).getId();
                    String wypozyczoneKsiazki = (String) model.getValueAt(jTable.getSelectedRow(), 3);
                    selectedCzytelnikKsiazki = new int[wypozyczoneKsiazki.split(" ").length];
                    int i = 0;
                    for(int j = 0; j < biblioteka.getWypozyczeniaSize(); j++) {
                        if (selectedCzytelnik == biblioteka.getWypozyczenia(j).getCzytelnik_ID()) {
                            selectedCzytelnikKsiazki[i] = biblioteka.getWypozyczenia(j).getKsiazka_ID();
                            i++;
                        }
                    }
                }
            });
            usuńZaznaczonąButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        biblioteka.removeCzytelnik(jTable.getSelectedRow());
                        model.removeRow(jTable.getSelectedRow());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            wyczyśćButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    szukajField.setText("");
                    for (int i = 0; i < model.getRowCount(); ) {
                        model.removeRow(i);
                    }
                    for (int i = 0; i < biblioteka.getCzytelnikSize(); i++) {
                        Czytelnik c1 = biblioteka.getCzytelnik(i);
                        String wypozyczoneKsiazki = "";
                        for(int j = 0; j < biblioteka.getWypozyczeniaSize(); j++) {
                            if (c1.getId() == biblioteka.getWypozyczenia(j).getCzytelnik_ID()) {
                                wypozyczoneKsiazki += biblioteka.getWypozyczenia(j).getKsiazka_ID() + " ";
                            }
                        }
                        model.addRow(new Object[]{c1.getId(), c1.getImie(), c1.getNazwisko(), wypozyczoneKsiazki});
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Plik");
        jMenu.add(new AbstractAction("Otwórz bazę danych") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(biblioteka != null) {
                    return;
                }
                biblioteka = new Biblioteka();
                widokKsiazek = true;
                try {
                    frame.setContentPane(new GUI().main);
                    frame.pack();
                    frame.setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Zapisz bazę danych") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biblioteka == null) {
                    return;
                }
                try {
                    biblioteka.updateDatabase();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Przełącz na widok czytelników") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biblioteka == null) {
                    return;
                }
                if(widokCzytelnikow) {
                    return;
                }
                widokKsiazek = false;
                widokCzytelnikow = true;
                try {
                    frame.setContentPane(new GUI().main);
                    frame.pack();
                    frame.setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Przełącz na widok książek") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biblioteka == null) {
                    return;
                }
                if(widokKsiazek) {
                    return;
                }
                widokKsiazek = true;
                widokCzytelnikow = false;
                try {
                    frame.setContentPane(new GUI().main);
                    frame.pack();
                    frame.setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Zamknij") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        jMenuBar.add(jMenu);
        frame.setJMenuBar(jMenuBar);
        frame.pack();
        frame.setVisible(true);
    }

}
