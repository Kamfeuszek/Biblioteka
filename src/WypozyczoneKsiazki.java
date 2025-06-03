import java.util.ArrayList;

public class WypozyczoneKsiazki {
    private int idCzytelnika;
    private ArrayList<Integer> idKsiazek;

    public WypozyczoneKsiazki(int idCzytelnika, int[] idKsiazek) {
        this.idCzytelnika = idCzytelnika;
        this.idKsiazek = idKsiazek;
    }

    public int getIdCzytelnika() {
        return idCzytelnika;
    }

    public int[] getIdKsiazek() {
        return idKsiazek;
    }

    public void setIdCzytelnika(int idCzytelnika) {
        this.idCzytelnika = idCzytelnika;
    }

    public void setIdKsiazek(int[] idKsiazek) {
        this.idKsiazek = idKsiazek;
    }
}
