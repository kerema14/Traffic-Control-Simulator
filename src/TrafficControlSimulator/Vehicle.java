package TrafficControlSimulator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Vehicle extends Pane{
    private int rotation;
	private int gridX;
	private int gridY;
	private double panePosX;
	private double panePosY;
	private double vehicleX;
	private double vehicleY;
	private final double vehicleSize = 100;
    public Vehicle( int gridX, int gridY) {
		
		
		this.gridX = gridX;
		this.gridY = gridY;
		

		panePosX = this.gridX * vehicleSize;
		panePosY = this.gridY * vehicleSize;

		addVehicle();
	}
    private void addVehicle() {
		Shape vehicle = createVehicle();
		this.getChildren().add(vehicle);

	}
    private Shape createVehicle(){
        Rectangle rect0 = new Rectangle(vehicleSize, vehicleSize * 0.8);
			rect0.setFill(Color.rgb(255, 25, 255, 0.8));
			
			rect0.setX(0);
			rect0.setY(vehicleSize * 0.1); 
			
			rect0.getTransforms().add(new Rotate(rotation, vehicleSize/2.0, vehicleSize/2.0));
			
			return rect0;
    }
}