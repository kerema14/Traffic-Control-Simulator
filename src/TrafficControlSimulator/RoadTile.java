package TrafficControlSimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoadTile {
	private int type;
	private int rotation;
	private int gridX;
	private int gridY;
	private double posX;
	private double posY;
	private double tileSize;
	private ImageView image;

	public RoadTile(int type, int rotation, int gridX, int gridY, double tileSize) {
		this.type = type;
		this.rotation = rotation;
		this.gridX = gridX;
		this.gridY = gridY-1;// tiles appear 1 tile down on the scene i couldnt fimd the source of the problem
		this.tileSize = tileSize;

		this.posX = this.gridX * tileSize;
		this.posY = this.gridY * tileSize;
		
		setImage();
	}

	// method for getting road tile ImageView
	private void setImage() {

		Image img = new Image("/images/RoadTile-Type" + type + ".png");
		ImageView imageView = new ImageView();
		imageView.setImage(img);
		imageView.setFitWidth(tileSize);
		imageView.setFitHeight(tileSize);
		// setRotate method rotates clockwise but in the level files rotation angles given counter-clockwise for type 1 roads
		if(type == 1) {
			imageView.setRotate(-rotation);
		} else {
			imageView.setRotate(rotation);
		}	

		image = imageView;

	}
	
	public ImageView getImage() {
		
		return image;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
}
