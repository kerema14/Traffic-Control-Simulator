
package TrafficControlSimulator;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws IOException {
        
        Button selectFileButton = new Button("Select level file");
        selectFileButton.setOnMouseClicked(event -> {
            
        });
        Text headerText = new Text("Traffic Control Simulator");
        GridPane gridPane = new GridPane();
        gridPane.add(selectFileButton, 800/2, 800/2);
        gridPane.add(headerText, 800/2, 800/2 - 50);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        Scene scene = new Scene(gridPane, 800, 800);
        primaryStage.setTitle("Traffic Control Simulator"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
