import java.util.ArrayList;

public class Czytelnik {
    private int id;
    private String imie;
    private String nazwisko;
    private WypozyczoneKsiazki wypozyczoneKsiazki;

    public Czytelnik(int id, String imie, String nazwisko, WypozyczoneKsiazki wypozyczoneKsiazki) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wypozyczoneKsiazki = wypozyczoneKsiazki;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getId() {
        return id;
    }

    public WypozyczoneKsiazki getWypozyczoneKsiazki() {
        return wypozyczoneKsiazki;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWypozyczoneKsiazki(WypozyczoneKsiazki wypozyczoneKsiazki) {
        this.wypozyczoneKsiazki = wypozyczoneKsiazki;
    }
}
