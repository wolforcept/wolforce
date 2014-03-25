package levels;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import code.enums.ObjectiveType;

public class LevelLoader {

	public static 
	
	public static void load() {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemClassLoader().getResourceAsStream("")));

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
			String[] objectiveline = br.readLine().replaceAll("\\s", "")
					.split(":");
			objective = ObjectiveType.valueOf(objectiveline[0].toUpperCase());
			switch (objective) {
			case RETRIEVE_SCROLLS:
				target = Integer.parseInt(objectiveline[1]);
				break;

			default:
				break;
			}

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
}
