
package espol.poo.slideshow;

import espol.poo.util.AnchorUtil;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NiceSliderShow extends AnchorPane {
    private static FadeTransition transition;
    private final StackPane stackPane = new StackPane();
    private final AnchorPane backPane = new AnchorPane();
    private final AnchorPane frontPane = new AnchorPane();
    private final ObservableList<Image> backImages =  FXCollections.observableArrayList();
    private final ObservableList<Image> frontImages = FXCollections.observableArrayList();
    private int backIndex = 0;
    private int frontIndex = 0;
    
    public NiceSliderShow() {
        super();
        initialize();
    }
    
    private void initialize() {
        this.stackPane.getChildren().addAll(backPane, frontPane);
        frontPane.toFront();
        frontPane.setOpacity(0);
        backPane.toBack();
        AnchorUtil.setAnchor(stackPane,0.0,0.0,0.0,0.0);
        getChildren().add(stackPane);
        
    }
    
    public void setImages(ArrayList<Image> images) { //Image...images
        if(images.size()<3) {
            try {
                throw new Exception("The image quantitu must be 3 or more!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            for(int i = 0 ; i<images.size() ; i++) {
                if(i%2==0) {
                    frontImages.add(images.get(i));
                } else {
                    backImages.add(images.get(i));
                }
        }
        }
        
        setBackgroundImage(backPane,backImages.get(0));
        setBackgroundImage(frontPane,frontImages.get(0));
    }
    
    public void initSliderShow(int animationDelay, int visibilityDelay) {
        Runnable rn = ()-> {
            Platform.runLater(() -> {
                frontPane.opacityProperty().addListener((observable, oldvalue, newValue)-> {
                   PauseTransition pt;
                   if(newValue.doubleValue()==0) {
                       frontIndex++;
                       if(frontIndex==frontImages.size()) {
                           frontIndex = 0;
                       }
                       setBackgroundImage(frontPane,frontImages.get(frontIndex));
                       pt = new PauseTransition(Duration.seconds(visibilityDelay));
                       pt.setOnFinished(event -> {
                           transition.play();
                       });
                       transition.pause();
                       pt.play();
                   } else if(newValue.doubleValue()==1) {
                       backIndex++;
                       if(backIndex == backImages.size()) {
                           backIndex = 0;
                       }
                       setBackgroundImage(backPane,backImages.get(backIndex));
                       pt = new PauseTransition(Duration.seconds(visibilityDelay));
                       pt.setOnFinished(event -> {
                        transition.play();
                        });
                        transition.pause();
                        pt.play();
                       
                   }
                });

        });
            transition = new FadeTransition(Duration.seconds(animationDelay),frontPane);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.setAutoReverse(true);
            transition.setCycleCount(-1);
            transition.play();
        };
        
        Thread t = new Thread(rn);
        t.start();
    }
    
    public synchronized void stop() {
        if(transition != null) {
            transition.stop();
        }
    }
    
    
    
    
    
    private void setBackgroundImage(AnchorPane target, Image image) {
        BackgroundSize backgroundSize = new BackgroundSize(100,100,true,true,true,true);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        target.setBackground(background);
        
    }
    
}
