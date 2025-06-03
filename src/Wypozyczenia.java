public class Wypozyczenia {
    private int id;
    private int ksiazka_ID;
    private int czytelnik_ID;

    public Wypozyczenia(int id, int ksiazka_ID, int czytelnik_ID) {
        this.id = id;
        this.ksiazka_ID = ksiazka_ID;
        this.czytelnik_ID = czytelnik_ID;
    }

    public Wypozyczenia(int ksiazka_ID, int czytelnik_ID) {
        this.ksiazka_ID = ksiazka_ID;
        this.czytelnik_ID = czytelnik_ID;
    }

    public int getId() {
        return id;
    }

    public int getKsiazka_ID() {
        return ksiazka_ID;
    }

    public int getCzytelnik_ID() {
        return czytelnik_ID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKsiazka_ID(int ksiazka_ID) {
        this.ksiazka_ID = ksiazka_ID;
    }

    public void setCzytelnik_ID(int czytelnik_ID) {
        this.czytelnik_ID = czytelnik_ID;
    }
}
