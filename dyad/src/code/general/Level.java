package code.general;

import java.awt.Dimension;

public class Level {

	public static int getNumberOfLevels() {
		return 1;
	}

	public static Level getLevel(int i) {
		switch (i) {

		case 1:
			return new Level(//
					new String[] { "11magus", "41champion", "14wood_wall",
							"24wood_wall", "34brick_wall", "44brick_wall",
							"54wood_wall", "04wood_wall", "15core",
							"45humanoid", "55red_key", "00red_door" },//
					//
					new String[] { "45:dir=1:st=5" }, //
					//
					"retrieve:core", //
					//
					"heat=2:cold=1",//
					//
					new Dimension(6, 6));
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
