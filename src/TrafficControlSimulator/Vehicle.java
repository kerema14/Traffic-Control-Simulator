package TrafficControlSimulator;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Vehicle extends Pane{
	
	public double paneX;
	public double paneY;
	
	public Vehicle(double paneX, double paneY) {
		this.paneX = paneX;
		this.paneY = paneY;
		
		this.getChildren().add(getCar());
	}

	private Rectangle getCar() {
		Rectangle car = new Rectangle(800.0 / 60.0, 800.0 / 30.0);
		car.setStyle("-fx-fill: darkblue");
		
		return car;
	}
	
}