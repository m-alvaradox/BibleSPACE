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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Button bttnback;
    @FXML
    private Text nomalbum;
    
    private ArrayList<Album> albumes = Album.cargarAlbumes(App.archgaleria);
    private ArrayList<Foto> fotos = Foto.cargarFotografias(albumes.get(PrincipalController.indice));
    public static ArrayList<Persona> personas = Persona.cargarPersonas(App.filepeople);
    @FXML
    private Button bttneditar;
    @FXML
    private Button bttnaddpeople;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip tbuttonadd = new Tooltip("Cargar");
        Tooltip tbttnslide = new Tooltip("Slideshow");
        Tooltip tbbtnmove = new Tooltip("Editar");
        bttnupload.setTooltip(tbuttonadd);
        bttneditar.setTooltip(tbbtnmove);
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
             Foto ft = new Foto(imgFile.getName(),des.getText(),place.getText().toUpperCase(),date,pseleccionadas,null);

             fotos.add(ft);
             
             albumes.get(PrincipalController.indice).setFotos(fotos);
             
             serializaralbumes();
             
             llenarImagenes(albumes.get(PrincipalController.indice));
                
             dialog.close();
             mostraralertaconfirmacion("Foto agregada correctamente");
                
             System.out.println(imgFile.getName() + " agregada correctamente");
    
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
    
    public void llenarImagenes(Album album) {
        pcontenido.getChildren().clear();
       
        try {
            if(album.getFotos() == null) {
                pcontenido.getChildren().add(new Label("<Vacio>"));
            }
            if(album.getFotos().isEmpty()) {
                pcontenido.getChildren().add(new Label("<Vacio>"));
            }
            
        for(Foto ft : album.getFotos()) {
            VBox cont = new VBox(1);
            Tooltip t = generarTooltip(ft);
            ImageView imageView = new ImageView();
            try {
                FileInputStream input = new FileInputStream(ft.getUrl());
                Image image = new Image(input);
                imageView.setImage(image);
                
                // tamaño porcentaje
                //imageView.setFitWidth(0.10*image.getWidth());
                
                imageView.setFitWidth(150);
                imageView.setFitHeight(100);
                
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


    @FXML
    private void editarfotografia(ActionEvent event) {
          Dialog dialog3 = new Dialog();
          dialog3.setTitle("Editar Foto");
          dialog3.setResizable(true);
          dialog3.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
          
          ComboBox cbxfoto = new ComboBox();
          cbxfoto.getItems().setAll(llenarCombo(fotos));
          
          ImageView prev1 = new ImageView();
          
          cbxfoto.setOnAction(evv-> {
     
            try {
                FileInputStream input1 = new FileInputStream((String)cbxfoto.getValue());
                Image image2 = new Image(input1);
                prev1.setImage(image2);
                prev1.setFitWidth(70);
                prev1.setFitHeight(70);
                
            } catch(IOException ex) {
                System.out.println("No se encuentra el archivo");
            }
   
            System.out.println(cbxfoto.getValue());
             });
          
          ComboBox cbxalbum = new ComboBox();
          cbxalbum.getItems().setAll(llenarCombo2(albumes));
          
          cbxalbum.setOnAction(evv2-> {
              System.out.println("Album destino: "+cbxalbum.getValue());
          });
          
          Button bttnmover = new Button("Guardar cambios");
          
          bttnmover.setOnMouseClicked(ev5 -> {
              
              
              try {
              albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).getFotos().add(fotos.get(cbxfoto.getSelectionModel().getSelectedIndex()));
              } catch(Exception e) {
                  ArrayList<Foto> primera = new ArrayList<>();
                  primera.add(fotos.get(cbxfoto.getSelectionModel().getSelectedIndex()));
                  albumes.get(cbxalbum.getSelectionModel().getSelectedIndex()).setFotos(primera);
              }
              
              albumes.get(PrincipalController.indice).getFotos().remove(fotos.get(cbxfoto.getSelectionModel().getSelectedIndex()));
              
              
              serializaralbumes();
              llenarImagenes(albumes.get(PrincipalController.indice));
              
              System.out.println(cbxfoto.getValue() + " fue movido a: "+cbxalbum.getValue());
 
              dialog3.close();
              mostraralertaconfirmacion("Foto editada correctamente");
                
          });
          
          
          GridPane gridPane3 = new GridPane();
          gridPane3.add(new Label("Seleccione foto "), 0, 0);
          gridPane3.add(cbxfoto, 1, 0);
          
          gridPane3.add(new Label("Mover a "), 0, 10);
          gridPane3.add(cbxalbum,1,10);
          gridPane3.add(bttnmover, 1, 11);
          gridPane3.add(prev1, 5, 10);
          
          
          
          dialog3.getDialogPane().setContent(gridPane3);
          dialog3.show();
    }
    
    public ArrayList<String> llenarCombo(ArrayList<Foto> fotos1) {
        ArrayList<String> sfotos = new ArrayList<>();
        for(Foto ph : fotos1) {
            sfotos.add(ph.getUrl());
        }
        
        return sfotos;
        
    }
    
    public ArrayList<String> llenarCombo2(ArrayList<Album> albumes1) {
        ArrayList<String> salbumes = new ArrayList<>();
        for(Album al2 : albumes1) {
            salbumes.add(al2.getNombre());
            
        }
        
        return salbumes;
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
    
    public void mostraralertaconfirmacion(String msg) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Confirmation Dialog");
             alert.setHeaderText("Resultado de la operacion");
             alert.setContentText(msg);
             alert.showAndWait();      
    }
    
    public Tooltip generarTooltip(Foto ft) {
        
        String participantes = "";
            
            if((ft.getPersonas() == null)) {
                participantes = "Sin informacion";
            } else if(ft.getPersonas().isEmpty()) {
                participantes = "Sin informacion";
            } else {
                for(Persona p : ft.getPersonas()){
                     participantes += p.getNombre() + " "; 
                } 
            }
            
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
            
        return t;
    }
    
    
}
