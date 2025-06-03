import java.io.FileNotFoundException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Biblioteka b1 = new Biblioteka();
        System.out.println(b1.getKsiazka(0));
    }
}