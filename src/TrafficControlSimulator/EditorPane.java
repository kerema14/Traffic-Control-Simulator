package TrafficControlSimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

public class EditorPane extends Pane {
	private int rotation = 0;
	private int colorIndex = 0;
	private boolean isDrawingPath = false;
	private String currentPathColor;
	private ArrayList<Circle> pathPoints = new ArrayList<>();
	private ArrayList<Circle> startPoints = new ArrayList<>();
	private ArrayList<Circle> endPoints = new ArrayList<>();
	private ArrayList<Line> markerLines = new ArrayList<>();
	private ArrayList<Path> createdPaths = new ArrayList<>();
	private StringBuilder levelToString = new StringBuilder();
	private String adderString;
	private LevelPane levelPane;
	private VBox vbox2;

	public EditorPane(LevelPane lp) {
		this.setStyle("-fx-background-color: #bbdeff;");
		this.setMaxSize(150, 800);
		this.setMinSize(150, 800);
		this.levelPane = lp;
		this.levelToString.append("Metadata 800.0 800.0 15 15 3 50 5");
		createButtons();
		createNodeButtons(rotation, colorIndex);
	}

	private void createButtons() {
		// colorButtons
		Button yellowButton = new Button();
		yellowButton.setMinSize(25, 20);
		yellowButton.setStyle("-fx-background-color: #fbc894; -fx-border-radius: 0");
		yellowButton.setOnAction(event -> {
			colorIndex = 0;
			createNodeButtons(rotation, colorIndex);
		});
		Button greenButton = new Button();
		greenButton.setMinSize(25, 20);
		greenButton.setStyle("-fx-background-color: #a7ddbd;-fx-border-radius: 0");
		greenButton.setOnAction(event -> {
			colorIndex = 1;
			createNodeButtons(rotation, colorIndex);
		});
		Button magentaButton = new Button();
		magentaButton.setMinSize(25, 20);
		magentaButton.setStyle("-fx-background-color: #b2aee5;-fx-border-radius: 0");
		magentaButton.setOnAction(event -> {
			colorIndex = 2;
			createNodeButtons(rotation, colorIndex);
		});
		Button redButton = new Button();
		redButton.setMinSize(25, 20);
		redButton.setStyle("-fx-background-color: #d17b87;-fx-border-radius: 0");
		redButton.setOnAction(event -> {
			colorIndex = 3;
			createNodeButtons(rotation, colorIndex);
		});

		HBox hbox = new HBox();
		hbox.getChildren().addAll(yellowButton, greenButton, magentaButton, redButton);
		hbox.setLayoutX(20);
		hbox.setLayoutY(20);
		this.getChildren().add(hbox);

		VBox vbox = new VBox();

		Button rotateButton = new Button("Rotate");
		rotateButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff;");
		rotateButton.setOnAction(e -> {
			if (rotation == 270) {
				rotation = 0;
			} else {
				rotation += 90;
			}
			createNodeButtons(rotation, colorIndex);
		});

		Button saveButton = new Button("Save Map");
		saveButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff;");
		saveButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Level to txt");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
			File initialDirectory;
			try {
				initialDirectory = new File("src/levels");
				fileChooser.setInitialDirectory(initialDirectory);
			} catch (Exception exception) {
			}
			File selectedFile = fileChooser.showSaveDialog(this.getScene().getWindow());
			try {
				FileWriter fileWriter = new FileWriter(selectedFile);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(levelToString.toString());
				bufferedWriter.close();
			} catch (Exception ex) {
			}

		});

		Button show_hideButton = new Button("Show/Hide Markers");
		show_hideButton.setStyle("-fx-background-color: #839bb2; -fx-text-fill: #ffffff; -fx-text-overrun: ellipsis;");
		show_hideButton.setOnMouseClicked(event -> {

			changeVisibilityMarkers();
		});

		vbox.getChildren().addAll(rotateButton, saveButton, show_hideButton);
		vbox.setSpacing(20);
		vbox.setLayoutX(20);
		vbox.setLayoutY(80);
		this.getChildren().add(vbox);
	}

	private void changeVisibilityMarkers() {
		for (Circle c : startPoints) {
			c.setVisible(!c.isVisible());
		}
		for (Circle c : endPoints) {
			c.setVisible(!c.isVisible());
		}
		for (Circle c : pathPoints) {
			c.setVisible(!c.isVisible());
		}
		
		for(Line l : markerLines) {
			l.setVisible(!l.isVisible());
		}
	}

	private void createNodeButtons(int rotation, int colorIndex) {
		this.getChildren().remove(vbox2);
		vbox2 = new VBox();

		vbox2.getChildren().clear();
		vbox2.setLayoutX(20);
		vbox2.setSpacing(20);
		vbox2.setLayoutY(150);

		// TrafficLight
		createTrafficLight(vbox2);

		// Roads
		createRoads(vbox2);

		// Buildings
		createBuildings(vbox2);
	}

	private void createTrafficLight(VBox vbox2) {
		TrafficLight tl = new TrafficLight(vbox2.getLayoutX() - 10, vbox2.getLayoutY() - 50, vbox2.getLayoutX() - 10,
				vbox2.getLayoutY() - 20);
		tl.rotate = rotation;
		Circle circle;
		circle = tl.getCircle();
		circle.relocate(tl.getCirclePosX() - tl.getCircleRadius(), tl.getCirclePosY() - tl.getCircleRadius());
		circle.setViewOrder(-1);
		Pane tlPane = new Pane();
		tlPane.getChildren().clear();
		tlPane.getChildren().add(tl.getLine());
		tlPane.getChildren().add(circle);
		tlPane.setOnMouseReleased(e -> {
			TrafficLight trafficlight = new TrafficLight(e.getSceneX(), e.getSceneY(), e.getSceneX(),
					e.getSceneY() + 30);
			trafficlight.rotate = rotation;
			Circle circle1;
			circle1 = tl.getCircle();
			circle1.relocate(trafficlight.getCirclePosX() - trafficlight.getCircleRadius(),
					trafficlight.getCirclePosY() - trafficlight.getCircleRadius());
			circle1.setViewOrder(-1);
			Line line = trafficlight.getLine();
			line.setRotate(trafficlight.rotate);
			adderString = "\nTrafficLight " + ((int) (line.getBoundsInParent().getMinX() * 100) / 100.0) + " "
					+ ((int) (line.getBoundsInParent().getMinY() * 100) / 100.0) + " "
					+ ((int) (line.getBoundsInParent().getMaxX() * 100) / 100.0) + " "
					+ ((int) (line.getBoundsInParent().getMaxY() * 100) / 100.0);
			levelToString.append(adderString);
			Pane tlPane1 = new Pane();
			tlPane1.getChildren().clear();
			tlPane1.setViewOrder(-2);
			tlPane1.getChildren().add(line);
			tlPane1.getChildren().add(circle1);
			levelPane.getChildren().add(tlPane1);
		});
		vbox2.getChildren().add(tlPane);
	}

	private void createRoads(VBox vbox2) {

		RoadTile squareRoad = new RoadTile(0, rotation, 0, 0, 30);
		squareRoad.setOnMouseReleased(e -> {
			RoadTile road = new RoadTile(0, rotation, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nRoadTile 0 " + rotation + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			road.setLayoutX(x_number);
			road.setLayoutY(y_number);
			road.setViewOrder(-2);
			roadTilesPathCircle(road);
			levelPane.getChildren().add(road);
		});
		vbox2.getChildren().add(squareRoad);

		RoadTile curvedRoad = new RoadTile(1, rotation, 0, 0, 30);
		curvedRoad.setOnMouseReleased(e -> {
			RoadTile road = new RoadTile(1, rotation, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nRoadTile 1 " + rotation + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			road.setLayoutX(x_number);
			road.setLayoutY(y_number);
			road.setViewOrder(-2);
			roadTilesPathCircle(road);
			levelPane.getChildren().add(road);
		});
		vbox2.getChildren().add(curvedRoad);

		RoadTile fullRoad = new RoadTile(2, rotation, 0, 0, 30);
		fullRoad.setOnMouseReleased(e -> {
			RoadTile road = new RoadTile(2, rotation, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.7 * mouseY;
			adderString = "\nRoadTile 2 " + rotation + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			road.setLayoutX(x_number);
			road.setLayoutY(y_number);
			road.setViewOrder(-2);
			roadTilesPathCircle(road);
			levelPane.getChildren().add(road);
		});
		vbox2.getChildren().add(fullRoad);

		RoadTile halfRoad = new RoadTile(3, rotation, 0, 0, 30);
		halfRoad.setOnMouseReleased(e -> {
			RoadTile road = new RoadTile(3, rotation, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nRoadTile 3 " + rotation + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			road.setLayoutX(x_number);
			road.setLayoutY(y_number);
			road.setViewOrder(-2);
			roadTilesPathCircle(road);
			levelPane.getChildren().add(road);
		});
		vbox2.getChildren().add(halfRoad);
	}

	private void createBuildings(VBox vbox2) {

		Building bigSquareBuilding = new Building(0, rotation, colorIndex, 2, 3, 20);
		bigSquareBuilding.setOnMouseReleased(e -> {
			Building building = new Building(0, rotation, colorIndex, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nBuilding 0 " + rotation + " " + colorIndex + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			building.setLayoutX(x_number);
			building.setLayoutY(y_number);
			building.setViewOrder(-2);
			buildingPathCircle(building);
			levelPane.getChildren().add(building);
		});
		vbox2.getChildren().add(bigSquareBuilding);

		Building circularBuilding = new Building(1, rotation, colorIndex, 2, 3, 20);
		circularBuilding.setOnMouseReleased(e -> {
			Building building = new Building(1, rotation, colorIndex, 2, 3, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nBuilding 1 " + rotation + " " + colorIndex + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			building.setLayoutX(x_number);
			building.setLayoutY(y_number);
			building.setViewOrder(-2);
			buildingPathCircle(building);
			levelPane.getChildren().add(building);
		});
		vbox2.getChildren().add(circularBuilding);

		Building smallSquareBuilding = new Building(2, rotation, colorIndex, 1, 1, 20);
		smallSquareBuilding.setOnMouseReleased(e -> {
			Building building = new Building(2, rotation, colorIndex, 1, 1, 52.8);
			int mouseX = (int) (e.getSceneX() / 52.8);
			int mouseY = (int) (e.getSceneY() / 52.8);
			double x_number = 53.4 * mouseX;
			double y_number = 53.8 * mouseY;
			adderString = "\nBuilding 2 " + rotation + " " + colorIndex + " " + mouseX + " " + mouseY;
			levelToString.append(adderString);
			building.setLayoutX(x_number);
			building.setLayoutY(y_number);
			building.setViewOrder(-2);
			buildingPathCircle(building);
			levelPane.getChildren().add(building);
		});
		vbox2.getChildren().add(smallSquareBuilding);
		this.getChildren().add(vbox2);
	}

	private void buildingPathCircle(Building building) {
		Circle startPoint = new Circle(8);
		startPoint.setStyle("-fx-fill: " + building.getColor());
		startPoint.setViewOrder(-3);
		startPoint.setOnMouseClicked(event -> {
			isDrawingPath = true;
			currentPathColor = building.getColor();
			
			Path path = new Path();

			path.getElements()
					.add(new MoveTo(startPoint.localToScene(startPoint.getCenterX(), startPoint.getCenterY()).getX(),
							startPoint.localToScene(startPoint.getCenterX(), startPoint.getCenterY()).getY()));
			createdPaths.add(path);
		});

		Circle endPoint = new Circle(8);
		endPoint.setFill(Color.web("#708090"));
		endPoint.setViewOrder(-3);
		endPoint.setOnMouseClicked(event -> {
			pathDrawer(endPoint);
			isDrawingPath = false;
		});

		if (building.getType() == 2) {
			startPoint.setStroke(Color.BLACK);
			startPoint.setStrokeWidth(1);
			
			startPoint.setTranslateX(building.getBoundsInLocal().getWidth() / 2.0);
			startPoint.setTranslateY(building.getBoundsInLocal().getHeight() / 2.0);

			pathPoints.add(startPoint);

			building.getChildren().add(startPoint);
		} else {
			startPoint.setCenterX(building.getBoundsInLocal().getWidth() / 4.0);
			startPoint.setCenterY(building.getBoundsInLocal().getHeight() * 3.0 / 4.0);

			endPoint.setCenterX(building.getBoundsInLocal().getWidth() * 3.0 / 4.0);
			endPoint.setCenterY(building.getBoundsInLocal().getHeight() * 3.0 / 4.0);

			startPoint.getTransforms().add(new Rotate(building.getRotation(), building.getBoundsInLocal().getCenterX(),
					building.getBoundsInLocal().getCenterY()));

			endPoint.getTransforms().add(new Rotate(building.getRotation(), building.getBoundsInLocal().getCenterX(),
					building.getBoundsInLocal().getCenterY()));

			startPoints.add(startPoint);
			endPoints.add(endPoint);

			building.getChildren().addAll(startPoint, endPoint);
		}

	}

	private void roadTilesPathCircle(RoadTile roadTile) {
		Circle c1 = new Circle(8);
		Circle c2 = new Circle(8);
		Circle c3 = new Circle(8);
		Circle c4 = new Circle(8);
		Circle c5 = new Circle(8);
		Circle c6 = new Circle(8);
		Circle c7 = new Circle(8);

		c1.setFill(Color.web("#708090"));
		c1.setViewOrder(-3);
		c2.setFill(Color.web("#708090"));
		c2.setViewOrder(-3);
		c3.setFill(Color.web("#708090"));
		c3.setViewOrder(-3);
		c4.setFill(Color.web("#708090"));
		c4.setViewOrder(-3);
		c5.setFill(Color.web("#708090"));
		c5.setViewOrder(-3);
		c6.setFill(Color.web("#708090"));
		c6.setViewOrder(-3);
		c7.setFill(Color.web("#708090"));
		c7.setViewOrder(-3);
		
		c1.setOnMouseClicked(event -> {
			pathDrawer(c1);
		});
		c2.setOnMouseClicked(event -> {
			pathDrawer(c2);
		});
		c3.setOnMouseClicked(event -> {
			pathDrawer(c3);
		});
		c4.setOnMouseClicked(event -> {
			pathDrawer(c4);
		});
		c5.setOnMouseClicked(event -> {
			pathDrawer(c5);
		});
		c6.setOnMouseClicked(event -> {
			pathDrawer(c6);
		});
		c7.setOnMouseClicked(event -> {
			pathDrawer(c7);
		});
		
		

		switch (roadTile.getType()) {
		case (0):
			c1.setCenterX(roadTile.getBoundsInParent().getWidth() / 2.0);
			c1.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c2.setCenterX(roadTile.getBoundsInParent().getWidth() / 2.0);
			c2.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c1.getTransforms().add(new Rotate(rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c2.getTransforms().add(new Rotate(rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));

			pathPoints.add(c1);
			pathPoints.add(c2);

			roadTile.getChildren().addAll(c1, c2);
			break;

		case (1):
			c1.setCenterX(roadTile.getBoundsInParent().getWidth() / 4.0);
			c1.setCenterY(roadTile.getBoundsInParent().getHeight());

			c2.setCenterX(roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0);
			c2.setCenterY(roadTile.getBoundsInParent().getHeight());

			c3.setCenterX(0);
			c3.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c4.setCenterX(0);
			c4.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c5.setCenterX((roadTile.getBoundsInParent().getWidth() / 4.0) * Math.cos(Math.toRadians(45)));
			c5.setCenterY(roadTile.getBoundsInParent().getHeight() * (1 - Math.sin(Math.toRadians(45)) / 4.0));

			c6.setCenterX((roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0) * Math.cos(Math.toRadians(60)));
			c6.setCenterY(roadTile.getBoundsInParent().getHeight() * (1 - Math.sin(Math.toRadians(60)) * 3.0 / 4.0));

			c7.setCenterX((roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0) * Math.cos(Math.toRadians(30)));
			c7.setCenterY(roadTile.getBoundsInParent().getHeight() * (1 - Math.sin(Math.toRadians(30)) * 3.0 / 4.0));

			c1.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c2.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c3.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c4.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c5.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c6.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c7.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));

			pathPoints.add(c1);
			pathPoints.add(c2);
			pathPoints.add(c3);
			pathPoints.add(c4);
			pathPoints.add(c5);
			pathPoints.add(c6);
			pathPoints.add(c7);

			roadTile.getChildren().addAll(c1, c2, c3, c4, c5, c6, c7);
			break;

		case (2):
			c1.setCenterX(roadTile.getBoundsInParent().getWidth() / 4.0);
			c1.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c2.setCenterX(roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0);
			c2.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c3.setCenterX(roadTile.getBoundsInParent().getWidth() / 4.0);
			c3.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c4.setCenterX(roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0);
			c4.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c1.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c2.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c3.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c4.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));

			pathPoints.add(c1);
			pathPoints.add(c2);
			pathPoints.add(c3);
			pathPoints.add(c4);

			roadTile.getChildren().addAll(c1, c2, c3, c4);
			break;

		case (3):
			c1.setCenterX(roadTile.getBoundsInParent().getWidth() / 4.0);
			c1.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c2.setCenterX(roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0);
			c2.setCenterY(roadTile.getBoundsInParent().getHeight() / 4.0);

			c3.setCenterX(roadTile.getBoundsInParent().getWidth() / 4.0);
			c3.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c4.setCenterX(roadTile.getBoundsInParent().getWidth() * 3.0 / 4.0);
			c4.setCenterY(roadTile.getBoundsInParent().getHeight() * 3.0 / 4.0);

			c1.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c2.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c3.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));
			c4.getTransforms().add(new Rotate(-rotation, roadTile.tileSize / 2.0, roadTile.tileSize / 2.0));

			pathPoints.add(c1);
			pathPoints.add(c2);
			pathPoints.add(c3);
			pathPoints.add(c4);

			roadTile.getChildren().addAll(c1, c2, c3, c4);
			break;
		}
	}
	
	private void pathDrawer(Circle point) {
		if (isDrawingPath) {
			Line line;
			if(createdPaths.getLast().getElements().getLast() instanceof LineTo) {
				line = new Line(((LineTo) createdPaths.getLast().getElements().getLast()).getX(),
						((LineTo) createdPaths.getLast().getElements().getLast()).getY(),
						point.localToScene(point.getCenterX(), point.getCenterY()).getX(),
						point.localToScene(point.getCenterX(), point.getCenterY()).getY());
				
			}else {
				line = new Line(((MoveTo) createdPaths.getLast().getElements().getLast()).getX(),
						((MoveTo) createdPaths.getLast().getElements().getLast()).getY(),
						point.localToScene(point.getCenterX(), point.getCenterY()).getX(),
						point.localToScene(point.getCenterX(), point.getCenterY()).getY());
			}
			line.setViewOrder(-10);
			line.setStyle("-fx-stroke: " + currentPathColor + "-fx-stroke-width: 3;");
			
			markerLines.add(line);

			levelPane.getChildren().add(line);

			createdPaths.getLast().getElements().add(new LineTo(point.localToScene(point.getCenterX(), point.getCenterY()).getX(), 
					point.localToScene(point.getCenterX(), point.getCenterY()).getY()));
		}
	}

}
