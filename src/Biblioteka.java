import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteka {
    private ArrayList<Książka> ksiazkaList;
    private ArrayList<Czytelnik> czytelnikList;
    private ArrayList<Wypozyczenia> wypozyczeniaList;
    private String url = "jdbc:mysql://localhost:3306/biblioteka";
    private String user = "root";
    private String password = "";

    private String requests = "";

    public Biblioteka() {
        ksiazkaList = new ArrayList<>();
        czytelnikList = new ArrayList<>();
        wypozyczeniaList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ksiazka");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String tytul = resultSet.getString(2);
                String autor = resultSet.getString(3);
                int rok = resultSet.getInt(4);
                String status = resultSet.getString(5);
                Książka k1 = new Książka(id, tytul, autor, rok, status);
                ksiazkaList.add(k1);
            }
            resultSet = statement.executeQuery("SELECT * FROM czytelnik");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String imie = resultSet.getString(2);
                String nazwisko = resultSet.getString(3);
                Czytelnik c1 = new Czytelnik(id,imie,nazwisko);
                czytelnikList.add(c1);
            }
            resultSet = statement.executeQuery("SELECT * FROM wypozyczenia");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int ksiazka_ID = resultSet.getInt(2);
                int czytelnik_ID = resultSet.getInt(3);
                Wypozyczenia w1 = new Wypozyczenia(id,ksiazka_ID,czytelnik_ID);
                wypozyczeniaList.add(w1);
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDatabase() throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(requests);
        } catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
        requests = "";
    }
    public void addKsiazka(Książka k1) throws IOException {
        requests += "INSERT INTO ksiazka VALUES(NULL,'" + k1.getTytul() +"','" + k1.getAutor() + "'," + k1.getRok() + ",'" + k1.getStatus() + "');";
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM ksiazka");
            while (resultSet.next()) {
                if(resultSet.getInt(1) > id) {
                    id = resultSet.getInt(1) + 1;
                    k1.setId(id);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        ksiazkaList.add(k1);
    }
    public void removeKsiazka(int index) throws IOException {
        requests += "DELETE FROM ksiazka WHERE ID=" + getKsiazka(index).getId() + ";";
        ksiazkaList.remove(index);
    }
    public Książka getKsiazka(int index) {
        return ksiazkaList.get(index);
    }
    public int getKsiazkaSize() {
        return ksiazkaList.size();
    }
    public void changeKsiazkaStatusFromAvailable(int czytelnik_ID, int index) {
        ksiazkaList.get(index).setStatus("Wypożyczona");
        requests += "INSERT INTO wypozyczenia VALUES(NULL, " + ksiazkaList.get(index).getId() + ", " + czytelnik_ID + ");";
        Wypozyczenia w1 = new Wypozyczenia(ksiazkaList.get(index).getId(), czytelnik_ID);
        wypozyczeniaList.add(w1);
    }
    public void changeKsiazkaStatusFromBorrowed(int czytelnik_ID, int index) {
        ksiazkaList.get(index).setStatus("Dostępna");
        requests += "DELETE FROM wypozyczenia WHERE ksiazka_ID =" + ksiazkaList.get(index).getId() + " AND czytelnik_ID=" + czytelnik_ID + ";";
        wypozyczeniaList.remove(index);
    }
    public void addCzytelnik(Czytelnik c1) throws IOException {
        requests += "INSERT INTO czytelnik VALUES(NULL,'" + c1.getImie() +"','" + c1.getNazwisko() + "');";
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM czytelnik");
            while (resultSet.next()) {
                if(resultSet.getInt(1) > id) {
                    id = resultSet.getInt(1) + 1;
                    c1.setId(id);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        czytelnikList.add(c1);
    }
    public void removeCzytelnik(int index) throws IOException {
        requests += "DELETE FROM czytelnik WHERE ID=" + getCzytelnik(index).getId() + ";";
        czytelnikList.remove(index);
    }
    public Czytelnik getCzytelnik(int index) {
        return czytelnikList.get(index);
    }
    public int getCzytelnikSize() {
        return czytelnikList.size();
    }
    public Wypozyczenia getWypozyczenia(int index) {
        return wypozyczeniaList.get(index);
    }
    public int getWypozyczeniaSize() {
        return wypozyczeniaList.size();
    }
}
