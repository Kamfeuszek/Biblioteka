import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Czytelnicy {
    ArrayList<Czytelnik> czytelnikList;
    String pathTemp = "C:\\Users\\9601323u\\Desktop\\bibliotekadane\\daneTemp.csv";

    public Czytelnicy(String directory, String file) throws FileNotFoundException {
        czytelnikList = new ArrayList<>();
        File f1 = new File(directory + file);
        Scanner scan = new Scanner(f1);
        while (scan.hasNext()) {
            String dane = scan.nextLine();
            String[] daneSplit = dane.split(",");
            int id = Integer.parseInt(daneSplit[0]);
            String imie = daneSplit[1];
            String nazwisko = daneSplit[2];
            String[] wypozyczoneKsiazkiTemp = daneSplit[3].split(";");
            ArrayList<Integer> wypozyczoneKsiazkiList = new ArrayList<>();
            for(int i = 0; i < wypozyczoneKsiazkiTemp.length; i++) {
                wypozyczoneKsiazkiList.add(Integer.valueOf(wypozyczoneKsiazkiTemp[i]));
            }
            WypozyczoneKsiazki wypozyczoneKsiazki = new WypozyczoneKsiazki(id, wypozyczoneKsiazkiList);
            Czytelnik c1 = new Czytelnik(id,imie,nazwisko,wypozyczoneKsiazki);
            czytelnikList.add(c1);
        }
    }
    public void addCzytelnik(Czytelnik c1) {
        czytelnikList.add(c1);
    }
    public void removeCzytelnik(int index) {
        czytelnikList.remove(index);
    }
    public void updateCzytelnicy(String directory, String file) throws IOException {
        FileWriter fw = new FileWriter(pathTemp);
        for (int i = 0; i < czytelnikList.size(); i++) {
            Czytelnik c1 = czytelnikList.get(i);
            fw.append(c1.getId() + "," + c1.getImie() + "," + c1.getNazwisko() + "," + c1.getWypozyczoneKsiazki() + "\n");
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
    public Czytelnik getCzytelnik(int index) {
        return czytelnikList.get(index);
    }
    public int getSize() {
        return czytelnikList.size();
    }
}
