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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        for(Path path:paths){
            path.setOpacity(0.0);
            levelPane.getChildren().add(path);
        }
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
        
        if (totalMillisecondsPassed-spawnTimeStamp > 600&&!levelPane.isGameOver) {
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
    	try {
        for(Vehicle car : cars){
            if (car.isCollidible() == false) {
            	if(car.wrecked)
            		levelParser.increasCarAccident();
            	else 
            		levelParser.increasReachCars();
                levelPane.getChildren().remove(car);
                cars.remove(car);
                continue;
            }
            car.checkAhead(trafficLights, cars);
            levelPane.updateScoreText();
            levelPane.updateAccidentText();
        }
    	}catch(Exception e){}
        if(levelParser.getCarAccident()/2>=levelParser.getMaxCarAccident()||levelParser.getReachCars()>=levelParser.getCarNumToWin()) {
        	//Pause to whole game 
        	for(Vehicle car :cars) {
        		car.pausePathTransition();
        	}
        	//Lose / Win Text
        	Text loseWinText = new Text();
    		loseWinText.setLayoutX(300);
    		loseWinText.setLayoutY(100);
    		loseWinText.setFont(Font.font("Arial",45));
        	if(levelParser.getCarAccident()/2>=levelParser.getMaxCarAccident()) {
        		loseWinText.setText("You Lose");
        		levelPane.isGameOver = true;
        	}else {
        		loseWinText.setText("You Win");
        		levelPane.isGameOver = true;
        	}
        	levelPane.getChildren().add(loseWinText);
        	
        }

    }
    
    
    
}