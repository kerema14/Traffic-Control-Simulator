package TrafficControlSimulator;

import java.util.logging.Level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LevelPane extends Pane{
	
	public LevelPane() {
		
	}
	
	public void setLevel(LevelParser levelParser) {
		this.setStyle("-fx-background-color: #9cbcdc;");
		
		double tileWidth = levelParser.getLvlWidth() / levelParser.getLvlColumnNum();
		double tileHeight = levelParser.getLvlHeight() / levelParser.getLvlRowNum();

		// adding empty tiles
		ImageView img;
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
		Circle circle;
		for(TrafficLight tl : levelParser.trafficLights) {
			// adding lines
			line = tl.getLine();
			line.relocate(tl.getLinePosX(), tl.getLinePosY());
			this.getChildren().add(line);
			
			//adding circles
			circle = tl.getCircle();
			circle.relocate(tl.getCirclePosX() - tl.getCircleRadius(), tl.getCirclePosY() - tl.getCircleRadius());
			this.getChildren().add(circle);
			
		}
	}

}
