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
    
   

    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        StackPane root=new StackPane();
         
        Knob k=new Knob();
        k.setPrefHeight(450);
        k.setPrefWidth(450);

        root.getChildren().add(k);
 
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(this.getClass().getResource("/resources/style.css").toExternalForm());
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
