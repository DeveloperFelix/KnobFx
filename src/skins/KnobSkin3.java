/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skins;

import com.sun.javafx.scene.paint.GradientUtils.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import knobfx.Knob;

/**
 *
 * @author Anon
 */
public class KnobSkin3 extends SkinBase<Knob>{
    
    private final String SVG_PATH="m 454.98877,248.94726 c 0,0.39977 -9.56025,150.57106 -9.57608,150.42011 -0.0532,-0.50654 -9.56232,-150.45757 -9.54482,-150.51315 0.0114,-0.0362 2.16606,7.24709 4.78805,16.18532 l 4.76728,16.25133 4.75953,-16.24846 c 2.61774,-8.93665 4.76999,-16.24845 4.78278,-16.24845 0.0128,0 0.0233,0.0689 0.0233,0.1533 z";
    private final ObservableList<String> ROMAN_NUM=FXCollections.observableArrayList("0","X","XX","XXX","XL","L");

    private static final double PREFERRED_WIDTH  = 500;
    private static final double PREFERRED_HEIGHT = 500;
    private static final double MINIMUM_WIDTH    = 50;
    private static final double MINIMUM_HEIGHT   = 50;
    private static final double MAXIMUM_WIDTH    = 1024;
    private static final double MAXIMUM_HEIGHT   = 1024;
    
    double     sinValue;
    double     cosValue;
    double     center_radiusX; 
    double     center_radiusY;
    double     startAngle;
    double     angleStep;
    double     centerX;
    double     centerY;
    double     radius;
    double     valueAngle;

