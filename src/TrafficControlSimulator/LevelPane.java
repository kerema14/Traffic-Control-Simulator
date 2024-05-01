package TrafficControlSimulator;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelPane extends Pane{
	
	private LevelParser levelParser;
	
	public boolean isGameOver = false;
	public Text accidentText;
	public Text scoreText;
	private int levelIndex;
	
	Text loseWinText = new Text();
	Button endLevelBtn = new Button();
	
	public LevelPane() {
		
	}
	
	public void setLevel(LevelParser levelParser) {
		this.levelIndex = levelParser.levelIndex;
		
		this.setStyle("-fx-background-color: #9cbcdc;");
		this.levelParser = levelParser;
		double tileWidth = levelParser.getLvlWidth() / levelParser.getLvlColumnNum();
		double tileHeight = levelParser.getLvlHeight() / levelParser.getLvlRowNum();

		// adding empty tiles
		Line line;
		for (int c = 0; c < levelParser.getLvlColumnNum(); c++) {
			for (int r = 0; r < levelParser.getLvlRowNum(); r++) {
				//vertical lines
				line = new Line();
				line.setStartX(c * tileWidth);
				line.setStartY(0);
				line.setEndX(c * tileWidth);
				line.setEndY(levelParser.getLvlHeight());
				line.setStyle("-fx-stroke: #8ca4bf;");
				this.getChildren().add(line);
				//horizontal lines
				line = new Line();
				line.setStartX(0);
				line.setStartY(r * tileHeight);
				line.setEndX(levelParser.getLvlWidth());
				line.setEndY(r * tileHeight);
				line.setStyle("-fx-stroke: #8ca4bf;");
				this.getChildren().add(line);
			}
		}

		// adding road tiles
		for (RoadTile roadTile : levelParser.roadTiles) {
			roadTile.setTranslateX(roadTile.getPanePosX());// set the absolute x coordinate
			roadTile.setTranslateY(roadTile.getPanePosY());// set the absolute y coordinate
			this.getChildren().add(roadTile);
		}

		// adding buildings
		for (Building building : levelParser.buildings) {
			building.setTranslateX(building.getPanePosX());// set the absolute x coordinate
			building.setTranslateY(building.getPanePosY());// set the absolute y coordinate
			this.getChildren().add(building);
		}
		
		//adding trafficlights
		
		for(TrafficLight tl : levelParser.trafficLights) {
			// adding lines
			line = tl.getLine();
			line.relocate(tl.getLinePosX(), tl.getLinePosY());
			this.getChildren().add(line);
			final Circle circle;
			//adding circles
			circle = tl.getCircle();
			circle.relocate(tl.getCirclePosX() - tl.getCircleRadius(), tl.getCirclePosY() - tl.getCircleRadius());
			circle.setViewOrder(-1);
			circle.setOnMouseClicked(event -> {
				tl.toggleLight();
				circle.setFill(tl.getColor());
				
			});
			this.getChildren().add(circle);
		}
		Game game = new Game();
		
		accidentText = new Text("Car Accident: "+levelParser.getCarAccident()/2+"/"+levelParser.getMaxCarAccident());
		accidentText.setLayoutX(20);
		accidentText.setLayoutY(20);
		this.getChildren().add(accidentText);
		
		scoreText = new Text("Score: "+levelParser.getReachCars()+"/"+levelParser.getCarNumToWin());
		scoreText.setLayoutX(20);
		scoreText.setLayoutY(40);
		this.getChildren().add(scoreText);
		
		// Lose / Win Text
		loseWinText.setLayoutX(300);
		loseWinText.setLayoutY(100);
		loseWinText.setFont(Font.font("Arial", 45));
		loseWinText.setVisible(false);
		
		//end level button
		endLevelBtn.setLayoutX(300);
		endLevelBtn.setLayoutY(100);
		endLevelBtn.setFont(Font.font("Arial", 45));
		endLevelBtn.setOnMouseClicked(event -> {
			int nextLevelIndex = this.levelIndex + 1;
			
			if(nextLevelIndex < 5) {
				this.levelParser = new LevelParser();
				this.levelParser.getLevelInfo(new File("src/levels/level" + nextLevelIndex + ".txt"));
			
				clearLevel();
				Main.switchLevel(this.getScene(), this.levelParser);
			}
		});
		endLevelBtn.setVisible(false);
		
		this.getChildren().addAll(loseWinText, endLevelBtn);
		
		game.initData(this);
	}

	public void setEmptyLevel() {
		this.setStyle("-fx-background-color: #9cbcdc;");
		
		Line line;
		for (int c = 0; c < 15; c++) {
			for (int r = 0; r < 15; r++) {
				//vertical lines
				line = new Line();
				double tileWidth = 800 / 15.0, tileHeight = 800 / 15.0;
				line.setStartX(c * tileWidth );
				line.setStartY(0);
				line.setEndX(c * tileWidth);
				line.setEndY(800);
				line.setStyle("-fx-stroke: #8ca4bf;");
				this.getChildren().add(line);
				//horizontal lines
				line = new Line();
				line.setStartX(0);
				line.setStartY(r * tileHeight);
				line.setEndX(800);
				line.setEndY(r * tileHeight);
				line.setStyle("-fx-stroke: #8ca4bf;");
				this.getChildren().add(line);
			}
		}
	}
	
	private void clearLevel() {
		this.getChildren().clear();
	}

	public LevelParser getLevelParser() {
		return levelParser;
	}
	public void updateAccidentText() {
		accidentText.setText("Car Accident: "+levelParser.getCarAccident()/2+"/"+levelParser.getMaxCarAccident());
	}
	public void updateScoreText() {
		scoreText.setText("Score: "+levelParser.getReachCars()+"/"+levelParser.getCarNumToWin());
	}
}