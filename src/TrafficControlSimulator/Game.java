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
    public void initData(LevelPane levelPane){
        animationTimelines = new ArrayList<Timeline>();
        levelParser = levelPane.getLevelParser();
        this.levelPane = levelPane;
        
        paths = levelParser.paths;

    }
    public void spawnCar(Random rand) {
        Timeline carSpawner = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Path carPath = paths.get(rand.nextInt(paths.size()));
                //double paneX = ((MoveTo)(carPath.getElements().get(0))).getX();
                Vehicle v = new Vehicle(0, 0);
                levelPane.getChildren().add(v);
                
                //v.startAnimation(); //burada animasyon başlatılacak
            }
        }));
        carSpawner.setCycleCount(Timeline.INDEFINITE);
        carSpawner.play();
        carTimeline = carSpawner;
        animationTimelines.add(carSpawner);
    }
    
    
}