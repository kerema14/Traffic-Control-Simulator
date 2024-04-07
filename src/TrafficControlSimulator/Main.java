
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
    public static File levelFile;
    public static LevelParser levelParser;
    public static Scene gameScene;
    @Override // Override the start method in the Application class
    
    public void start(Stage primaryStage) throws IOException {
        
        Button selectFileButton = new Button("Select level file");
        Button startGameButton = new Button("Start game");

        Text headerText = new Text("Traffic Control Simulator");
        Text fileNameText = new Text("No file selected yet");

        GridPane gridPane = new GridPane();
        VBox vBox = new VBox();
        
        vBox.getChildren().addAll(headerText,startGameButton,selectFileButton,fileNameText);
        vBox.setSpacing(10);
        
        gridPane.add(vBox, 400, 400);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        Scene scene = new Scene(gridPane, 800, 800);
        
        selectFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a level file");
            File initialDirectory;
            try{
                initialDirectory = new File("src/levels");
                fileChooser.setInitialDirectory(initialDirectory);
            } catch (Exception e){
            }
            levelFile = fileChooser.showOpenDialog(primaryStage);
            try{
                fileNameText.setText(levelFile.getName());
                levelParser = new LevelParser();
                levelParser.getLevelInfo(levelFile);
            } catch (Exception e){
                
            }
            

        });
        startGameButton.setOnMouseClicked(event -> {
            if (levelFile != null) {
                //switch to level scene by changing the root on the existing scene
            	GridPane levelPane = new GridPane();
            	this.setLevelPane(levelPane);
                scene.setRoot(levelPane);
            }

        });
        
        primaryStage.setTitle("Traffic Control Simulator"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();
    }
    
    private void setLevelPane(GridPane levelpane) {
    	double tileWidth = levelParser.getDimensonInfo()[0] / levelParser.getDimensonInfo()[2];
    	double tileHeight = levelParser.getDimensonInfo()[1] / levelParser.getDimensonInfo()[3];
		
    	for(int c = 0; c < levelParser.getDimensonInfo()[2]; c++) {
    		for(int r= 0; r < levelParser.getDimensonInfo()[3]; r++) {
    			levelpane.add(getEmptyTile(tileWidth, tileHeight), r, c);
    		}
    	}
    	
    	for(int[] roadData : levelParser.roadTileInfo) {
    		levelpane.add(getRoadTile(roadData[0], roadData[1], tileWidth, tileHeight), roadData[2], roadData[3]);
    	}
	}
    
    private ImageView getEmptyTile(double width, double height) {
    	
    	Image image = new Image("/images/EmptyTile.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        
        return imageView;
    }
    
    private ImageView getRoadTile(int type, int rotation, double width, double height) {
    	
    	Image image = new Image("/images/RoadTile-Type" + type + "-Rotation" + rotation + ".png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        
        return imageView;
    	
    }
    
    private ImageView getBuildingTile(int type, int colorindex, int rotation) {
    	
    	Image image = new Image("/images/EmptyTile.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        
        return imageView;
    	
    }

	public static void main(String[] args) {
        launch(args);
    }

}
