package code.general;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import code.enums.ObjectiveType;

public class Level {

	private String title;
	private Dimension gridSize;
	private HashMap<String, Integer> mana;
	private ObjectiveType objective;
	private String target;
	private LinkedList<UnbuiltObject> objectList;

	public Level(String file) {
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream(file)));

		try {
			/*
			 * READ TITLE
			 */
			title = br.readLine();
			System.out.println(title);

			/*
			 * READ GRID SIZE
			 */
			String[] gridSizeline = br.readLine().replaceAll("\\s", "")
					.split(",");
			gridSize = new Dimension(Integer.parseInt(gridSizeline[0]),
					Integer.parseInt(gridSizeline[1]));
			System.out.println(gridSize);

			/*
			 * READ MANA
			 */
			mana = new HashMap<>();

			String[] manaline = br.readLine().replaceAll("\\s", "").split(",");
			for (String s : manaline) {
				String[] thisMana = s.split("=");
				mana.put(thisMana[0], Integer.parseInt(thisMana[1]));
			}
			System.out.println(mana.toString());

			/*
			 * READ OBJECTIVE
			 */
			String objectiveline = br.readLine();
			if (objectiveline.contains(":")) {
				String[] objectiveAndTarget = objectiveline.replaceAll("\\s",
						"").split(":");
				objective = ObjectiveType.valueOf(objectiveAndTarget[0]
						.toUpperCase());
				target = objectiveAndTarget[1];
			} else {
				objective = ObjectiveType.valueOf(objectiveline.toUpperCase());
			}
			System.out.println(objective + ": " + target);

		} catch (IOException e) {

		}
		objectList = new LinkedList<UnbuiltObject>();
		try {
			/*
			 * READ OBJECTS
			 */
			while (true) {
				UnbuiltObject o = new UnbuiltObject();

				String[] objLine = br.readLine().replaceAll("\\s", "")
						.split("/");
				String[] obj = objLine[0].split(":");
				String[] point = obj[1].split(",");

				o.obj = obj[0];
				o.x = Integer.parseInt(point[0]);
				o.y = Integer.parseInt(point[1]);

				if (objLine.length == 2) {
					o.properties = objLine[1].split(";");
				}
				System.out.println(o);
				objectList.add(o);
			}
		} catch (Exception E) {
			System.out.println("-----");
		}
	}

	public ObjectiveType getObjective() {
		return objective;
	}

	public LinkedList<UnbuiltObject> getObjectList() {
		return objectList;
	}

	public HashMap<String, Integer> getMana() {
		return mana;
	}

	public Dimension getGridSize() {
		return gridSize;
	}

	class UnbuiltObject {
		String obj;
		int x, y;
		String[] properties;

		@Override
		public String toString() {
			return obj + " (" + x + "," + y + ") " + (properties != null);
		}
	}

	public String getTarget() {
		return target;
	}
}
