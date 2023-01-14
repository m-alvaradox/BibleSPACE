/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.Album;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
/**
 * FXML Controller class
 *
 * @author Mario
 */
public class AlbumvisController implements Initializable {


    @FXML
    private FlowPane pcontenido;
    @FXML
    private Button bttnupload;
    @FXML
    private Button bttnslide;
    @FXML
    private Button bttnmove;
    @FXML
    private Button bttnback;
    @FXML
    private Text nomalbum;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip tbuttonadd = new Tooltip("Cargar");
        Tooltip tbttnslide = new Tooltip("Slideshow");
        Tooltip tbbtnmove = new Tooltip("Mover");
        bttnupload.setTooltip(tbuttonadd);
        bttnmove.setTooltip(tbbtnmove);
        bttnslide.setTooltip(tbttnslide);
    }    
    
    @FXML
    private void btncargar(ActionEvent event) throws IOException {
        
        // Obtener la imagen seleccionada
        File imgFile = buscarArchivo();
   
        if(imgFile != null) {
          Image image = new Image("file:" + imgFile.getAbsolutePath());
          System.out.println("Foto seleccionada: "+ imgFile.getName());
          
          ImageView prev = new ImageView(image);
          prev.setFitWidth(0.10*image.getWidth());
          prev.setFitHeight(0.10*image.getHeight());
          
          Dialog dialog = new Dialog();
          dialog.setTitle("Nuevo Álbum");
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
          GridPane gridPane = new GridPane();
          gridPane.add(new Label("Foto seleccionada"), 0, 0);
          gridPane.add(prev, 0, 1);
          gridPane.add(new Label("Nombre: "+imgFile.getName()), 0, 5);
          gridPane.add(new Label(""),0,6);
          gridPane.add(new Label("Ingresa una descripción corta para esta imagen "), 0, 7);
          gridPane.add(new Label("Ingrese lugar: "), 0, 8);
          gridPane.add(new Label("Ingrese fecha: "), 0, 9); //calendar
          gridPane.add(new Label("¿Quienes aparecen en la foto?: "), 0, 10); //checkedbox
          gridPane.add(new Label("Guardar imagen en: "), 0, 11);
          
         TextField des = new TextField();
         TextField place = new TextField();
         TextField date = new TextField();
         date.setPromptText("26/01/2002");
         TextField people = new TextField();
         people.setPromptText("Marcos,Juana");
         
         ComboBox cmbAlbum = new ComboBox();
         ArrayList<String> albums = new ArrayList<>();
         for(Album al : Album.cargarAlbumes(App.archgaleria)) {
             albums.add(al.getNombre());
         }
         cmbAlbum.getItems().setAll(albums);
         
         gridPane.add(des,1,7);
         gridPane.add(place,1,8);
         gridPane.add(date,1,9);
         gridPane.add(people,1,10);
         gridPane.add(cmbAlbum, 1, 11);
         
         Button cr = new Button("Cargar");
         gridPane.add(new Label(""),0,12);
         gridPane.add(cr, 0, 13);
          
          
          
          dialog.getDialogPane().setContent(gridPane);
          dialog.show(); }
    }
    
    public File buscarArchivo() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Buscar archivo");
        
        // Agregar filtros para facilitar la busqueda
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        
        File imgFile = fc.showOpenDialog(null);
        return imgFile;    
    }
    
    
    @FXML
    private void regresar(ActionEvent event) throws IOException {
        App.setRoot("principal");
    }
    
    public void llenarTitulo(Album album) {
        nomalbum.setText(album.getNombre());
    }
    
    public void llenarImagenes(Album album) {
        
    }
    
}
