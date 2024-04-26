package TrafficControlSimulator;

import java.util.ArrayList;



import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Vehicle extends Pane{
	
	public double paneX;
	public double paneY;
	public Path carPath;
	public PathTransition carPathTransition = new PathTransition();;
	public boolean collidible = true;
	public boolean moving = true;
	public double pathLength;
	Rectangle carRectangle;
	
	public Vehicle(Path path) {
		this.paneX = ((MoveTo)(path.getElements().get(0))).getX();;
		this.paneX = ((MoveTo)(path.getElements().get(0))).getY();;
		carPath = path;
		this.getChildren().add(getCar());
	}

	private Rectangle getCar() {
		carRectangle = new Rectangle(800.0 / 40.0, 800.0 / 80.0);
		carRectangle.setStyle("-fx-fill: blue");
		return carRectangle;
	}
	
	public void checkCars(ArrayList<Vehicle> cars) {
		//check if car is ahead
		for(Vehicle vehicle:cars){
			if (checkCollision(this, vehicle) ) {
				this.handleDeath();
				
			}
		}
	}
	private boolean checkCollision(Vehicle v1,Vehicle v2){
		boolean collision = false;
		if (v1.getBoundsInParent().intersects(v2.getBoundsInParent()) && (v1.isCollidible() && v2.isCollidible())&& (v1!=v2)) {
			collision = true;
			carRectangle.setStrokeType(StrokeType.INSIDE);
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
		System.out.println(totalLength);
        return totalLength;
    }
	public void startPathTransition() {
		pathLength = calculatePathLength(carPath);
		System.out.println(pathLength);
		collidible = true;
		this.moving = true;
		
		carPathTransition.setRate(100.0/pathLength);
		carPathTransition.setNode(this);
		carPathTransition.setPath(carPath);
		carPathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		carPathTransition.setCycleCount(1);
		carPathTransition.setAutoReverse(false);
		carPathTransition.play();
		carPathTransition.setOnFinished(event -> {
			carRectangle.setStrokeType(StrokeType.INSIDE);
			carRectangle.setStroke(Color.GREEN);
			
			handleDeath();
		});
	}
	public void pausePathTransition(){
		carPathTransition.pause();
	}/* 
	public double getPathLength(){
		double pathLength;

	}*/
	public void handleDeath(){
		
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