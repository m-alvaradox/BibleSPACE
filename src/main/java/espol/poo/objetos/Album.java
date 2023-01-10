
package espol.poo.objetos;

import java.util.ArrayList;

public class Album {
    private String nombre;
    private String descripcion;
    private ArrayList<Fotografia> fotos;

    public Album(String nombre, String descripcion, ArrayList<Fotografia> fotos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotos = fotos;
    } 
}
