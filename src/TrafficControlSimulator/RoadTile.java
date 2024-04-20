package TrafficControlSimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class RoadTile extends ImageView{
	private int type;
	private int rotation;
	private int gridX;
	private int gridY;
	private double posX;
	private double posY;
	private double tileSize;

	public RoadTile(int type, int rotation, int gridX, int gridY, double tileSize) {
		this.type = type;
		this.rotation = rotation;
		this.gridX = gridX;
		this.gridY = gridY;
		this.tileSize = tileSize;

		this.posX = this.gridX * tileSize;
		this.posY = this.gridY * tileSize;

		setImage();
	}
	
	// method for getting road tile ImageView
	private void setImage() {

		Image img = new Image("/images/RoadTile-Type" + type + ".png");
		this.setImage(img);
		this.setFitWidth(tileSize);
		this.setFitHeight(tileSize);
		// setRotate method rotates clockwise but in the level files rotation angles
		// given counter-clockwise for type 1 roads
		if (type == 1) {
			this.setRotate(-rotation);
		} else {
			this.setRotate(rotation);
		}

	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
}
