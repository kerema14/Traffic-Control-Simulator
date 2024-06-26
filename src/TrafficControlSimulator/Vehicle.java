//150123045 Buğra Kaya
//150123055 Kerem Adalı 
//150122029 Ali Talip Keleş
package TrafficControlSimulator;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Vehicle extends Pane{
	
	public double paneX;
	public double paneY;
	public Path carPath;
	public PathTransition carPathTransition = new PathTransition();
	public boolean collidible = true;
	public boolean wrecked = false;
	
	public double pathLength;
	Rectangle carRectangle;
	
	double velocity = 0.2;
	public Line centerAntennaLine;
	public Line leftAntennaLine;
	public Line rightAntennaLine;
	
	public Vehicle(Path path) {
		this.paneX = ((MoveTo)(path.getElements().get(0))).getX();
		this.paneY = ((MoveTo)(path.getElements().get(0))).getY();
		carPath = path;
		
		this.getChildren().add(createCar());
		
		
	}
	
	public Rectangle getCarRectangle(){
		return this.carRectangle;
	}
	
	private Group createCar() {
		//Create a Car with Random Color 
		Random rand = new Random();
		carRectangle = new Rectangle(800.0 / 40.0, 800.0 / 80.0);
		this.setMaxSize(200.0 / 40.0, 200.0 / 80.0);
		Rectangle window = new Rectangle(800.0 / 80.0, 800.0 / 160.0,5,8);
		int red = rand.nextInt(225);
		int green = rand.nextInt(225);
		int blue = rand.nextInt(225);
		carRectangle.setFill(Color.rgb(red, green, blue, .99));
		carRectangle.setArcHeight(5);
		carRectangle.setArcWidth(5);
		window.setFill(Color.BLACK);
		//Add car lines for stop when car ahead or traffic light 
		Group car = new Group(carRectangle,window);
		car.getChildren().get(1).setLayoutY(-4);
		car.getChildren().add(centerAntennaLineCreator());
		car.getChildren().add(leftAntennaLineCreator());
		car.getChildren().add(rightAntennaLineCreator());
		//car.getChildren().add(backLineCreator());
		this.centerAntennaLine.setViewOrder(-1);
		
		return car;
	}
	
	
	
	private boolean checkCollision(Vehicle v1,Vehicle v2){
		boolean collision = false;
	
		Bounds v1Bounds = v1.carRectangle.localToScene(v1.carRectangle.getBoundsInParent());
		Bounds v2Bounds= v2.carRectangle.localToScene(v2.carRectangle.getBoundsInParent());

		if (v1Bounds.intersects(v2Bounds)&& (v1.isCollidible() && v2.isCollidible())&& (v1!=v2)&&v1.carPathTransition.getCurrentTime().toSeconds()>0.1&&v2.carPathTransition.getCurrentTime().toSeconds()>0.1) {
			
			collision = true;
			carRectangle.setStrokeType(StrokeType.INSIDE);
			carRectangle.setStrokeWidth(3.35);
			carRectangle.setStroke(Color.RED);
			
		}
		return collision;

	}
	public Line backLineCreator() {
		Line line = new Line(0,5,-10,5);
		line.setStroke(Color.RED);
		line.startXProperty().bind(this.carRectangle.xProperty().add(0));
		line.startYProperty().bind(carRectangle.yProperty().add(this.carRectangle.getHeight()/2));
		line.endXProperty().bind(line.startXProperty().add(-5)); 
        line.endYProperty().bind(line.startYProperty().add(0));
		line.setOpacity(100);
        
        return line;
	}
	public Line centerAntennaLineCreator() {
		Line line = new Line(0,5,-10,5);
		line.setStroke(Color.BLACK);
		line.startXProperty().bind(this.carRectangle.xProperty().add(20));
		line.startYProperty().bind(carRectangle.yProperty().add(this.carRectangle.getHeight()/2));
		line.endXProperty().bind(line.startXProperty().add(5)); 
        line.endYProperty().bind(line.startYProperty().add(0));
		this.centerAntennaLine = line;
		line.setOpacity(0.0);
        
        return line;
	}
	public Line leftAntennaLineCreator() {
		Line line = new Line(0,5,-10,5);
		line.setStroke(Color.BLACK);
		line.startXProperty().bind(this.carRectangle.xProperty().add(20));
		line.startYProperty().bind(carRectangle.yProperty().add(0));
		line.endXProperty().bind(line.startXProperty().add(5)); 
        line.endYProperty().bind(line.startYProperty().add(-1));
		this.leftAntennaLine = line;
		line.setStrokeWidth(0.5);
		line.setOpacity(0.0);
        
        return line;
	}
	public Line rightAntennaLineCreator() {
		Line line = new Line(0,5,-10,5);
		line.setStroke(Color.BLACK);
		line.startXProperty().bind(this.carRectangle.xProperty().add(20));
		line.startYProperty().bind(carRectangle.yProperty().add(this.carRectangle.getHeight()));
		line.endXProperty().bind(line.startXProperty().add(5)); 
        line.endYProperty().bind(line.startYProperty().add(1));
		this.rightAntennaLine = line;
		line.setStrokeWidth(2.0);
		line.setOpacity(0.0);
        
        return line;
	}
	
	public void checkAhead(ArrayList<TrafficLight> lights,ArrayList<Vehicle> cars){
		boolean ahead = false;
		if (wrecked) {
			return;
		}

		
        ArrayList<Line> lines = new ArrayList<>();
		lines.add(leftAntennaLine);
		lines.add(centerAntennaLine);
		lines.add(rightAntennaLine);
        
		for(Vehicle car:cars){
			if (checkCollision(this, car)) {
				this.handleDeath();
				return;
			}
			for(Line lineAhead:lines){
				if (checkCars(car, lineAhead)) {
					this.carPathTransition.pause();
					ahead = true;
				}
			}
			
		}
		//Check all Traffic lights 
		for(TrafficLight light:lights){
			for(Line lineAhead:lines){
				if (checkTrafficLight(light,lineAhead)) {
					
					this.carPathTransition.pause();
					ahead = true;
				} 
			}
		}
		//İf the front of the car is empty, continue 
		if (ahead==false) {
			this.carPathTransition.play();
			
		}

	}
	
	public boolean checkTrafficLight(TrafficLight light,Line lineAhead){
		boolean redLightAhead = false;
		Bounds lineBounds = lineAhead.localToScene(lineAhead.getBoundsInLocal());
		if (light.getMode() == TrafficLightMode.GREEN) {
			return false;
		}
		if (lineBounds.intersects(light.circle.getBoundsInParent())) {
			redLightAhead = true;
		}
		return redLightAhead;
	}
	
	private boolean checkCars(Vehicle vehicle,Line lineAhead){
		boolean carAhead = false;
		Bounds thisVehicleBounds = this.carRectangle.localToScene(vehicle.carRectangle.getBoundsInLocal());
		Bounds vehicleBounds = vehicle.carRectangle.localToScene(vehicle.carRectangle.getBoundsInLocal());
		Bounds lineBounds = lineAhead.localToScene(lineAhead.getBoundsInLocal());
		if (lineBounds.intersects(vehicleBounds)&&(vehicle!=this)&&(vehicle.isCollidible())){
			
			if ((false==vehicle.isMoving())) {
				carAhead = true;
			}
			else if (vehicle.isMoving() && (vehicle.carPath == this.carPath)) {
				carAhead=true;
			}
		}
		return carAhead;
	}
	
	//calculatePathLength for set Duration of path transistion
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
		
		carPathTransition.setDuration(Duration.millis(pathLength/velocity));
		
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
	
	public void handleWin(){
		
		this.carPathTransition.pause();
		
		
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
		
		this.carPathTransition.pause();
		
		
		this.wrecked = true;
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
		return (carPathTransition.getStatus() == PathTransition.Status.PAUSED)?false:true;
	}
	
}