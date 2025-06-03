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
    private static Czytelnicy czytelnicy;

    public GUI() throws FileNotFoundException {
        if(biblioteka != null) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tytuł");
            model.addColumn("Autor");
            model.addColumn("Rok");
            model.addColumn("Status");
            for (int i = 0; i < biblioteka.getSize(); i++) {
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
                    Książka k1 = new Książka(biblioteka.getSize(), tytul, autor, rok, "Dostępna");
                    model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
                    try {
                        biblioteka.addKsiazka(k1);
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
                    for (int i = 0; i < biblioteka.getSize(); i++) {
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
                    if (biblioteka.getKsiazka(jTable.getSelectedRow()).getStatus().equals("Dostępna")) {
                        biblioteka.getKsiazka(jTable.getSelectedRow()).setStatus("Wypożyczona");
                        model.setValueAt("Wypożyczona", jTable.getSelectedRow(), 4);
                    } else {
                        biblioteka.getKsiazka(jTable.getSelectedRow()).setStatus("Dostępna");
                        model.setValueAt("Dostępna", jTable.getSelectedRow(), 4);
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
                    for (int i = 0; i < biblioteka.getSize(); i++) {
                        Książka k1 = biblioteka.getKsiazka(i);
                        model.addRow(new Object[]{k1.getId(), k1.getTytul(), k1.getAutor(), k1.getRok(), k1.getStatus()});
                    }
                }
            });
        }
        if(czytelnicy != null) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Imię");
            model.addColumn("Nazwisko");
            model.addColumn("Wypozyczone ksiazki");
            for (int i = 0; i < czytelnicy.getSize(); i++) {
                Czytelnik c1 = czytelnicy.getCzytelnik(i);
                model.addRow(new Object[]{c1.getId(), c1.getImie(), c1.getNazwisko(), c1.getWypozyczoneKsiazki()});
            }
            JTable jTable = new JTable(model);
            tableScrollPane.setViewportView(jTable);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Plik");
        jMenu.add(new AbstractAction("Otwórz bibliotekę") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czytelnicy != null) {
                    czytelnicy = null;
                }
                FileDialog fd = new FileDialog(frame, "Wybierz plik z danymi", FileDialog.LOAD);
                fd.setVisible(true);
                try {
                    biblioteka = new Biblioteka(fd.getDirectory(), fd.getFile());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    frame.setContentPane(new GUI().main);
                    frame.pack();
                    frame.setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Zapisz bibliotekę") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biblioteka == null) {
                    return;
                }
                FileDialog fd = new FileDialog(frame, "Wybierz plik do zapisu danych", FileDialog.SAVE);
                fd.setVisible(true);
                try {
                    biblioteka.updateKsiazka(fd.getDirectory(), fd.getFile());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Otwórz czytelników") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(biblioteka != null) {
                    biblioteka = null;
                }
                FileDialog fd = new FileDialog(frame, "Wybierz plik z danymi czytelnikow", FileDialog.LOAD);
                fd.setVisible(true);
                try {
                    czytelnicy = new Czytelnicy(fd.getDirectory(), fd.getFile());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    frame.setContentPane(new GUI().main);
                    frame.pack();
                    frame.setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jMenu.add(new AbstractAction("Zapisz czytelników") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czytelnicy == null) {
                    return;
                }
                FileDialog fd = new FileDialog(frame, "Wybierz plik do zapisu danych czytelnikow", FileDialog.SAVE);
                fd.setVisible(true);
                try {
                    czytelnicy.updateCzytelnicy(fd.getDirectory(), fd.getFile());
                } catch (IOException ex) {
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
