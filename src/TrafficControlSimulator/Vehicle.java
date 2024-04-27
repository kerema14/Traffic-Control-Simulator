package TrafficControlSimulator;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Vehicle extends Pane{
	
	public double paneX;
	public double paneY;
	public Path carPath;
	public PathTransition carPathTransition = new PathTransition();;
	public boolean collidible = true;
	public boolean wrecked = false;
	public boolean moving = true;
	public double pathLength;
	Rectangle carRectangle;
	
	public Vehicle(Path path) {
		this.paneX = ((MoveTo)(path.getElements().get(0))).getX();;
		this.paneY = ((MoveTo)(path.getElements().get(0))).getY();;
		carPath = path;
		
		this.getChildren().add(createCar());
		
	}
	private Rectangle getCarRectangle(){
		return this.carRectangle;
	}
	

	private Rectangle createCar() {
		Random rand = new Random();
		carRectangle = new Rectangle(800.0 / 40.0, 800.0 / 80.0);
		
	    this.setMaxHeight(200.0 / 80.0);
		this.setMaxWidth(200.0 / 40.0);
		this.setWidth(200.0 / 40.0);
		this.setHeight(200.0 / 80.0);
		
		int red = rand.nextInt(225);
		int green = rand.nextInt(225);
		int blue = rand.nextInt(225);
		
        carRectangle.setFill(Color.rgb(red, green, blue, .99));
		carRectangle.setArcHeight(5);
		carRectangle.setArcWidth(5);
		
		return carRectangle;
	}
	
	public void checkCars(ArrayList<Vehicle> cars) {
		//check if car is ahead
		for(Vehicle vehicle:cars){
			if (checkCollision(this, vehicle) && (this.moving == true)) {
				this.handleDeath();
				//collision count needs to be handled! currently is not very correct
				
				
			}
		}
	}
	private boolean checkCollision(Vehicle v1,Vehicle v2){
		boolean collision = false;
		
		if (v1.getBoundsInParent().intersects(v2.getBoundsInParent()) && (v1.isCollidible() && v2.isCollidible())&& (v1!=v2)) {
			collision = true;
			carRectangle.setStrokeType(StrokeType.INSIDE);
			carRectangle.setStrokeWidth(3.35);
			carRectangle.setStroke(Color.RED);
		}
		return collision;

	}
	
	
	public void checkTrafficLights(ArrayList<TrafficLight> lights) {
		//check if lights are ahead
		
		
	}
	private double calculatePathLength(Path path) {
        double totalLength = 0.0;
		LineTo previousLineTo = null;
		MoveTo moveTo = null;
        for (PathElement element : path.getElements()) {
			if (element instanceof MoveTo) {
				moveTo = (MoveTo)element;
			}
			
            if (element instanceof LineTo) {
                LineTo lineTo = (LineTo) element;
				if (previousLineTo == null) {
					previousLineTo = lineTo;
					totalLength += Math.sqrt(Math.pow(moveTo.getX() - lineTo.getX(), 2)
                        + Math.pow(moveTo.getY() - lineTo.getY(), 2));

					continue;
				}
                totalLength += Math.sqrt(Math.pow(previousLineTo.getX() - lineTo.getX(), 2)
                        + Math.pow(previousLineTo.getY() - lineTo.getY(), 2));
				previousLineTo = lineTo;
            }
			
           
        }
		
        return totalLength;
    }
	public void startPathTransition() {
		pathLength = calculatePathLength(carPath);
		
		collidible = true;
		this.moving = true;
		carPathTransition.setDuration(Duration.millis(pathLength/0.25));
		
		carPathTransition.setNode(this);
		carPathTransition.setPath(carPath);
		carPathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		carPathTransition.setCycleCount(1);
		carPathTransition.setAutoReverse(false);
		carPathTransition.setInterpolator(Interpolator.LINEAR);
		carPathTransition.play();
		carPathTransition.setOnFinished(event -> {
			carRectangle.setStrokeWidth(1.35);
			carRectangle.setStrokeType(StrokeType.INSIDE);
			carRectangle.setStroke(Color.GREEN);
			
			handleWin();
		});
	}
	public void pausePathTransition(){
		carPathTransition.pause();
	}/* 
	public double getPathLength(){
		double pathLength;

	}*/
	public void handleWin(){
		
		this.pausePathTransition();
		this.moving = false;
		//wait function here
		Vehicle v = this;
		
		AnimationTimer timer = new AnimationTimer() { 
			double timeStamp = 0;
			double totalMillisecondsPassed;
			boolean stamped;
            @Override 
            public void handle(long now) { 
				totalMillisecondsPassed = now/1000000.0;
				if (stamped == false) {
					timeStamp = now/1000000.0;
					stamped = true;
				}
				if (totalMillisecondsPassed-timeStamp > 0) {
					
					v.getChildren().removeAll();
					v.setVisible(false);
					collidible = false;
					this.stop();
				}
            } 
        }; 
        timer.start(); 
	}
	public void handleDeath(){
		
		this.pausePathTransition();
		this.moving = false;
		this.wrecked = true;
		//wait function here
		Vehicle v = this;
		
		AnimationTimer timer = new AnimationTimer() { 
			double timeStamp = 0;
			double totalMillisecondsPassed;
			boolean stamped;
            @Override 
            public void handle(long now) { 
				totalMillisecondsPassed = now/1000000.0;
				if (stamped == false) {
					timeStamp = now/1000000.0;
					stamped = true;
				}
				if (totalMillisecondsPassed-timeStamp > 500) {
					
					v.getChildren().removeAll();
					v.setVisible(false);
					collidible = false;
					this.stop();
				}
            } 
        }; 
        timer.start(); 
	}

	public boolean isCollidible() {
		return collidible;
	}

	public boolean isMoving() {
		return moving;
	}
	
}