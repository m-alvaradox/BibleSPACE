
package espol.poo.objetos;

import espol.poo.biblespace.AlbumvisController;
import static espol.poo.biblespace.AlbumvisController.personas;
import espol.poo.biblespace.App;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Persona implements Serializable {
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
    
    public static void agregarpersona() {
        
          ArrayList<Persona> personas = Persona.cargarPersonas(App.filepeople);
        
          Dialog dialog = new Dialog();
          dialog.setTitle("Agregar persona");
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
          GridPane gridPane = new GridPane();
          gridPane.add(new Label("Nombre "), 0, 0);
          gridPane.add(new Label("Apellido "), 0, 1);
         
          TextField nom = new TextField();
          TextField ape = new TextField();
         
          gridPane.add(nom,1,0);
          gridPane.add(ape,1,1);
          
          Button agg = new Button("Agregar");
          
          agg.setOnMouseClicked(evento -> {
             Persona person = new Persona(nom.getText(),ape.getText());
             personas.add(person);
             
             System.out.println("Se agregó un nueva persona: "+person.getNombre()+" "+person.getApellido());
             
             try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(App.filepeople))) {
                 out1.writeObject(personas);
                 out1.flush();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Resultado de la operacion");
                alert.setContentText("Persona agregada correctamente");
                AlbumvisController.personas = Persona.cargarPersonas(App.filepeople);
                dialog.close();
                alert.showAndWait();
                
                }
             catch(IOException ex) {
               System.out.println("Error al encontrar el archivo");  
             }
         });
         gridPane.add(agg, 6, 3);
         
          Image image = new Image(App.fileimages+"persona.png");
          
          ImageView ico = new ImageView(image);
          ico.setFitWidth(0.10*image.getWidth());
          ico.setFitHeight(0.10*image.getHeight());
          gridPane.add(ico, 6, 4);
         
         
         
         dialog.getDialogPane().setContent(gridPane);
         dialog.show();     
 
    }    
}
