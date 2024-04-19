
package TrafficControlSimulator;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	public static File levelFile;
	public static LevelParser levelParser;

	@Override // Override the start method in the Application class

	public void start(Stage primaryStage) throws IOException {

		Button selectFileButton = new Button("Select level file");
		Button startGameButton = new Button("Start game");

		Text headerText = new Text("Traffic Control Simulator");
		Text fileNameText = new Text("No file selected yet");

		GridPane gridPane = new GridPane();
		VBox vBox = new VBox();

		vBox.getChildren().addAll(headerText, startGameButton, selectFileButton, fileNameText);
		vBox.setSpacing(10);

		gridPane.add(vBox, 400, 400);
		gridPane.setHgap(1);
		gridPane.setVgap(1);
		
		Scene scene = new Scene(gridPane, 800, 800);

		selectFileButton.setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a level file");
			File initialDirectory;
			try {
				initialDirectory = new File("src/levels");
				fileChooser.setInitialDirectory(initialDirectory);
			} catch (Exception e) {
			}
			levelFile = fileChooser.showOpenDialog(primaryStage);
			try {
				fileNameText.setText(levelFile.getName());
				levelParser = new LevelParser();
				levelParser.getLevelInfo(levelFile);
			} catch (Exception e) {

			}

		});
		startGameButton.setOnMouseClicked(event -> {
			if (levelFile != null) {
				// switch to level scene by changing the root on the existing scene
				Pane levelPane = new Pane();
				// setting up the level with levelparser
				this.setLevelPane(levelPane);
				
				scene.setRoot(levelPane);
			}

		});

		primaryStage.setTitle("Traffic Control Simulator"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void setLevelPane(Pane levelpane) {
		double tileWidth = levelParser.getLvlWidth() / levelParser.getLvlColumnNum();
		double tileHeight = levelParser.getLvlHeight() / levelParser.getLvlRowNum();

		// adding empty tiles
		ImageView img;
		for (int c = 0; c < levelParser.getLvlColumnNum(); c++) {
			for (int r = 0; r < levelParser.getLvlRowNum(); r++) {
				img = getEmptyTile(tileWidth, tileHeight);
				img.setTranslateX(r * tileWidth);// set the absolute x coordinate
				img.setTranslateY(c * tileHeight);// set the absolute y coordinate
				levelpane.getChildren().add(img);
			}
		}

		// adding road tiles
		for (RoadTile roadTile : levelParser.roadTiles) {
			roadTile.setTranslateX(roadTile.getPosX());// set the absolute x coordinate
			roadTile.setTranslateY(roadTile.getPosY());// set the absolute y coordinate
			levelpane.getChildren().add(roadTile);
		}

		// adding buildings
		for (Building building : levelParser.buildings) {
			building.setTranslateX(building.getPosX());// set the absolute x coordinate
			building.setTranslateY(building.getPosY());// set the absolute y coordinate
			levelpane.getChildren().add(building);
		}
		
		//adding trafficlights
		Line line;
		Circle circle;
		for(TrafficLight tl : levelParser.trafficLights) {
			// adding lines
			line = tl.getLine();
			line.relocate(tl.getLinePosX(), tl.getLinePosY());
			levelpane.getChildren().add(line);
			
			//adding circles
			circle = tl.getCircle();
			circle.relocate(tl.getCirclePosX() - tl.getCircleRadius(), tl.getCirclePosY() - tl.getCircleRadius());
			levelpane.getChildren().add(circle);
			
		}
	}

	// method for getting empty tile image
	private ImageView getEmptyTile(double width, double height) {

		Image image = new Image("/images/EmptyTile.png");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

		return imageView;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
