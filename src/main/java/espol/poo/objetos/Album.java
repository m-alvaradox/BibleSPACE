
package espol.poo.objetos;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private String nombre;
    private String descripcion;
    private ArrayList<Foto> fotos;
    
    public Album(String nombre, String descripcion, ArrayList<Foto> fotos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotos = fotos;
    }
    
    public static ArrayList<Album> cargarAlbumes(String archivo) {
        ArrayList<Album> albumes = new ArrayList<>();
        
        try(ObjectInputStream oit = new ObjectInputStream(new FileInputStream("galeria.ser"))){
            albumes = (ArrayList<Album>) oit.readObject();
        }
        catch(Exception e) {
            System.out.println("Galeria vacia");
        }
        // por hacer
        return albumes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }
    
       
      
}
