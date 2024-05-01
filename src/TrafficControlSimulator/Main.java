
package TrafficControlSimulator;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	public static File levelFile;
	public static LevelParser levelParser = new LevelParser();

	@Override // Override the start method in the Application class

	public void start(Stage primaryStage) throws IOException {

		Button selectFileButton = new Button("Select level file");
		Button startGameButton = new Button("Start game");
		Button levelEditorButton = new Button("Level Editor");

		Text headerText = new Text("Traffic Control Simulator");
		Text fileNameText = new Text("No file selected yet");

		GridPane gridPane = new GridPane();
		VBox vBox = new VBox();

		vBox.getChildren().addAll(headerText, startGameButton, selectFileButton, fileNameText, levelEditorButton);
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
				levelParser.getLevelInfo(levelFile);
			} catch (Exception e) {

			}

		});

		startGameButton.setOnMouseClicked(event -> {
			if (levelFile != null) {
				// switch to level scene by changing the root on the existing scene
				LevelPane levelPane = new LevelPane();

				// setting up the level with levelparser
				scene.setRoot(levelPane);
				levelPane.setLevel(levelParser);
			} else {
				// setting the default level
				File defaultLevelFile = new File("src/levels/level1.txt");
				levelFile = defaultLevelFile;
				levelParser.getLevelInfo(levelFile);

				// switch to level scene by changing the root on the existing scene
				LevelPane levelPane = new LevelPane();

				// setting up the level with levelparser
				switchLevel(scene, levelParser);
			}

		});
		
		levelEditorButton.setOnMouseClicked(event -> {
			LevelPane emptyLevel = new LevelPane();
			emptyLevel.setEmptyLevel();
			EditorPane editorPane = new EditorPane();
			
			GridPane gp = new GridPane();
			gp.add(emptyLevel, 0, 0);
			gp.add(editorPane, 1, 0);
			
			Scene editorSccene = new Scene(gp, 1000, 800);
			
			primaryStage.setScene(editorSccene);
		});

		primaryStage.setTitle("Traffic Control Simulator"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void switchLevel(Scene scene, LevelParser lparser) {
		// setting up the level with levelparser
		LevelPane lpane = new LevelPane();
		scene.setRoot(lpane);
		lpane.setLevel(lparser);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
