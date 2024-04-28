package TrafficControlSimulator;

import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
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
	
	public double pathLength;
	Rectangle carRectangle;
	public double startingRotate;
	
	public Vehicle(Path path) {
		this.paneX = ((MoveTo)(path.getElements().get(0))).getX();;
		this.paneY = ((MoveTo)(path.getElements().get(0))).getY();;
		carPath = path;
		
		
		this.getChildren().add(createCar());
		startingRotate = this.getRotate();
		
	}
	private Rectangle getCarRectangle(){
		return this.carRectangle;
	}
	
	

	private Group createCar() {
		Random rand = new Random();
		carRectangle = new Rectangle(800.0 / 40.0, 800.0 / 80.0);
		Rectangle window = new Rectangle(800.0 / 80.0, 800.0 / 160.0,5,8);
		int red = rand.nextInt(225);
		int green = rand.nextInt(225);
		int blue = rand.nextInt(225);
		carRectangle.setFill(Color.rgb(red, green, blue, .99));
		carRectangle.setArcHeight(5);
		carRectangle.setArcWidth(5);
		window.setFill(Color.BLACK);
		Group car = new Group(carRectangle,window);
		car.getChildren().get(1).setLayoutY(-4);
		/* 
	    this.setMaxHeight(200.0 / 80.0);
		this.setMaxWidth(200.0 / 40.0);
		this.setWidth(200.0 / 40.0);
		this.setHeight(200.0 / 80.0);*/
		
		
		
        
		
		return car;
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
	public Line carController() {
		Line line = new Line(0,5,-10,5);
		line.setStroke(Color.BLACK);
		line.startXProperty().bind(this.carRectangle.xProperty().add(0));
		line.startYProperty().bind(carRectangle.yProperty().add(this.carRectangle.getHeight()/2));
		line.endXProperty().bind(line.startXProperty().add(-10)); 
        line.endYProperty().bind(line.startYProperty().add(0));
        
		return line;
	}
	public void checkAhead(ArrayList<TrafficLight> lights,ArrayList<Vehicle> cars){
		boolean ahead = false;
		
		// Create a Line object ahead of the rectangle with the same rotation
		
		double aheadX = this.getTranslateX() + 10; //* Math.cos(Math.toRadians(this.getRotate()));
        double aheadY = this.getTranslateY(); //+ 10 * Math.sin(Math.toRadians(this.getRotate()));
		
        Line lineAhead = new Line(this.getTranslateX(), this.getTranslateY(), aheadX , aheadY );
        lineAhead.setVisible(true);
		((LevelPane)(this.getParent())).getChildren().add(lineAhead); 
		
		try{
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
					if (totalMillisecondsPassed-timeStamp > 2) {
						
						lineAhead.setVisible(false);
						this.stop();
					}
				} 
			}; 
			timer.start();
		}catch(Exception e){}

		for(Vehicle car:cars){
			if (checkCars(car, lineAhead)) {
				System.err.println("I see a car ahead");
				pausePathTransition();
				ahead = true;
			}
		}

		for(TrafficLight light:lights){
			if (checkTrafficLight(light,lineAhead)) {
				
				pausePathTransition();
				ahead = true;
			} 
		}
		//((LevelPane)(this.getParent())).getChildren().remove(lineAhead); 

		if (ahead==false) {
			continuePathTransition();
			
		}

	}
	public boolean checkTrafficLight(TrafficLight light,Line lineAhead){
		boolean redLightAhead = false;
		if (light.getMode() == TrafficLightMode.GREEN) {
			return false;
		}
		if (lineAhead.getBoundsInParent().intersects(light.circle.getBoundsInParent())) {
			redLightAhead = true;
		}
		return redLightAhead;
	}
	private boolean checkCars(Vehicle pane,Line lineAhead){
		boolean carAhead = false;
		if (lineAhead.getBoundsInParent().intersects(pane.getBoundsInParent())&&(pane!=this)&&(false==pane.isMoving())&&(pane.isCollidible())){
			carAhead = true;
		}
		return carAhead;
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
		
		double velocity = 0.2;
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
	
	
	public void continuePathTransition(){
		
		carPathTransition.play();
		
	}
	public void pausePathTransition(){
		
		carPathTransition.pause();
	}/* 
	public double getPathLength(){
		double pathLength;

	}*/
	public void handleWin(){
		
		this.pausePathTransition();
		
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
		carPathTransition.stop();
		
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
		return (carPathTransition.getStatus() == PathTransition.Status.PAUSED)?false:true;
	}
	
}