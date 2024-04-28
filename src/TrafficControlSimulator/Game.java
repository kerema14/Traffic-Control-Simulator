package TrafficControlSimulator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.shape.*;

public class Game {
    
    
    private LevelParser levelParser;
    private LevelPane levelPane;
	public ArrayList<Building> buildings = new ArrayList<>();
	public ArrayList<RoadTile> roadTiles = new ArrayList<>();
	public ArrayList<TrafficLight> trafficLights = new ArrayList<>();
    public ArrayList<Path> paths = new ArrayList<>();
    public static ArrayList<Timeline> animationTimelines;
    static Timeline carTimeline; 
    public long oldNow;
    public double millisecondsPassed;
    public double totalMillisecondsPassed;
    public double spawnTimeStamp;
    public ArrayList<Vehicle> cars = new ArrayList<>();

    public void initData(LevelPane levelPane){
        
        animationTimelines = new ArrayList<Timeline>();
        levelParser = levelPane.getLevelParser();
        this.levelPane = levelPane;
        this.trafficLights = levelParser.trafficLights;
        paths = levelParser.paths;
        createTraffic();

    }
    public void createTraffic() {
        
        AnimationTimer timer = new AnimationTimer() { 
            @Override 
            public void handle(long now) { 
                update(now); 
            } 
        }; 
        timer.start(); 
    }
    public void update(long now){
        totalMillisecondsPassed = now/1000000.0;
        millisecondsPassed = (now-oldNow)/1000000.0;
        
        if (totalMillisecondsPassed-spawnTimeStamp > 600) {
            spawnCar();
            spawnTimeStamp = totalMillisecondsPassed;
            
        }
        carBehaviour();
        
        
        
        oldNow = now;
    }
    public void spawnCar(){
        Random random = new Random();
        
        Path carPath = paths.get(random.nextInt(paths.size()));
        
        
        Vehicle v = new Vehicle(carPath);
        
        
        cars.add(v);

        
        v.setTranslateX(v.paneX);
        v.setTranslateY(v.paneY);
        levelPane.getChildren().add(v);
        v.startPathTransition();
        
        
    }
    public void carBehaviour(){
        for(Vehicle car : cars){
            if (car.isCollidible() == false) {
                levelPane.getChildren().remove(car);
                continue;
            }
            car.checkAhead(trafficLights, cars);
        }
        

    }
    
    
    
}