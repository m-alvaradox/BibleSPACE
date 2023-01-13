/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
/**
 * FXML Controller class
 *
 * @author Mario
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button bttnadd;
    @FXML
    private FlowPane pcontenido;
    
    private ArrayList<Album> albumes = Album.cargarAlbumes(App.archgaleria);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for(Album al : albumes) {
            VBox cont = new VBox(3);
            Label lblnom = new Label(al.getNombre());
            Tooltip t = new Tooltip(al.getDescripcion());
            t.setFont(Font.font("Verdana", FontPosture.REGULAR, 10));
            
            ImageView imgalb = new ImageView();
            try {
            Image image = new Image(App.fileimages +"Albumdefault.png",100,100,false,false);
            imgalb.setImage(image);
            } catch(Exception ex) {
                System.out.println("No se encuentra el archivo");
            }
            //lblnom.setTooltip(t);
            Tooltip.install(imgalb, t);
            cont.getChildren().addAll(imgalb,lblnom);
            pcontenido.getChildren().add(cont);
        }
        
        
    }

    @FXML
    private void btncargar(ActionEvent event) {
//        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
//        mensaje.setTitle("Añadir ");
//        mensaje.setContentText("Este es un mensaje");
//        mensaje.showAndWait();

//        FileChooser fc = new FileChooser();
//        File archivo = fc.showOpenDialog(null);
//        System.out.println(archivo);

          Dialog dialog = new Dialog();
          dialog.setTitle("Nuevo Álbum");
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
       
          dialog.getDialogPane().setContent(crearalbum());
          dialog.show();
          
    }
    
    private Node crearalbum() { // Para que sirve node?
         
         //ArrayList<Album> albumes = Album.cargarAlbumes(App.archgaleria);
         GridPane gridPane = new GridPane();
         gridPane.add(new Label("Ingresa un nombre para este álbum"), 0, 0);
         gridPane.add(new Label("Ingresa una descripción para este álbum "), 0, 1);
         
         TextField tit = new TextField();
         tit.setPromptText("Título");
         TextField des = new TextField();
         des.setPromptText("Descripción");
         
         gridPane.add(tit,1,0);
         gridPane.add(des,1,1);
         
         Button cr = new Button("Crear");
         cr.setOnMouseClicked(evento -> {
             Album al = new Album(tit.getText(),des.getText(),null);
             albumes.add(al);
             System.out.println("Se agregó un nuevo album: "+al.getNombre());
             System.out.println("Descripcion: "+al.getDescripcion());
             
             try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(App.archgaleria))) {
                 out.writeObject(albumes);
                 out.flush();
                App.setRoot("principal");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Resultado de la operacion");
                alert.setContentText("Álbum creado correctamente");
                alert.showAndWait();
                
                }
             catch(IOException ex) {
               System.out.println("IOException:" + ex.getMessage());  
             }
         });
         gridPane.add(cr, 6, 3);

         return gridPane;
         
    }


    
    
    
}