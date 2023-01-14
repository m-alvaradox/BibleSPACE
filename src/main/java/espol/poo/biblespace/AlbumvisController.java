/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.Album;
import espol.poo.objetos.Foto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    private ArrayList<Album> albumes = Album.cargarAlbumes(App.archgaleria);
    private int indice;
    private ArrayList<Foto> fotos = new ArrayList<>();
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
        
        // Mostrar la imagen
        if(imgFile != null) {
          Image image = new Image("file:" + imgFile.getAbsolutePath());
          System.out.println("Foto seleccionada: "+ imgFile.getName());
          
          ImageView prev = new ImageView(image);
          prev.setFitWidth(0.10*image.getWidth());
          prev.setFitHeight(0.10*image.getHeight());
          
          Dialog dialog = new Dialog();
          dialog.setTitle("Cargar Foto");
          dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
          GridPane gridPane = new GridPane();
          gridPane.add(new Label("Foto seleccionada"), 0, 0);
          gridPane.add(prev, 0, 1);
          gridPane.add(new Label("Nombre: "+imgFile.getName()), 0, 5);
          gridPane.add(new Label(""),0,6);
          gridPane.add(new Label("Su imagen se guardara en: "+albumes.get(indice).getNombre()), 0, 7);
          gridPane.add(new Label(""),0,8);
          gridPane.add(new Label("Ingresa una descripción corta para esta imagen "), 0, 9);
          gridPane.add(new Label("Ingrese lugar: "), 0, 10);
          gridPane.add(new Label("Ingrese fecha: "), 0, 11); //calendar
          gridPane.add(new Label("¿Quienes aparecen en la foto?: "), 0, 12); //checkedbox
          
          
         TextField des = new TextField();
         TextField place = new TextField();
         TextField date = new TextField();
         date.setPromptText("26/01/2002");
         TextField people = new TextField();
         people.setPromptText("Marcos,Juana");
         
         gridPane.add(des,1,9);
         gridPane.add(place,1,10);
         gridPane.add(date,1,11);
         gridPane.add(people,1,12);
         
         Button cr = new Button("Cargar");
         gridPane.add(new Label(""),0,13);
         gridPane.add(cr, 0, 14);
         
         cr.setOnMouseClicked(evento1 -> {
             //copiar la imagen
             Path from = Paths.get(imgFile.toURI());
             Path to = Paths.get(imgFile.getName());
              try {
                  Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);
              } catch (IOException ex) {
                    System.out.println("Error en la copia del archivo");
              }             
             
             Foto ft = new Foto(imgFile.getName(),des.getText(),place.getText(),date.getText(),null,null);
             fotos.add(ft);
             albumes.get(indice).setFotos(fotos);
             
             try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(App.archgaleria))) {
                 out.writeObject(albumes);
                 out.flush();
                llenarImagenes(albumes.get(indice));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Resultado de la operacion");
                alert.setContentText("Foto agregada correctamente");
                dialog.close();
                alert.showAndWait();
                }
             catch(IOException ex) {
               System.out.println("IOException:" + ex.getMessage());  
             }
             
             
             
             
             
             
         });
         
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
    
    public void recogerIndice(int indice) {
        this.indice = indice;
    }
    
    public void llenarImagenes(Album album) {
        
    }
    
}
