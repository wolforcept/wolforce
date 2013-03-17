package general;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import objects.Champion;
import objects.Door;
import objects.GameObject;
import objects.Magus;
import objects.Player;
import general.Level;

public class Ivory {

	public static final int REFRESH_FREQUENCY = 60;
	public static final Dimension SIZE = new Dimension(640, 640);
	public static int WIDTH = 6, HEIGHT = 6, CELL_SIZE = 40;
	private Point mouse;

	private LinkedList<GameObject> objectList;

	private boolean selected;

	private Magus magus;
	private Champion champion;
	private Door door;

	public Ivory(int levelNumber) {

		objectList = new LinkedList<>();

		mouse = new Point(0, 0);

		String[] ObjectsToAdd = Level.getLevel(levelNumber);

		for (int i = 0; i < ObjectsToAdd.length; i++) {

			String name = ObjectsToAdd[i].substring(2);
			int x = Integer.parseInt(ObjectsToAdd[i].charAt(0) + "", 10);
			int y = Integer.parseInt(ObjectsToAdd[i].charAt(1) + "", 10);

			GameObject objectToAdd = GameObject.makeByName(name, x, y);
			objectList.add(objectToAdd);

			if (objectToAdd instanceof Champion) {
				champion = (Champion) objectToAdd;
				selected = true;
			}
			if (objectToAdd instanceof Magus) {
				magus = (Magus) objectToAdd;
				selected = false;
			}
			if (objectToAdd instanceof Door) {
				magus = (Door) objectToAdd;
				selected = false;
			}

		}

	}

	public LinkedList<GameObject> getObjectListClone() {
		return new LinkedList<GameObject>(objectList);
	}

	public Magus getMagus() {
		return magus;
	}

	public Champion getChampion() {
		return champion;
	}

	public void setMouse(Point mouse) {
		this.mouse.x = mouse.x;
		this.mouse.y = mouse.y;
	}

	public Point getMouse() {
		return mouse;
	}

	public boolean move(int x1, int y1, int x2, int y2) {
		if (field[x2][y2] == null) {
			field[x2][y2] = field[x1][y1];
			field[x2][y2].setPos(x2, y2);
			field[x1][y1] = null;
			return true;
		} else {
			return false;
		}
	}

	public boolean getSelected() {
		return selected;
	}

	public Player getSelectedPlayer() {
		if (getSelected()) {
			return (Player) getGameObject("magus");
		}
		return (Player) getGameObject("champion");
	}

	public void toggleSelected() {
		selected = selected ? false : true;
		System.out.println("selected is: " + selected);
	}
}
