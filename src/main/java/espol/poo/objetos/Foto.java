
package espol.poo.objetos;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    
    public static ArrayList<Foto> cargarFotografias(Album alx) {
        ArrayList<Foto> ft = new ArrayList<>();
        
        try {
            for(Foto ft1 : alx.getFotos()) {
                ft.add(ft1);
            }
        }
        catch(Exception e) {
            System.out.println(" ");
        }
        return ft;
    }
    
    
    
    
    
    
}
