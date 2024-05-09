//150123045 Buğra Kaya
//150123055 Kerem Adalı 
//150122029 Ali Talip Keleş
package TrafficControlSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.shape.*;

public class LevelParser {

	private double lvlWidth;
	private double lvlHeight;
	private int lvlColumnNum;
	private int lvlRowNum;
	private double tileSize;
	private int lvlPathNum;
	private int carNumToWin;
	private int maxCarAccident;
	private int carAccident=0;
	private int reachCars=0;
	public int levelIndex;
	
	public File levelFile;

	private ArrayList<int[]> buildingInfo = new ArrayList<>();// type, rotation, color, gridX, gridY
	private ArrayList<int[]> roadTileInfo = new ArrayList<>();// type, rotation, gridX, gridY
	private ArrayList<double[]> trafficLightInfo = new ArrayList<>();// x1, y1, x2, y2
	private ArrayList<double[]> moveToInfo = new ArrayList<>();// path index, posX, posY
	private ArrayList<double[]> lineToInfo = new ArrayList<>();// path index, posX, posY

	public ArrayList<Building> buildings = new ArrayList<>();
	public ArrayList<RoadTile> roadTiles = new ArrayList<>();
	public ArrayList<TrafficLight> trafficLights = new ArrayList<>();
	public ArrayList<Path> paths = new ArrayList<>();

	LevelParser() {
	}

	// method for parsing the level data from txt files and storing them
	public void getLevelInfo(File levelFile) {
		this.levelFile = levelFile;

		Scanner reader = null;
		try {
			File lvlFile = new File("src\\levels\\" + levelFile.getName());
			reader = new Scanner(lvlFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// reading lines
		ArrayList<String[]> lines = new ArrayList<>();

		while (reader.hasNext()) {
			String lineData = reader.nextLine();
			String[] words = lineData.split(" ");
			lines.add(words);
		}
		
		//level Index
		try {
			levelIndex = Integer.parseInt(levelFile.getName().substring(levelFile.getName().length() - 5, levelFile.getName().length() - 4), 10);
		} catch(Exception e) {
			levelIndex = 0;
		}

		// metadata width, height, column, row, number of paths, number of cars to win,
		// maxnumber of cars to lose
		this.lvlWidth = Double.parseDouble(lines.get(0)[1]);
		this.lvlHeight = Double.parseDouble(lines.get(0)[2]);
		this.lvlColumnNum = Integer.parseInt(lines.get(0)[3]);
		this.lvlRowNum = Integer.parseInt(lines.get(0)[4]);
		this.tileSize = lvlWidth / lvlColumnNum;
		this.lvlPathNum = Integer.parseInt(lines.get(0)[5]);
		this.carNumToWin = Integer.parseInt(lines.get(0)[6]);
		this.maxCarAccident = Integer.parseInt(lines.get(0)[7]);

		// getting the other data
		for (String[] line : lines) {
			String dataType = line[0];

			switch (dataType) {
			case "Building":
				int[] buidingData = new int[5];
				buidingData[0] = Integer.parseInt(line[1]);
				buidingData[1] = Integer.parseInt(line[2]);
				buidingData[2] = Integer.parseInt(line[3]);
				buidingData[3] = Integer.parseInt(line[4]);
				buidingData[4] = Integer.parseInt(line[5]);
				this.buildingInfo.add(buidingData);
				break;

			case "RoadTile":
				int[] roadTileData = new int[4];
				roadTileData[0] = Integer.parseInt(line[1]);
				roadTileData[1] = Integer.parseInt(line[2]);
				roadTileData[2] = Integer.parseInt(line[3]);
				roadTileData[3] = Integer.parseInt(line[4]);
				this.roadTileInfo.add(roadTileData);
				break;

			case "TrafficLight":
				double[] trafficLightData = new double[4];
				trafficLightData[0] = Double.parseDouble(line[1]);
				trafficLightData[1] = Double.parseDouble(line[2]);
				trafficLightData[2] = Double.parseDouble(line[3]);
				trafficLightData[3] = Double.parseDouble(line[4]);
				this.trafficLightInfo.add(trafficLightData);
				break;

			case "Path":
				double[] pathData = new double[3];
				int pathIndex = Integer.parseInt(line[1]);
				pathData[1] = Double.parseDouble(line[3]);
				pathData[2] = Double.parseDouble(line[4]);
				if (line[2].equals("MoveTo")) {
					Path path = new Path();
					path.getElements().add(new MoveTo(pathData[1],pathData[2]));
					paths.add(pathIndex, path);
				} else if (line[2].equals("LineTo")) {
					paths.get(pathIndex).getElements().add(new LineTo(pathData[1],pathData[2]));
				}
				break;
			}

		}

		createTiles();

		reader.close();
	}

	private void createTiles() {
		// creating road tiles
		for (int[] roadData : roadTileInfo) {
			roadTiles.add(new RoadTile(roadData[0], roadData[1], roadData[2], roadData[3], tileSize));
		}

		// creating building tiles
		for (int[] buildingData : buildingInfo) {
			buildings.add(new Building(buildingData[0], buildingData[1], buildingData[2], buildingData[3],
					buildingData[4], tileSize));
		}

		// creating traffic light objects
		for (double[] trafficLightData : trafficLightInfo) {
			trafficLights.add(new TrafficLight(trafficLightData[0], trafficLightData[1], trafficLightData[2],
					trafficLightData[3]));
		}

	}
	
	public void increasCarAccident() {
		carAccident++;
	}
	public void increasReachCars() {
		reachCars++;
	}
	// getter methods

	public int getLvlPathNum() {
		return lvlPathNum;
	}

	public int getCarNumToWin() {
		return carNumToWin;
	}

	public int getMaxCarAccident() {
		return maxCarAccident;
	}

	public double getLvlWidth() {
		return lvlWidth;
	}

	public double getLvlHeight() {
		return lvlHeight;
	}

	public int getLvlColumnNum() {
		return lvlColumnNum;
	}

	public int getLvlRowNum() {
		return lvlRowNum;
	}
	public int getCarAccident() {
		return carAccident;
	}
	public int getReachCars() {
		return reachCars;
	}
	

}
