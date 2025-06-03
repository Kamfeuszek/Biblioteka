import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteka {
    private ArrayList<Książka> ksiazkaList;
    String pathTemp = "C:\\Users\\9601323u\\Desktop\\bibliotekadane\\daneTemp.csv";

    public Biblioteka(String directory,String file) throws FileNotFoundException {
        ksiazkaList = new ArrayList<>();
        File f1 = new File(directory + file);
        Scanner scan = new Scanner(f1);
        while (scan.hasNext()) {
            String dane = scan.nextLine();
            String[] daneSplit = dane.split(",");
            int id = Integer.parseInt(daneSplit[0]);
            String tytul = daneSplit[1];
            String autor = daneSplit[2];
            int rok = Integer.parseInt(daneSplit[3]);
            String status = daneSplit[4];
            Książka k1 = new Książka(id, tytul, autor, rok, status);
            ksiazkaList.add(k1);
        }
    }
    public void addKsiazka(Książka k1) throws IOException {
        ksiazkaList.add(k1);
    }
    public void removeKsiazka(int index) throws IOException {
        ksiazkaList.remove(index);
    }
    public void updateKsiazka(String directory, String file) throws IOException {
        FileWriter fw = new FileWriter(pathTemp);
        for (int i = 0; i < ksiazkaList.size(); i++) {
            Książka k1 = ksiazkaList.get(i);
            fw.append(k1.getId() + "," + k1.getTytul() + "," + k1.getAutor() + "," + k1.getRok() + "," + k1.getStatus() + "\n");
        }
        fw.close();
        File f1 = new File(pathTemp);
        Scanner scan = new Scanner(f1);
        FileWriter fw2 = new FileWriter(directory + file);
        while (scan.hasNext()) {
            fw2.append(scan.nextLine() + "\n");
        }
        fw2.close();
        fw = new FileWriter(pathTemp);
        fw.append("");
        fw.close();
    }
    public Książka getKsiazka(int index) {
        return ksiazkaList.get(index);
    }
    public int getSize() {
        return ksiazkaList.size();
    }
}
