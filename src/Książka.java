public class Książka {
    private int id;
    private String tytul;
    private String autor;
    private int rok;
    private String status;

    public Książka(int id, String tytul, String autor, int rok, String status) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
        this.rok = rok;
        this.status = status;
    }

    public Książka(String tytul, String autor, int rok, String status) {
        this.tytul = tytul;
        this.autor = autor;
        this.rok = rok;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTytul() {
        return tytul;
    }

    public String getAutor() {
        return autor;
    }

    public int getRok() {
        return rok;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Książka{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", autor='" + autor + '\'' +
                ", rok=" + rok +
                ", status='" + status + '\'' +
                '}';
    }
}
