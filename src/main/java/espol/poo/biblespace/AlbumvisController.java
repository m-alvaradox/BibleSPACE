/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
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
    private ArrayList<Foto> fotos = Foto.cargarFotografias(albumes.get(PrincipalController.indice));
    private ArrayList<Persona> personas = Persona.cargarPersonas(App.filepeople);
    @FXML
    private Button bttnaddpeople;
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
        
        llenarImagenes(albumes.get(PrincipalController.indice));
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
          gridPane.add(new Label("Su imagen se guardara en: "+albumes.get(PrincipalController.indice).getNombre()), 0, 7);
          gridPane.add(new Label(""),0,8);
          gridPane.add(new Label("Ingresa una descripción corta para esta imagen "), 0, 9);
          gridPane.add(new Label("Ingrese lugar: "), 0, 10);
          gridPane.add(new Label("Ingrese fecha: "), 0, 11); //calendar
          gridPane.add(new Label("¿Quienes aparecen en la foto?: "), 0, 12); //checkedbox
          
          
         TextField des = new TextField();
         TextField place = new TextField();
         
         
         DatePicker datepicker = new DatePicker();
         datepicker.setValue(LocalDate.now());
         
         HBox hpersonas = new HBox(personas.size());
         
         ArrayList<Persona> pseleccionadas = new ArrayList<>();
 
         for(Persona p : personas) {
             CheckBox cb = new CheckBox(p.getNombre() +" "+ p.getApellido());
             hpersonas.getChildren().add(cb);
             
             cb.setOnAction(e-> {
                 if(cb.isSelected()) {
                     pseleccionadas.add(p);
                 } else {
                     pseleccionadas.remove(p);
                 }
             });
         }
         
//         TextField people = new TextField();
//         people.setPromptText("Marcos,Juana");
         
         gridPane.add(des,1,9);
         gridPane.add(place,1,10);
         gridPane.add(datepicker,1,11);
         //gridPane.add(new Label(seleccionados),1,12);
         gridPane.add(hpersonas, 1, 13);
         
         Button cr = new Button("Cargar");
         gridPane.add(new Label(""),0,13);
         gridPane.add(cr, 0, 15);
         
         cr.setOnMouseClicked(evento1 -> {
             //copiar la imagen
             Path from = Paths.get(imgFile.toURI());
             Path to = Paths.get(imgFile.getName());
              try {
                  Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);
              } catch (IOException ex) {
                    System.out.println("Error en la copia del archivo");
              }
              
             String date = datepicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
             Foto ft = new Foto(imgFile.getName(),des.getText(),place.getText(),date,pseleccionadas,null);

             fotos.add(ft);
             
             albumes.get(PrincipalController.indice).setFotos(fotos);
             
             try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(App.archgaleria))) {
                 out.writeObject(albumes);
                 out.flush();
                llenarImagenes(albumes.get(PrincipalController.indice));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Resultado de la operacion");
                alert.setContentText("Foto agregada correctamente");
                System.out.println(imgFile.getName() + " agregada correctamente");
 
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
        fc.setTitle("Buscar archivoa");
        
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
        pcontenido.getChildren().clear();
       
        try {
            if(album.getFotos() == null) {
                pcontenido.getChildren().add(new Label("<Vacio>"));
            }
            
        for(Foto ft : album.getFotos()) {
            VBox cont = new VBox(1);
            String participantes = "";
            if((ft.getPersonas() != null)) {
                for(Persona p : ft.getPersonas()){
                     participantes += p.getNombre() + " "; 
                } 
            } else { participantes = "Sin informacion"; }
            
            String descripcion = "";
            if(ft.getDescripcion().equals("")) {
                descripcion += "Sin informacion";
            } else {descripcion += ft.getDescripcion(); }
            
            String lugar = "";
            if(ft.getLugar().equals("")) {
                lugar += "Sin informacion"; 
            } else { lugar += ft.getLugar();}
            
            Tooltip t = new Tooltip("Descripcion: "+descripcion+"\nLugar: "+lugar
                    +"\nTomada el: "+ft.getFecha()+"\nAparecen: "+participantes);
            t.setFont(Font.font("Verdana", FontPosture.REGULAR, 10));
            
            ImageView imageView = new ImageView();
            try {
                FileInputStream input = new FileInputStream(ft.getUrl());
                Image image = new Image(input);
                imageView.setImage(image);
                imageView.setFitWidth(0.10*image.getWidth());
                imageView.setFitHeight(0.10*image.getHeight());
                
            } catch(IOException ex) {
                System.out.println("No se encuentra el archivo");
            }
            Tooltip.install(imageView, t);
            
            cont.getChildren().addAll(imageView);
            pcontenido.getChildren().add(cont);
            
        }
        } catch(Exception ex) {
            System.out.println("Album vacio");
        }
        
    }

    @FXML
    private void agregarpersona(ActionEvent event) {
        Persona.agregarpersona();
    }
    
}
