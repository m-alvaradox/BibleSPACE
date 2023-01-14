
package espol.poo.objetos;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Persona {
    private String nombre;
    private String apellido;

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public static ArrayList<Persona> cargarPersonas(String archivo) {
        ArrayList<Persona> personas = new ArrayList<>();
        
        try(ObjectInputStream oit = new ObjectInputStream(new FileInputStream("personas.ser"))){
            personas = (ArrayList<Persona>) oit.readObject();
        }
        catch(Exception e) {
            System.out.println("No hay personas");
        }
        return personas;
    }
    
    
    
}