    private    Canvas canvas;
    private    GraphicsContext gc ;

 

public KnobSkin3(Knob knob){
         super(knob);
         
         init();
         backgroundCircle();
         initGraphics();
         border();
         registerListeners();
      
    }
    @Override
public void dispose() {
        getChildren().clear();
        super.dispose();
    }
private void init() {
        if (Double.compare(getSkinnable().getPrefWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getPrefHeight(), 0.0) <= 0 ||
            Double.compare(getSkinnable().getWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getHeight(), 0.0) <= 0) {
            if (getSkinnable().getPrefWidth() < 0 && getSkinnable().getPrefHeight() < 0) {
                getSkinnable().setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }
        if (Double.compare(getSkinnable().getMinWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getMinHeight(), 0.0) <= 0) {
            getSkinnable().setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        }

        if (Double.compare(getSkinnable().getMaxWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getMaxHeight(), 0.0) <= 0) {
            getSkinnable().setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);
        }
    }
private void initGraphics(){
      
        startAngle = getSkinnable().getStartAngle();
        angleStep  = getSkinnable().getAngleStep();
        centerX    = (getSkinnable().getPrefWidth())  * 0.5;
        centerY    = (getSkinnable().getPrefHeight()) * 0.5;
        radius     = getSkinnable().getPrefWidth()  * 0.00001;

        canvas = new Canvas(getSkinnable().getPrefWidth(),getSkinnable().getPrefHeight());
        gc     = canvas.getGraphicsContext2D();
 
        int i=0;
        
        gc.setFill(getSkinnable().getTickMarkColor());
        gc.setStroke(getSkinnable().getTickMarkColor());
        
        valueTextDisplay();
        
        gc.setFont(Font.getDefault());
        gc.setLineWidth(getSkinnable().getPrefWidth() * 0.006);
        
        for (double angle = 0, counter = 0; counter <= getSkinnable().getMaxValue() ; angle -=angleStep, counter++) {
            
            sinValue = Math.sin(Math.toRadians(angle + startAngle));
            cosValue = Math.cos(Math.toRadians(angle + startAngle));

            center_radiusX = centerX  * sinValue + centerX;
            center_radiusY = centerY  * cosValue + centerY;
 
            if(counter % 10 == 0){
                
               double minor_ticksX = (centerX * 0.8)  * sinValue + centerX;
               double minor_ticksY = (centerY * 0.8)  * cosValue + centerY;
               
               double textX = (centerX * 0.685)  * sinValue + centerX;
               double textY = (centerY * 0.685)  * cosValue + centerY;
     
               gc.setGlobalAlpha(0.5);

               gc.fillText(ROMAN_NUM.get(i), textX, textY);
               gc.strokeLine(center_radiusX, center_radiusY, minor_ticksX,minor_ticksY);
               
               i++; 
            }else{
                
               double minor_ticksX_ = (centerX * 0.9)  * sinValue + centerX;
               double minor_ticksY_ = (centerY * 0.9)  * cosValue + centerY; 
               
               gc.setGlobalAlpha(0.3);
               gc.strokeLine(center_radiusX, center_radiusY, minor_ticksX_,minor_ticksY_);
              
            }

        }

           pointer();

           getChildren().add(canvas);
  
    }
private void pointer(){
       
        double valueAng = getSkinnable().getStartAngle() - (getSkinnable().getValue() - getSkinnable().getMinValue()) * getSkinnable().getAngleStep();   

        SVGPath svg_arrow=new SVGPath();
        svg_arrow.setFill(getSkinnable().getKnobStroke());

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(getSkinnable().getPrefWidth() * 0.012);
        dropShadow.setOffsetX(getSkinnable().getPrefWidth() * 0.0005);
        dropShadow.setOffsetY(getSkinnable().getPrefWidth() * 0.0005);
        dropShadow.setColor(Color.RED);
        svg_arrow.setEffect(dropShadow);

        svg_arrow.setContent(SVG_PATH);
        svg_arrow.setScaleX(getSkinnable().getPrefWidth() * 0.004);
        svg_arrow.setScaleY(getSkinnable().getPrefWidth() * 0.004);
        svg_arrow.setRotate(-valueAng);
       
        Circle center_circle=new Circle();
        center_circle.setFill(getSkinnable().getKnobFill());
        center_circle.setEffect(dropShadow);
        center_circle.setRadius(getSkinnable().getPrefWidth() * 0.07);
        center_circle.setLayoutX(getSkinnable().getPrefWidth() * 0.45);
        center_circle.setLayoutY(getSkinnable().getPrefWidth() * 0.45);
       
        getChildren().addAll(svg_arrow,center_circle);
   }
private void valueTextDisplay(){
    
    gc.setLineWidth(getSkinnable().getPrefWidth() * 0.0006);
    gc.setGlobalAlpha(0.5);
    gc.strokeRect(centerX * 0.81, centerY * 1.6, getSkinnable().getPrefWidth() * 0.2,
                  getSkinnable().getPrefHeight() * 0.1);
    
    gc.setFont(Font.font("castellar",getSkinnable().getPrefWidth() * 0.115));
    gc.fillText(String.valueOf((int)getSkinnable().getValue()),centerX * 0.9, centerY * 1.78);
}
private void backgroundCircle(){
    
   Circle bg_circle=new Circle();
   bg_circle.setRadius(getSkinnable().getPrefWidth() * 0.5);

   getChildren().add(bg_circle);
} 
private void border(){
    
    Circle border=new Circle();
    border.setFill(Color.TRANSPARENT);
    border.setRadius(getSkinnable().getPrefWidth() * 0.5);
    border.setStrokeWidth(getSkinnable().getPrefWidth() * 0.026);
    border.setStroke(Color.WHITE);
   
    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(getSkinnable().getPrefWidth() * 0.0195);
    dropShadow.setOffsetX(getSkinnable().getPrefWidth() * 0.0009);
    dropShadow.setOffsetY(getSkinnable().getPrefWidth() * 0.0009);
    dropShadow.setColor(Color.BLACK);

    border.setEffect(dropShadow);

    getChildren().add(border);
    
}
private void registerListeners() {
         getSkinnable().valueProperty().addListener(e -> {
             getChildren().clear();
             initGraphics();
             border();
         });
 }
}
