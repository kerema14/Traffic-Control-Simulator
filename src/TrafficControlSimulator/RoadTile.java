package TrafficControlSimulator;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

public class RoadTile extends Pane {
	private int type;
	private int rotation;
	private int gridX;
	private int gridY;
	private double panePosX;
	private double panePosY;
	private double tileSize;

	public RoadTile(int type, int rotation, int gridX, int gridY, double tileSize) {
		this.type = type;
		this.rotation = rotation;
		this.gridX = gridX;
		this.gridY = gridY;
		this.tileSize = tileSize;

		panePosX = this.gridX * tileSize;
		panePosY = this.gridY * tileSize;

		addRoad();
		addLines();
	}

	// method for getting road tile ImageView
	private void addRoad() {
		Shape road = createRoad();
		this.getChildren().add(road);

	}

	private void addLines() {

		switch (type) {
		case (0):
			Line line = new Line(0, tileSize / 2.0, tileSize, tileSize / 2.0);
			line.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));
			line.getStrokeDashArray().addAll(tileSize / 8.0, tileSize / 4.0, tileSize / 4.0, tileSize / 4.0,
					tileSize / 8.0);
			this.getChildren().add(line);
			break;

		case (1):
			QuadCurve curve = new QuadCurve(0, tileSize / 2.0, tileSize / 2.0, tileSize / 2.0, tileSize / 2.0,
					tileSize);
			curve.setFill(Color.rgb(0, 0, 0, 0));
			curve.setStroke(Color.BLACK);
			curve.getStrokeDashArray().addAll(tileSize * 0.1, tileSize * 0.2, tileSize * 0.2, tileSize * 0.2,
					tileSize * 0.1);
			curve.getTransforms().add(new Rotate(-rotation, tileSize / 2.0, tileSize / 2.0));
			this.getChildren().add(curve);
			break;

		case (2):
			break;

		case (3):
			break;
		}

	}

	private Shape createRoad() {
		Shape shape;

		switch (type) {
		case (0):
			Rectangle rect0 = new Rectangle(tileSize, tileSize * 0.8);
			rect0.setFill(Color.rgb(255, 255, 255, 0.8));

			rect0.setX(0);
			rect0.setY(tileSize * 0.1);

			rect0.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));

			return rect0;

		case (1):
			Circle circle0 = new Circle();
			Circle circle1 = new Circle();
			Rectangle rect1 = new Rectangle();
			Shape ring;

			circle0.setRadius(tileSize * 0.9);
			circle1.setRadius(tileSize * 0.1);
			rect1.setWidth(tileSize);
			rect1.setHeight(tileSize);

			circle0.setCenterX(0);
			circle0.setCenterY(tileSize);
			circle1.setCenterX(0);
			circle1.setCenterY(tileSize);

			ring = Shape.subtract(circle0, circle1);
			shape = Shape.intersect(rect1, ring);

			shape.setFill(Color.rgb(255, 255, 255, 0.8));

			shape.getTransforms().add(new Rotate(-rotation, tileSize / 2.0, tileSize / 2.0));

			return shape;

		case (2):
			Rectangle rect2 = new Rectangle(tileSize, tileSize * 0.8);
			rect2.relocate(0, tileSize / 10.0);

			Rectangle rect3 = new Rectangle(tileSize * 0.8, tileSize);
			rect3.relocate(tileSize / 10.0, 0);

			shape = Shape.union(rect2, rect3);
			shape.setFill(Color.rgb(255, 255, 255, 0.8));

			shape.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));

			return shape;

		case (3):
			Rectangle rect4 = new Rectangle(tileSize, tileSize * 0.8);
			rect4.relocate(0, tileSize / 10.0);

			Rectangle rect5 = new Rectangle(tileSize * 0.8, tileSize * 0.9);
			rect5.relocate(tileSize / 10.0, tileSize / 10.0);

			shape = Shape.union(rect4, rect5);

			shape.setFill(Color.rgb(255, 255, 255, 0.8));

			shape.getTransforms().add(new Rotate(rotation, tileSize / 2.0, tileSize / 2.0));

			return shape;

		default:
			return null;
		}
	}

	public double getPanePosX() {
		return panePosX;
	}

	public double getPanePosY() {
		return panePosY;
	}
}
