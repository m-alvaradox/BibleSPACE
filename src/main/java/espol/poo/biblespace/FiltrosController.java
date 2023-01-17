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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    @FXML
    private Text txtresal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void radioCheck(ActionEvent event) {
        hboxafiltrar.getChildren().clear();
        hscroll.getChildren().clear();
        textoresultado1.setText("");
        txtresal.setText("");
        
        if(radioLugar.isSelected()) {
            ComboBox cbxlugares = new ComboBox();
            cbxlugares.getItems().setAll(llenarcombolugares());
            
            cbxlugares.setOnAction(ev -> {
                textoresultado1.setText("Mostrando resultados");
                hscroll.getChildren().clear();

                for(Album alx : albumes ) {
                    for(Foto fts : alx.getFotos()) {
                        if(fts.getLugar().equals((String) cbxlugares.getValue())) {
                            ImageView img = crearimagen(fts);
                            Tooltip tltp = generarTooltip(fts,alx);
                            Tooltip.install(img, tltp);
                            hscroll.getChildren().add(img);
                        }
                    }
                }
            });

            hboxafiltrar.getChildren().add(new Label("Buscar fotos tomadas en: "));
            hboxafiltrar.getChildren().add(cbxlugares);
 
        }
        
        if(radioPersona.isSelected()) {
            ComboBox cbxpersonas = new ComboBox();
            cbxpersonas.getItems().setAll(llenarcombopersonas());
            
            cbxpersonas.setOnAction(ev1 -> {
                textoresultado1.setText("Mostrando resultados");
                ArrayList<Album> alganador = new ArrayList<>();
                alganador.removeAll(alganador);
                String msg = (String) cbxpersonas.getValue() + " aparece en: ";
                
                hscroll.getChildren().clear();
                for(Album alx1 : albumes) {
                    for(Foto fts1 : alx1.getFotos()) {
                        for(Persona per : fts1.getPersonas()) {    
                            if(per.getNombre().equals(AlbumvisController.personas.get(cbxpersonas.getSelectionModel().getSelectedIndex()).getNombre())
                                    & per.getApellido().equals(AlbumvisController.personas.get(cbxpersonas.getSelectionModel().getSelectedIndex()).getApellido())) {
                                
                                ImageView img2 = crearimagen(fts1);
                                Tooltip tltp = generarTooltip(fts1,alx1);
                                Tooltip.install(img2, tltp);
                                hscroll.getChildren().add(img2); 
                                
                                if(!alganador.contains(alx1)) {
                                    alganador.add(alx1); 
                                    msg += alx1.getNombre() + ", ";  }
                            }
                        }
                        
                            
                        }
                    }
                txtresal.setText(msg);
            });
   
            hboxafiltrar.getChildren().add(new Label("Buscar fotos o albumes donde aparece: "));
            hboxafiltrar.getChildren().add(cbxpersonas);
        }
        
        if(radioFecha.isSelected()) {
            DatePicker desde = new DatePicker();
            DatePicker hasta = new DatePicker();
            Button bttnbuscar = new Button("Buscar");
            hboxafiltrar.getChildren().add(new Label("Desde: "));
            hboxafiltrar.getChildren().add(desde);
            hboxafiltrar.getChildren().add(new Label("Hasta: "));
            hboxafiltrar.getChildren().add(hasta);
            hboxafiltrar.getChildren().add(bttnbuscar);
            
            bttnbuscar.setOnMouseClicked(eventb -> {
                String dateinicio = desde.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String datefinal = desde.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                
            });

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
    
    public ArrayList<String> llenarcombopersonas() {
        ArrayList<String> lpersonas = new ArrayList<>();
        for(Persona p : AlbumvisController.personas) {
            lpersonas.add(p.getNombre() +" "+ p.getApellido());
        }
        return lpersonas;
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
    
    public Tooltip generarTooltip(Foto ft, Album al) {
        
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
                    +"\nTomada el: "+ft.getFecha()+"\nAparecen: "+participantes +
                    "\nPertenece a: "+al.getNombre());
            t.setFont(Font.font("Verdana", FontPosture.REGULAR, 10));
        return t;    
    }
}
