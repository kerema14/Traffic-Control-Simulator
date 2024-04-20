package TrafficControlSimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Building extends Pane {

	private int type;
	private int rotation;
	private int colorIndex;// Colr indexs => 0-Orange, 1-Green, 2-Magenta, 3-Red
	private int gridX;
	private int gridY;
	private double panePosX;
	private double panePosY;
	private double tileSize;

	public Building(int type, int rotation, int colorIndex, int gridX, int gridY, double tileSize) {
		this.type = type;
		this.rotation = rotation;
		this.colorIndex = colorIndex;
		this.gridX = gridX;
		this.gridY = gridY;

		this.tileSize = tileSize;
		this.panePosX = this.gridX * tileSize;
		this.panePosY = this.gridY * tileSize;

		addBuilding();
	}

	private void addBuilding() {
		Shape building;
		
		switch(type) {
		case(0):
			Rectangle base0 = new Rectangle(tileSize * 2, tileSize * 3);
			base0.getTransforms().add(new Rotate(rotation, tileSize, tileSize * 3.0 / 2.0));
			this.getChildren().add(base0);
			break;
			
		case(1):
			Rectangle base1 = new Rectangle(tileSize * 2, tileSize * 3);
			base1.getTransforms().add(new Rotate(rotation, tileSize, tileSize * 3.0 / 2.0));
			this.getChildren().add(base1);
			break;
			
			
		case(2):
			building = createSquare(tileSize, tileSize);
			building.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));
			this.getChildren().add(building);
			break;
		}
	}
	
	private Shape createSquare(double w, double h) {

		Rectangle rect2 = new Rectangle(w, h);
	
		rect2.setArcWidth(w * 0.5);
		rect2.setArcHeight(h * 0.5);
		
		rect2.setScaleX(1.2);
		rect2.setScaleY(1.2);
		
		switch(colorIndex) {
		case(0):
			rect2.setStyle("-fx-fill: #fecb9b; -fx-stroke: #f2b98d; -fx-stroke-width: 5;");
			break;
		case(1):
			rect2.setStyle("-fx-fill: #8edbb7; -fx-stroke: #83cfac; -fx-stroke-width: 5;");
			break;
		case(2):
			rect2.setStyle("-fx-fill: #bcb5e9; -fx-stroke: #aca5d5; -fx-stroke-width: 5;");
			break;
		case(3):
			rect2.setStyle("-fx-fill: #ef7e91; -fx-stroke: #da7283; -fx-stroke-width: 5;");
			break;
		}
		
		return rect2;
	}

	public double getPanePosX() {
		return panePosX;
	}

	public double getPanePosY() {
		return panePosY;
	}

}
