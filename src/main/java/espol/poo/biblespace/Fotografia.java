
package espol.poo.biblespace;

import java.util.ArrayList;

public class Fotografia {
    private String descripcion;
    private String fecha;
    private String lugar;
    private ArrayList<Persona> personas;
    private Album album;

    public Fotografia(String descripcion, String lugar, ArrayList<Persona> personas, Album album) {
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.personas = personas;
        this.album = album;
    }
    
    
    
    
}
