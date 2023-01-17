/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.poo.biblespace;

import espol.poo.objetos.Album;
import espol.poo.objetos.Foto;
import espol.poo.objetos.Persona;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author Mario
 */
public class FiltrosController implements Initializable {


    @FXML
    private Text nomalbum;
    @FXML
    private Button bttnback;
    
    private ArrayList<Album> albumes = Album.cargarAlbumes(App.archgaleria);

    @FXML
    private RadioButton radioFecha;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton radioLugar;
    @FXML
    private RadioButton radioPersona;
    @FXML
    private Text textoresultado1;
    @FXML
    private HBox hboxafiltrar;
    @FXML
    private HBox hscroll;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void radioCheck(ActionEvent event) {
        hboxafiltrar.getChildren().clear();
        hscroll.getChildren().clear();
        if(radioLugar.isSelected()) {
            ComboBox cbxlugares = new ComboBox();
            cbxlugares.getItems().setAll(llenarcombolugares());
            
            cbxlugares.setOnAction(ev -> {
                hscroll.getChildren().clear();
                
                for(Album alx : albumes ) {
                    for(Foto fts : alx.getFotos()) {
                        if(fts.getLugar().equals((String) cbxlugares.getValue())) {
                            ImageView img = crearimagen(fts);
                            Tooltip tltp = generarTooltip(fts);
                            Tooltip.install(img, tltp);
                            hscroll.getChildren().add(img);
                        }
                    }
                }
            });
            
            
            
            hboxafiltrar.getChildren().add(new Label("Buscar fotos o albumes tomadas de: "));
            hboxafiltrar.getChildren().add(cbxlugares);
            
            
            
            
            
        }
        
        if(radioPersona.isSelected()) {
            hboxafiltrar.getChildren().add(new Label("Persona is selected"));
        }
    }
    
    @FXML
    private void regresar(ActionEvent event) throws IOException {
        App.setRoot("principal");
    }
    
    public ArrayList<String> llenarcombolugares() {
        ArrayList<String> lugares = new ArrayList<>();
        for(Album al : albumes) {
            for(Foto ft : al.getFotos()) {
                if(!ft.getLugar().equals("")) {
                    if(!lugares.contains(ft.getLugar())) {
                        lugares.add(ft.getLugar());
                    }
                }
            }
            
        }
        return lugares;
        
    }
    
    public ImageView crearimagen(Foto ft) {
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
    return imageView;    
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
