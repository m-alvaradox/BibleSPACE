/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
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
        Tooltip tbuttonadd = new Tooltip("Crear un álbum nuevo");
        bttnadd.setTooltip(tbuttonadd);
        
        for(Album al : albumes) {
            VBox cont = new VBox(2);
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
            imgalb.setCursor(Cursor.HAND);
            Tooltip.install(imgalb, t);
            
            imgalb.setOnMouseClicked(ev -> 
            { //AlbumvisController.albumseleccionado(al);
                try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("albumvis.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                AlbumvisController avc = fxmlLoader.getController();
                avc.llenarTitulo(al);
                int indice = albumes.indexOf(al);
                enviarIndice(indice);
                
                App.changeRoot(root);
                } catch(IOException ex) {System.out.println("Error"); }
            
            }); 
            
            cont.getChildren().addAll(imgalb,lblnom);
            pcontenido.getChildren().add(cont);
            
        }
        
        
    }

    @FXML
    private void btncargar(ActionEvent event) {

          Dialog dialog = new Dialog();
          dialog.setTitle("Nuevo Álbum");
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
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
                dialog.close();
                alert.showAndWait();
                
                }
             catch(IOException ex) {
               System.out.println("IOException:" + ex.getMessage());  
             }
         });
         gridPane.add(cr, 6, 3);
         dialog.getDialogPane().setContent(gridPane);
         dialog.show();     
    }

    public void enviarIndice(int indice) {
        try {
            FileWriter fichero = new FileWriter("indicealbum.txt");
            fichero.write(Integer.toString(indice));
            fichero.close();
        }
        catch (Exception ex) { System.out.println("No se encuentra el archivo"); }
    }
}
