
package espol.poo.objetos;

import espol.poo.objetos.Album;
import java.io.Serializable;
import java.util.ArrayList;

public class Foto implements Serializable {
    private String url;
    private String descripcion;
    private String fecha;
    private String lugar;
    private ArrayList<Persona> personas;
    private Album album;

    public Foto(String url,String descripcion, String lugar, String fecha, ArrayList<Persona> personas, Album album) {
        this.url = url;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.personas = personas;
        this.album = album;
    }
    
    
    
    
}
