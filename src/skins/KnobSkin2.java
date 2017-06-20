/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skins;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import knobfx.Knob;

/**
 *
 * @author Anon
 */
public class KnobSkin2 extends SkinBase<Knob>{
       
    private static final double PREFERRED_WIDTH  = 300;
    private static final double PREFERRED_HEIGHT = 300;
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

 
  public KnobSkin2(Knob knob){
         super(knob);
         init();
         initGraphics();
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
        centerX    = getSkinnable().getPrefWidth()  * 0.5;
        centerY    = getSkinnable().getPrefHeight() * 0.5;
        radius     = getSkinnable().getPrefWidth()  * 0.01;
        
        canvas = new Canvas(getSkinnable().getPrefHeight() + radius,getSkinnable().getPrefWidth() + radius);
        gc     = canvas.getGraphicsContext2D();
        
        //tick marks width
        gc.setLineWidth(getSkinnable().getPrefWidth() * 0.006);
        
        for (double angle = 0, counter = 0; counter <= getSkinnable().getMaxValue() ; angle -=angleStep, counter++) {
            
            sinValue = Math.sin(Math.toRadians(angle + startAngle));
            cosValue = Math.cos(Math.toRadians(angle + startAngle));

            center_radiusX = centerX  * sinValue + centerX;
            center_radiusY = centerY  * cosValue + centerY;
            
            double minor_ticksX = (centerX * 0.93)  * sinValue + centerX;
            double minor_ticksY = (centerY * 0.93)  * cosValue + centerY;
            
            double major_ticksX = (centerX * 0.85)  * sinValue + centerX;
            double major_ticksY = (centerY * 0.85)  * cosValue + centerY;
            
            double center_radiusX_point = (centerX * 0.82)  * sinValue + centerX;
            double center_radiusY_point = (centerY * 0.82)  * cosValue + centerY;
    
            double center_radiusX_label = (centerX * 0.73)  * sinValue + centerX;
            double center_radiusY_label = (centerY * 0.73)  * cosValue + centerY;
              
            //major tick marks
            if(counter % 10 == 0 && counter > 0){
   
                if(counter == 40 || counter == 50){
                    gc.setFill(Color.RED);
                    gc.setStroke(Color.RED);
                }
               gc.strokeLine(center_radiusX, center_radiusY, major_ticksX,major_ticksY); 
               gc.fillText(String.valueOf((int)counter), center_radiusX_label, center_radiusY_label);
               
              // minor tick marks 
             }else if(counter < 40 && counter > 0){
          
                gc.setFill(getSkinnable().getTickMarkColor());
                gc.setStroke(getSkinnable().getTickMarkColor());
                gc.strokeLine(center_radiusX, center_radiusY, minor_ticksX,minor_ticksY);  
                gc.fillOval(center_radiusX_point, center_radiusY_point, radius,radius);
               // 0th tick mark 
            }else if(counter == 0){
               gc.setFill(getSkinnable().getTickMarkColor());
               gc.setStroke(getSkinnable().getTickMarkColor());
               gc.strokeLine(center_radiusX, center_radiusY, major_ticksX,major_ticksY); 
               gc.fillText(String.valueOf((int)counter), center_radiusX_label, center_radiusY_label);   
             }
            //tick marks >= 40
            if(counter >= 40){
     
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);
                gc.strokeLine(center_radiusX, center_radiusY, minor_ticksX, minor_ticksY);
         
                if(counter != 40 && counter !=50){
                   gc.fillOval(center_radiusX_point, center_radiusY_point, radius,radius); 
                }
            }

        }
          
          pointer(centerX,centerY);
      
          getChildren().addAll(canvas);
  }
  private void pointer(double centerX,double centerY){
      
        valueAngle = getSkinnable().getStartAngle() - (getSkinnable().getValue() - getSkinnable().getMinValue()) * getSkinnable().getAngleStep();
        radius     = getSkinnable().getPrefWidth()  * 0.09;
        
        sinValue = Math.sin(Math.toRadians(valueAngle));
        cosValue = Math.cos(Math.toRadians(valueAngle));
        
        center_radiusX = (centerX * 0.70) * sinValue + centerX ;
        center_radiusY = (centerY * 0.70) * cosValue + centerY ;
        
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineDashes(getSkinnable().getPrefWidth()  * 0.02);
        gc.setStroke(getSkinnable().getMarkerColor());
        gc.strokeLine(centerX,centerY,center_radiusX,center_radiusY);
  
        centerX    = getSkinnable().getPrefWidth()  * 0.46;
        centerY    = getSkinnable().getPrefHeight() * 0.46;
        
        gc.setStroke(getSkinnable().getKnobStroke());
        gc.setFill(getSkinnable().getKnobFill());
        gc.setLineDashes(0);
        gc.fillOval(centerX,centerY,radius,radius);
        gc.strokeOval(centerX, centerY, radius, radius);

  }

   private void registerListeners() {
         getSkinnable().valueProperty().addListener(e -> {
             getChildren().clear();
             initGraphics();
         });
    }
}
