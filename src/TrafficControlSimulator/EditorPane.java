package TrafficControlSimulator;

import  javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EditorPane extends Pane{
	int rotation = 0;
	int colorIndex = 0;
	VBox vbox2;
	public EditorPane() {
		this.setStyle("-fx-background-color: #bbdeff;");
		this.setMinSize(150, 800);
		createButtons();
		createBuildings(rotation,colorIndex);
		
		
		
	}
	public void createButtons() {
		//colorButtons
		Button yellowButton = new Button();
		yellowButton.setMinSize(25, 20);
		yellowButton.setStyle("-fx-background-color: #fbc894; -fx-border-radius: 0");
		yellowButton.setOnAction(event->{
			colorIndex = 0;
			createBuildings(rotation,colorIndex);
		});
		Button greenButton = new Button();
		greenButton.setMinSize(25, 20);
		greenButton.setStyle("-fx-background-color: #a7ddbd;-fx-border-radius: 0");
		greenButton.setOnAction(event->{
			colorIndex = 1;
			createBuildings(rotation,colorIndex);
		});
		Button magentaButton = new Button();
		magentaButton.setMinSize(25, 20);
		magentaButton.setStyle("-fx-background-color: #b2aee5;-fx-border-radius: 0");
		magentaButton.setOnAction(event->{
			colorIndex = 2;
			createBuildings(rotation,colorIndex);
		});
		Button redButton = new Button();
		redButton.setMinSize(25, 20);
		redButton.setStyle("-fx-background-color: #d17b87;-fx-border-radius: 0");
		redButton.setOnAction(event->{
			colorIndex = 3;
			createBuildings(rotation,colorIndex);
		});
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(yellowButton,greenButton,magentaButton,redButton);
		hbox.setLayoutX(20);
		hbox.setLayoutY(20);
		this.getChildren().add(hbox);
		
		VBox vbox = new VBox();
		
		Button rotateButton = new Button("Rotate");
		rotateButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff;");
		rotateButton.setOnAction(e->{
			rotation += 90;
			createBuildings(rotation,colorIndex);
		});
		
		Button saveButton = new Button("Save Map");
		saveButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff;");
		
		Button show_hideButton = new Button("Show/Hide Markers");
		show_hideButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff; -fx-text-overrun: ellipsis;");
		
		
		
		vbox.getChildren().addAll(rotateButton,saveButton,show_hideButton);
		vbox.setSpacing(20);
		vbox.setLayoutX(20);
		vbox.setLayoutY(80);
		this.getChildren().add(vbox);
	}
	public void createBuildings(int rotation,int colorIndex) {
		this.getChildren().remove(vbox2);
		vbox2 = new VBox();
		
		vbox2.getChildren().clear();
		vbox2.setLayoutX(20);
		vbox2.setSpacing(20);
		vbox2.setLayoutY(150);
		
		//TrafficLight
		TrafficLight tl = new TrafficLight(vbox2.getLayoutX()-10,vbox2.getLayoutY()-60,vbox2.getLayoutX()-10,vbox2.getLayoutY()-20);
		tl.rotate = rotation;
		final Circle circle;
		circle = tl.getCircle();
		circle.relocate(tl.getCirclePosX() - tl.getCircleRadius(), tl.getCirclePosY() - tl.getCircleRadius());
		circle.setViewOrder(-1);
		Pane tlPane = new Pane();
		tlPane.getChildren().clear();
		tlPane.getChildren().add(tl.getLine());
		tlPane.getChildren().add(circle);
		vbox2.getChildren().add(tlPane);
		
		//Roads
		RoadTile squareRoad = new RoadTile(0,rotation,0,0,30);
		vbox2.getChildren().add(squareRoad);
		
		RoadTile curvedRoad = new RoadTile(1,rotation,0,0,30);
		vbox2.getChildren().add(curvedRoad);
		
		RoadTile fullRoad = new RoadTile(2,rotation,0,0,30);
		vbox2.getChildren().add(fullRoad);
		
		RoadTile halfRoad = new RoadTile(3,rotation,0,0,30);
		vbox2.getChildren().add(halfRoad);
		
		//Buildings 
		Building bigSquareBuilding = new Building(0,rotation,colorIndex,2,3,20);
		vbox2.getChildren().add(bigSquareBuilding);
		
		Building circularBuilding = new Building(1,rotation,colorIndex,2,3,20);
		vbox2.getChildren().add(circularBuilding);
		
		Building smallSquareBuilding = new Building(2,rotation,colorIndex,2,3,20);
		vbox2.getChildren().add(smallSquareBuilding);
		this.getChildren().add(vbox2);
		
		
	}
}
