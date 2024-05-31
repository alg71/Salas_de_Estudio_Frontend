package es.albertolopez.salas_estudio;

/*Autor: Alberto López Garcia
Creative commons CC BY-SA 4.0
https://creativecommons.org/licenses/by-sa/4.0/deed.es*/

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
