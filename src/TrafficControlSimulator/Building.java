//150123045 Buğra Kaya
//150123055 Kerem Adalı 
//150122029 Ali Talip Keleş
package TrafficControlSimulator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
		Shape shape;

		Rectangle base = new Rectangle(tileSize * 2.2, tileSize * 3.3);
		base.setArcWidth(tileSize * 0.5);
		base.setArcHeight(tileSize * 0.5);	
		base.setStyle("-fx-fill: #eff9fe; -fx-stroke: #6b8393; -fx-stroke-width: 3;");
		base.getTransforms().add(new Rotate(rotation, tileSize * 1.1, tileSize * 1.65));

		Rectangle rect1;
		rect1 = createSquare(tileSize * 1.7);
		rect1.setTranslateX(tileSize * 0.25);
		rect1.setTranslateY(tileSize * 0.25);
		rect1.getTransforms().add(new Rotate(rotation, tileSize * 0.85, tileSize * 1.4));

		Rectangle rect2;
		rect2 = createSquare(tileSize * 1.3);
		rect2.setTranslateX(tileSize * 0.45);
		rect2.setTranslateY(tileSize * 0.45);
		rect2.getTransforms().add(new Rotate(rotation, tileSize * 0.65, tileSize * 1.3));

		Circle circle1 = createCircle(1.7);
		circle1.setCenterX(tileSize);
		circle1.setCenterY(tileSize);
		circle1.getTransforms().add(new Rotate(rotation, tileSize, tileSize * 1.5));

		Circle circle2 = createCircle(1.3);
		circle2.setCenterX(tileSize);
		circle2.setCenterY(tileSize);
		circle2.getTransforms().add(new Rotate(rotation, tileSize, tileSize * 1.5));
		
		//position tuning
		switch (type) {
		case (0):
			if(rotation == 90) {
				base.setTranslateX(tileSize * 0.55);
				base.setTranslateY(-tileSize * 0.55);
				
				rect1.setTranslateX(-tileSize * 0.3);
				rect1.setTranslateY(-tileSize * 0.3);
				
				rect2.setTranslateX(-tileSize * 0.2);
				rect2.setTranslateY(-tileSize * 0.2);
			}
		
			if (rotation == 270) {
				base.setTranslateX(tileSize * 0.55);
				base.setTranslateY(-tileSize * 0.55);
				
				rect1.setTranslateX(tileSize * 1.85);
				rect1.setTranslateY(-tileSize * 0.3);
				
				rect2.setTranslateX(tileSize * 2.15);
				rect2.setTranslateY(-tileSize * 0.2);
			}
			
			if(rotation == 180) {
				rect2.setTranslateY(tileSize * 0.25);
			}

			this.getChildren().addAll(base, rect1, rect2);
			break;
		case (1):
			if (rotation % 180 != 0) {
				base.setTranslateX(-base.getBoundsInParent().getMinX());
				base.setTranslateY(-base.getBoundsInParent().getMinY());

				if (rotation == 270) {
					circle1.setTranslateX(-circle1.getBoundsInParent().getMinX() / 0.23);
					circle2.setTranslateX(-circle2.getBoundsInParent().getMinX() / 0.1);
				} else {
					circle1.setTranslateX(-circle1.getBoundsInParent().getMinX() / 1.2);
					circle2.setTranslateX(-circle2.getBoundsInParent().getMinX() / 1.5);
				}

				circle1.setTranslateY(-circle1.getBoundsInParent().getMinY() / 1.5);
				circle2.setTranslateY(-circle1.getBoundsInParent().getMinY() / 0.5);
			}

			this.getChildren().addAll(base, circle1, circle2);
			break;
		case (2):
			shape = createSquare(tileSize);
			shape.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));

			this.getChildren().add(shape);
			break;
		}
	}

	private Rectangle createSquare(double x) {

		Rectangle rect = new Rectangle(x, x);

		rect.setArcWidth(x * 0.3);
		rect.setArcHeight(x * 0.3);
		
		switch (colorIndex) {
		case (0):
			rect.setStyle("-fx-fill: #fecb9b; -fx-stroke: #f2b98d; -fx-stroke-width: 5;");
			break;

		case (1):
			rect.setStyle("-fx-fill: #8edbb7; -fx-stroke: #83cfac; -fx-stroke-width: 5;");
			break;

		case (2):
			rect.setStyle("-fx-fill: #bcb5e9; -fx-stroke: #aca5d5; -fx-stroke-width: 5;");
			break;

		case (3):
			rect.setStyle("-fx-fill: #ef7e91; -fx-stroke: #da7283; -fx-stroke-width: 5;");
			break;
		}

		return rect;
	}

	private Circle createCircle(double r) {

		Circle circle = new Circle();

		circle.setRadius(tileSize * r / 2.0);

		switch (colorIndex) {
		case (0):
			circle.setStyle("-fx-fill: #fecb9b; -fx-stroke: #f2b98d; -fx-stroke-width: 5;");
			break;

		case (1):
			circle.setStyle("-fx-fill: #8edbb7; -fx-stroke: #83cfac; -fx-stroke-width: 5;");
			break;

		case (2):
			circle.setStyle("-fx-fill: #bcb5e9; -fx-stroke: #aca5d5; -fx-stroke-width: 5;");
			break;

		case (3):
			circle.setStyle("-fx-fill: #ef7e91; -fx-stroke: #da7283; -fx-stroke-width: 5;");
			break;
		}

		return circle;

	}

	public String getColor() {
		switch (colorIndex) {
		case (0):
			return "#fecb9b;";
		case (1):
			return "#8edbb7;";
		case (2):
			return "#bcb5e9;";
		case (3):
			return "#ef7e91;";
		}
		return null;
	}

	public double getPanePosX() {
		return panePosX;
	}

	public double getPanePosY() {
		return panePosY;
	}
	
	public double getTileSize() {
		return tileSize;
	}

	public int getType() {
		return type;
	}

	public int getRotation() {
		return rotation;
	}
}
