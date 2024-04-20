package TrafficControlSimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class RoadTile extends Pane{
	private int type;
	private int rotation;
	private int gridX;
	private int gridY;
	private double panePosX;
	private double panePosY;
	private double roadX;
	private double roadY;
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
	}
	
	// method for getting road tile ImageView
	private void addRoad() {
		Shape road = createRoad();
		road.setLayoutX(roadX);
		road.setLayoutY(roadY);
		this.getChildren().add(road);

	}

	private Shape createRoad() {
		Shape shape;
		
		switch(type) {
		case(0):
			Rectangle rect0 = new Rectangle(tileSize, tileSize * 0.8);
			rect0.setFill(Color.rgb(255, 255, 255, 0.8));
			
			roadX = 0;
			roadY = (tileSize - rect0.getLayoutBounds().getMaxY()) / 2.0;
			
			rect0.setRotate(rotation);
			
			return rect0;
			
		case(1):
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
			
			shape.setRotate(-rotation);//in type 1 roads angles are given counter clock-wise
			
			if(rotation == 90) {
				roadX = tileSize * 0.1;
			}else if(rotation == 180) {
				roadX = tileSize * 0.1;
				roadY = tileSize * -0.1;
			}else if(rotation == 270) {
				roadY = tileSize * -0.1;
			}

			return shape;
		
		case(2):
			Rectangle rect2 = new Rectangle(tileSize, tileSize * 0.8);
			rect2.relocate(0, tileSize / 10.0);
			
			Rectangle rect3 = new Rectangle(tileSize * 0.8, tileSize);
			rect3.relocate(tileSize / 10.0, 0);
			
			shape = Shape.union(rect2, rect3);
			shape.setFill(Color.rgb(255, 255, 255, 0.8));
			
			shape.setRotate(rotation);
			
			roadX = 0;
			roadY = (tileSize - shape.getLayoutBounds().getMaxY()) / 2.0;
			
			return shape;
		
		case(3):
			Rectangle rect4 = new Rectangle(tileSize, tileSize * 0.8);
			rect4.relocate(0, tileSize / 10.0);
			
			Rectangle rect5 = new Rectangle(tileSize * 0.8, tileSize * 0.9);
			rect5.relocate(tileSize / 10.0, tileSize / 10.0);
			
			shape = Shape.union(rect4, rect5);
			
			shape.setFill(Color.rgb(255, 255, 255, 0.8));
			
			shape.setRotate(rotation);
			
			if(rotation % 180 == 0) {
				roadX = 0;
				roadY = tileSize - shape.getLayoutBounds().getHeight() - tileSize * 0.1;
			}
			// rotationdan sonra kordinatlar 0.05 kayıyor şimdilik böyle çözüldü
			else if(rotation == 270){
				roadX = tileSize * 0.05;
				roadY = tileSize * -0.05;
			}else {
				roadX = tileSize * -0.05;
				roadY = tileSize * -0.05;
			}
			
			return shape;
		
		default:
			return null;
		}
	}

	public double getPosX() {
		return panePosX;
	}

	public double getPosY() {
		return panePosY;
	}
}
