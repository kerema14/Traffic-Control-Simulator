package TrafficControlSimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Building extends ImageView {

	private int type;
	private int rotation;
	private int colorIndex;// Colr indexs => 0-Orange, 1-Green, 2-Magenta, 3-Red
	private int gridX;
	private int gridY;
	private double posX;
	private double posY;
	private double tileSize;

	public Building(int type, int rotation, int colorIndex, int gridX, int gridY, double tileSize) {
		this.type = type;
		this.rotation = rotation;
		this.colorIndex = colorIndex;
		this.gridX = gridX;
		this.gridY = gridY;

		this.tileSize = tileSize;
		this.posX = this.gridX * tileSize;
		this.posY = this.gridY * tileSize;

		// buildins that are rotated 90 or 270 degrees appear 0.5 tile down and left.
		if ((rotation == 90 || rotation == 270) && type != 2) {
			this.posY -= 0.5 * tileSize;
			this.posX += 0.5 * tileSize;
		}

		setImage();
	}

	// method for setting building tile's Image
	private void setImage() {

		// Colr indexs => 0-Orange, 1-Green, 2-Magenta, 3-Red
		Image img = new Image("/images/BuildingTile-Type" + type + "-Color" + colorIndex + ".png");
		this.setImage(img);

		// setting the width and height
		if (type == 2) {// type 2 is 1x1 building
			this.setFitWidth(tileSize);
			this.setFitHeight(tileSize);
		} else {// type 0 & 1 buildings' dimension is 2x3 or 3x2 by rotation

			if (rotation == 0 || rotation == 180) {
				this.setFitWidth(2 * tileSize);
				this.setFitHeight(3 * tileSize);
			} else {
				this.setFitWidth(2 * tileSize);
				this.setFitHeight(3 * tileSize);
			}

		}
		// setRotate method rotates clockwise but in the level files rotation angles
		// given counter-clockwise for buildings
		this.setRotate(-rotation);
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

}
