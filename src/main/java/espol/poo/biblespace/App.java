package espol.poo.biblespace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static String archgaleria = "galeria.ser";
    public static String fileimages = "espol/poo/images/";
    public static String filepeople = "personas.ser";

    //metodo para cambiar el contenido de la escena

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("principal"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("BIBLESPACE");
        System.out.println("----------BIBLESPACE----------\n Elaborado por Grupo #4 - POO\n"
                + "- Mario Alvarado\n- Austin Estrella\n- Andres Layedra");
        stage.show();
    }
 
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void changeRoot(Parent rootNode) {
        scene.setRoot(rootNode);
    }

}