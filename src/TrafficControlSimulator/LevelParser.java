package TrafficControlSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelParser {

	private double lvlWidth;
	private double lvlHeight;
	private int lvlColumnNum;
	private int lvlRowNum;
	private int lvlPathNum;
	private int carNumToWin;
	private int maxCarAccident;

	private ArrayList<int[]> buildingInfo = new ArrayList<>();// type, rotation, color, gridX, gridY
	private ArrayList<int[]> roadTileInfo = new ArrayList<>();// type, rotation, gridX, gridY
	private ArrayList<double[]> trafficLightInfo = new ArrayList<>();// x1, y1, x2, y2
	private ArrayList<double[]> moveToInfo = new ArrayList<>();// path index, posX, posY
	private ArrayList<double[]> lineToInfo = new ArrayList<>();// path index, posX, posY

	LevelParser() {
	}
	
	//method for parsing the level data from txt files and storing them
	public void getLevelInfo(File levelFile) {

		Scanner reader = null;
		try {
			File lvlFile = new File("src\\levels\\"+levelFile.getName());
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

		// metadata width, height, column, row, number of paths, number of cars to win, maxnumber of cars to lose
		this.lvlWidth = Double.parseDouble(lines.get(0)[1]);
		this.lvlHeight = Double.parseDouble(lines.get(0)[2]);
		this.lvlColumnNum = Integer.parseInt(lines.get(0)[3]);
		this.lvlRowNum = Integer.parseInt(lines.get(0)[4]);
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
				pathData[0] = Double.parseDouble(line[1]);
				pathData[1] = Double.parseDouble(line[3]);
				pathData[2] = Double.parseDouble(line[4]);
				if (line[2].equals("MoveTo")) {
					this.moveToInfo.add(pathData);
				} else if (line[2].equals("LineTo")) {
					this.lineToInfo.add(pathData);
				}
				break;
			}

		}

		reader.close();
	}
	
	//getter methods
	
	public double[] getDimensonInfo() {
		
		double[] dimensionInfo = new double[4];
		dimensionInfo[0] = this.lvlWidth;
		dimensionInfo[1] = this.lvlHeight;
		dimensionInfo[2] = this.lvlColumnNum;
		dimensionInfo[3] = this.lvlRowNum;
		
		return dimensionInfo;
	}

	public int getLvlPathNum() {
		return lvlPathNum;
	}

	public int getCarNumToWin() {
		return carNumToWin;
	}

	public int getMaxCarAccident() {
		return maxCarAccident;
	}
	
	
}
