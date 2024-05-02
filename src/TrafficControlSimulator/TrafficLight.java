package TrafficControlSimulator;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

enum TrafficLightMode {
	RED, GREEN;
};

public class TrafficLight {

	private TrafficLightMode trafficLightMode;
	private double linex1;
	private double liney1;
	private double linex2;
	private double liney2;
	private double linePosX;
	private double linePosY;
	private double circlePosX;
	private double circlePosY;
	private final double circleRadius = 800.0 / (15.0 * 8.0); // 1/8 of a tile
	public double rotate = 0; 
	public Circle circle;

	TrafficLight(double x1, double y1, double x2, double y2) {
		linex1 = x1;
		liney1 = y1;
		linex2 = x2;
		liney2 = y2;
		// setting traffic line's x and y values to min because pane adds nodes to their
		// top-left corner's coordinates
		linePosX = (linex1 < linex2) ? linex1 : linex2;
		linePosY = (liney1 < liney2) ? liney1 : liney2;

		// setting circle's position to middle of the traffic line
		circlePosX = (linex1 + linex2) / 2;
		circlePosY = (liney1 + liney2) / 2;
		this.setMode(TrafficLightMode.RED);
		
	}

	public Line getLine() {
		Line line = new Line();
		line.setStartX(linex1);
		line.setStartY(liney1);
		line.setEndX(linex2);
		line.setEndY(liney2);
		line.setRotate(rotate);
		line.setStrokeWidth(1.0f);
		line.setStroke(Color.BLACK);

		return line;
	}

	public Color getColor() {
		return (this.getMode() == TrafficLightMode.GREEN) ? Color.GREEN : Color.RED;
	}

	public TrafficLightMode getMode() {
		return this.trafficLightMode;

	}

	public void toggleLight() {
		this.trafficLightMode = (this.getMode() == TrafficLightMode.GREEN) ? TrafficLightMode.RED
				: TrafficLightMode.GREEN;
		//circle.setFill(this.getColor());
	}

	public void setMode(TrafficLightMode trafficLightMode) {/* 0 for red light, */
		this.trafficLightMode = trafficLightMode;
		//circle.setFill(this.getColor());
	}

	public Circle getCircle() {
		Circle circle = new Circle();
		circle.setCenterX(circlePosX);
		circle.setCenterY(circlePosY);
		circle.setRadius(circleRadius);
		circle.setStrokeWidth(1.0f);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.RED);
		this.circle = circle;

		return circle;
	}
	public double getLinePosX() {
		return linePosX;
	}

	public double getLinePosY() {
		return linePosY;
	}

	public double getCirclePosX() {
		return circlePosX;
	}

	public double getCirclePosY() {
		return circlePosY;
	}

	public double getCircleRadius() {
		return circleRadius;
	}

}
