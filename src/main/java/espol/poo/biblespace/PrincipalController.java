/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
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
    public static int indice;
    @FXML
    private Button bttnaddpeople;
    @FXML
    private Button bttnsearch;
    @FXML
    private Button bttneditar;
    @FXML
    private Button bttneditpeople;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Tooltip tbuttonadd = new Tooltip("Crear un álbum nuevo");
        bttnadd.setTooltip(tbuttonadd);
        bttnaddpeople.setTooltip(new Tooltip("Agregar persona"));
        bttnsearch.setTooltip(new Tooltip("Buscar"));
        bttneditar.setTooltip(new Tooltip("Editar albumes"));
        
        llenaralbumes();
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
             
             serializaralbumes();
             
             pcontenido.getChildren().clear();
             
             llenaralbumes();
             
             dialog.close();
             mostraralertaconfirmacion("Álbum creado correctamente");
     
         });
         gridPane.add(cr, 6, 3);
         dialog.getDialogPane().setContent(gridPane);
         dialog.show();     
    }

    @FXML
    private void agregarpersona(ActionEvent event) {
        Persona.agregarpersona();
    }
    
    public void serializaralbumes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(App.archgaleria))) {
                 out.writeObject(albumes);
                 out.flush();
                }
             catch(IOException ex) {
               System.out.println("IOException:" + ex.getMessage());  
             }
        
    }
    
    public void llenaralbumes() {
        
        for(Album al : albumes) {
            VBox cont = new VBox(1);
            Label lblnom = new Label(al.getNombre());
            
            Tooltip t;
            if(al.getDescripcion().equals("")) {
                t = new Tooltip("Sin informacion");
            } else {
                t = new Tooltip(al.getDescripcion());
            }

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
            
            imgalb.setOnMouseClicked(event -> 
            { 
                try {
                PrincipalController.indice = albumes.indexOf(al);
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("albumvis.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                AlbumvisController avc = fxmlLoader.getController();
                avc.llenarTitulo(al);

                App.changeRoot(root);
                
                } catch(IOException ex) {System.out.println("Error"); }
            
            }); 
            
            cont.getChildren().addAll(imgalb,lblnom);
            pcontenido.getChildren().add(cont);
              
        }
        
    }
    
    public void mostraralertaconfirmacion(String msg) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Confirmation Dialog");
             alert.setHeaderText("Resultado de la operacion");
             alert.setContentText(msg);
             alert.showAndWait();      
    }
    
                    //para dormir
//                Thread mith = Thread.currentThread();
//                try {
//                    mith.sleep(4000);  // en milisegundos
//                    } catch(InterruptedException ie) {
//                        System.err.println("Capturada InterruptedException: "+ie);
//                    }

    @FXML
    private void filtrado(ActionEvent event) throws IOException {
           try {
                FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource("filtros.fxml"));
                Parent root1 = (Parent) fxmlLoader1.load();
                FiltrosController fill = fxmlLoader1.getController();

                App.changeRoot(root1);
                
                } catch(IOException ex) {System.out.println("Error"); }
        
    }

    @FXML
    private void editaralbum(ActionEvent event) {
          Dialog dialog = new Dialog();
          dialog.setTitle("Editar Álbum");
          dialog.setResizable(true);
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
          GridPane gridPane = new GridPane();
          gridPane.add(new Label("Seleccione album"), 0, 0);
          
          
          ComboBox cbxalbum = new ComboBox();
          cbxalbum.getItems().setAll(llenarCombo());
          gridPane.add(cbxalbum, 1, 0);
          
          cbxalbum.setOnAction(evvb -> {
              gridPane.add(new Label("Nombre"), 0, 2);
              gridPane.add(new Label("Descripcion"), 0, 3);
              
              TextField txtnom = new TextField(albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).getNombre());
              TextField txtdes = new TextField(albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).getDescripcion());
              
              gridPane.add(txtnom, 1, 2);
              gridPane.add(txtdes, 1, 3);
              
              Button bttnmod = new Button("Modificar Album");
              Button bttnelmininar = new Button("Eliminar Album");
              
              gridPane.add(bttnmod, 0, 5);
              gridPane.add(bttnelmininar , 1, 5);
              
              bttnmod.setOnMouseClicked(ev6 -> {
                  
                  albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).setNombre(txtnom.getText());
                  albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).setDescripcion(txtdes.getText());
                  serializaralbumes();
                  pcontenido.getChildren().clear();
                  llenaralbumes();
                  dialog.close();
                  mostraralertaconfirmacion("Album modificado correctamente");
              });
              
              bttnelmininar.setOnMouseClicked(ev7-> {                  
                  albumes.remove(cbxalbum.getSelectionModel().getSelectedIndex());
                  serializaralbumes();
                  pcontenido.getChildren().clear();
                  llenaralbumes();
                  dialog.close();
                  mostraralertaconfirmacion("Album eliminado correctamente");
              });

          });
         dialog.getDialogPane().setContent(gridPane);
         dialog.show();    
        
    }
    
    public ArrayList<String> llenarCombo() {
        ArrayList<String> salbumes = new ArrayList<>();
        for(Album album : albumes) {
            salbumes.add(album.getNombre());
        }
        return salbumes;
        
    }
}
