/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.*;
import java.io.File;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox hbox1;
    
    private ArrayList<Album> albumes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        for(Album a : Album.cargarAlbumes("hola.txt")) {
//            
//        }
        
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
        GridPane gridPane = new GridPane();
         gridPane.add(new Label("Ingresa un nombre para este álbum"), 0, 0);
         gridPane.add(new Label("Ingresa una descripción para este álbum "), 0, 1);
         TextField tit = new TextField();
         tit.setPromptText("Título");
         TextField txtdes = new TextField();
         txtdes.setPromptText("Descripción");
         gridPane.add(tit,1,0);
         gridPane.add(txtdes,1,1);
         gridPane.add(new Button("Crear"), 6, 3);
         
         //searchInput.setId("TextField");
         
         return gridPane;
    }
    
}
