package es.albertolopez.salas_estudio;

public class Sala {
    private int id;
    private String nombre;
    private int aforo;

    public Sala(int id, String nombre, int aforo) {
        this.id = id;
        this.nombre = nombre;
        this.aforo = aforo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAforo() {
        return aforo;
    }
}
