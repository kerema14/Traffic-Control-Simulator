package TrafficControlSimulator;

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
		double tileWidth = levelParser.getLvlWidth() / levelParser.getLvlColumnNum();
		double tileHeight = levelParser.getLvlHeight() / levelParser.getLvlRowNum();

		// adding empty tiles
		ImageView img;
		for (int c = 0; c < levelParser.getLvlColumnNum(); c++) {
			for (int r = 0; r < levelParser.getLvlRowNum(); r++) {
				img = getEmptyTile(tileWidth, tileHeight);
				img.setTranslateX(r * tileWidth);// set the absolute x coordinate
				img.setTranslateY(c * tileHeight);// set the absolute y coordinate
				this.getChildren().add(img);
			}
		}

		// adding road tiles
		for (RoadTile roadTile : levelParser.roadTiles) {
			roadTile.setTranslateX(roadTile.getPosX());// set the absolute x coordinate
			roadTile.setTranslateY(roadTile.getPosY());// set the absolute y coordinate
			this.getChildren().add(roadTile);
		}

		// adding buildings
		for (Building building : levelParser.buildings) {
			building.setTranslateX(building.getPosX());// set the absolute x coordinate
			building.setTranslateY(building.getPosY());// set the absolute y coordinate
			this.getChildren().add(building);
		}
		
		//adding trafficlights
		Line line;
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

	// method for getting empty tile image
	private ImageView getEmptyTile(double width, double height) {

		Image image = new Image("/images/EmptyTile.png");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

		return imageView;
	}

}
