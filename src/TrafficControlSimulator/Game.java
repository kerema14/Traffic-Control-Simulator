package TrafficControlSimulator;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Game {

	public LevelParser levelParser;
	public LevelPane levelPane;

	public ArrayList<Building> buildings = new ArrayList<>();
	public ArrayList<RoadTile> roadTiles = new ArrayList<>();
	public ArrayList<TrafficLight> trafficLights = new ArrayList<>();
	public ArrayList<Path> paths = new ArrayList<>();
	public ArrayList<Vehicle> cars = new ArrayList<>();

	public static ArrayList<Timeline> animationTimelines;
	static Timeline carTimeline;

	public int spawnedCarNum;
	public int reachedCarNum;
	public int accidentNum;
	public long oldNow;
	public double millisecondsPassed;
	public double totalMillisecondsPassed;
	public double spawnTimeStamp;

	public void initData(LevelPane levelPane) {
		spawnedCarNum = 0;
		reachedCarNum = 0;
		accidentNum = 0;

		animationTimelines = new ArrayList<Timeline>();

		levelParser = levelPane.getLevelParser();

		this.levelPane = levelPane;

		this.trafficLights = levelParser.trafficLights;
		paths = levelParser.paths;
		for (Path path : paths) {
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

	public void update(long now) {
		totalMillisecondsPassed = now / 1000000.0;
		millisecondsPassed = (now - oldNow) / 1000000.0;

		if (totalMillisecondsPassed - spawnTimeStamp > 600 && !levelPane.isGameOver && Math.random() < 0.3) {
			spawnCar();
			spawnTimeStamp = totalMillisecondsPassed;

		}
		carBehaviour();

		oldNow = now;
	}

	public void spawnCar() {
		Random random = new Random();

		Path carPath = paths.get(random.nextInt(paths.size()));

		Vehicle v = new Vehicle(carPath);

		cars.add(v);

		v.setTranslateX(v.paneX);
		v.setTranslateY(v.paneY);
		levelPane.getChildren().add(v);
		v.startPathTransition();

		spawnedCarNum++;
	}

	public void carBehaviour() {
		try {
			for (Vehicle car : cars) {
				if (car.isCollidible() == false) {
					if (car.wrecked) {
						levelParser.increasCarAccident();
						accidentNum++;
					} else {
						levelParser.increasReachCars();
						reachedCarNum++;
					}

					levelPane.getChildren().remove(car);
					cars.remove(car);

					continue;
				}
				car.checkAhead(trafficLights, cars);
				levelPane.updateScoreText();
				levelPane.updateAccidentText();
			}
		} catch (Exception e) {
		}
		if (levelParser.getCarAccident() / 2 >= levelParser.getMaxCarAccident()
				|| levelParser.getReachCars() >= levelParser.getCarNumToWin()) {
			// Pause to whole game
			for (Vehicle car : cars) {
				car.pausePathTransition();
			}

			if (levelParser.getCarAccident() / 2 >= levelParser.getMaxCarAccident()) {
				levelPane.loseWinText.setText("YOU LOSE 😥");
				levelPane.loseWinText.setVisible(true);
				
				levelPane.isGameOver = true;
			} else {
				if(levelParser.levelIndex != 5) {// checking if last level
					levelPane.loseWinText.setText("YOU WIN 😄");
					levelPane.loseWinText.setVisible(true);
					
					levelPane.endLevelBtn.setText("NEXT LEVEL");
					levelPane.endLevelBtn.setVisible(true);
				}else {
					levelPane.loseWinText.setText("YOU WON IT ALL😄");
					levelPane.loseWinText.setVisible(true);
				}
				
				levelPane.isGameOver = true;
			}

		}

	}

}