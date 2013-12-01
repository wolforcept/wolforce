package code.general;

import java.awt.Dimension;

public class Level {

	public static int getNumberOfLevels() {
		return 2;
	}

	public static Level getLevel(int i) {
		switch (i) {

		case 1:
			return new Level(//
					new String[] { "0,3,magus", "4,0,champion", "1,0,core",
							"2,0,wood_wall", "2,1,wood_wall", "1,1,wood_wall",
							"3,2,wood_wall", "5,2,wood_wall", "2,3,wood_wall",
							"2,4,wood_wall", "5,5,core", "2,2,switch1",
							"5,5,blue_key", "2,5,blue_door", "0,1,red_door",
							"5,1,red_key", "4,2,switch1_door" },//
					//
					new String[] {}, //
					//
					"retrieve:core", //
					//
					// "heat=2:cold=1:mind=2",//
					"",
					//
					new Dimension(6, 6));
		case 2:
			return new Level(//
					new String[] { "1,1,magus", "4,1,champion",
							"1,4,wood_wall", "2,4,wood_wall", "3,4,brick_wall",
							"4,4,brick_wall", "5,4,wood_wall", "0,4,wood_wall",
							"1,5,core", "4,5,humanoid", "5,5,red_key",
							"0,0,red_door" },//
					//
					new String[] { "45:dir=1:st=5" }, //
					//
					"retrieve:core", //
					//
					"heat=2:cold=1:mind=2",//
					//
					new Dimension(10, 10));
		default:
			return null;
		}

	}

	/*
	 * 
	 */

	private String[] objectList;
	private String[] properties;
	private String objective;
	private String mana;
	private Dimension size;

	public Level(String[] objectList, String[] properties, String objective,
			String mana, Dimension size) {
		this.objectList = objectList;
		this.properties = properties;
		this.objective = objective;
		this.mana = mana;
		this.size = size;
	}

	public String getObjective() {
		return objective;
	}

	public String[] getObjectList() {
		return objectList;
	}

	public String[] getProperties() {
		return properties;
	}

	public String getMana() {
		return mana;
	}

	public Dimension getSize() {
		return size;
	}
}
