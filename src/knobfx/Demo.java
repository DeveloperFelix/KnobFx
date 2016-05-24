/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knobfx;

import java.util.Random;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author F-effect
 */
public class Demo extends Application {
    
Random RND=new Random();
int i=0;
StackPane root=new StackPane();
Timeline tl=new Timeline();
   
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        
        i=RND.nextInt(25);
     
        Knob k=new Knob();
        k.setPrefHeight(250);
        k.setPrefWidth(250);

        root.getChildren().add(k);
        
        final KeyValue KEY_VALUE = new KeyValue(k.valueProperty(), 50, Interpolator.LINEAR);
        final KeyFrame KEY_FRAME = new KeyFrame(Duration.seconds(20), KEY_VALUE);
        tl.getKeyFrames().setAll(KEY_FRAME);
        tl.play();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/resources/style.css").toExternalForm());
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
