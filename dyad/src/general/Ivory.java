package general;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;

import auxis.Auxi;

import objects.Champion;
import objects.GameObject;
import objects.Magus;
import objects.Player;

public class Ivory {

	public static final int REFRESH_FREQUENCY = 60;
	public static final Dimension SIZE = new Dimension(640, 640);
	public static int WIDTH = 6, HEIGHT = 6, CELL_SIZE = 40;
	private Point mouse;

	private GameObject[][] field;

	private boolean selected;

	private Magus magus;
	private Champion champion;
	private LinkedList<Button> buttons;
	private LinkedList<Spell> spells;
	private TargetingSpell u;

	public Ivory(int levelNumber) {

		field = new GameObject[WIDTH][HEIGHT];

		mouse = new Point(0, 0);

		String[] ObjectsToAdd = Level.getLevel(levelNumber);

		buttons = new LinkedList<Button>();

		SpellType[] vals = SpellType.values();
		for (int i = 0; i < vals.length; i++) {
			Button b = new Button(CELL_SIZE * 7, CELL_SIZE * i, vals[i]);
			buttons.add(b);
		}

		spells = new LinkedList<Spell>();

		for (int i = 0; i < ObjectsToAdd.length; i++) {

			String name = ObjectsToAdd[i].substring(2);
			int x = Integer.parseInt(ObjectsToAdd[i].charAt(0) + "", 10);
			int y = Integer.parseInt(ObjectsToAdd[i].charAt(1) + "", 10);

			field[x][y] = GameObject.makeByName(name, x, y);
			if (name.equals("champion")) {
				champion = (Champion) field[x][y];
				selected = true;
			}
			if (name.equals("magus")) {
				magus = (Magus) field[x][y];
				selected = false;
			}
		}
	}

	public void updateSpells() {

		for (Iterator<Spell> iterator = spells.iterator(); iterator.hasNext();) {
			Spell a = (Spell) iterator.next();

			if (a.getCurrentImage() == a.getNumberOfImages()) {
				if (a.interact(field[a.getX()][a.getY()])) {
					field[a.getX()][a.getY()] = null;
				}
				iterator.remove();
			}
			a.incrementImage();
		}
	}

	public LinkedList<Button> getButtons() {
		return buttons;
	}

	public GameObject[][] getField() {
		return field;
	}

	public Magus getMagus() {
		return magus;
	}

	public Champion getChampion() {
		return champion;
	}

	public GameObject getGameObject(String name) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] != null && name.equals(field[i][j].getName())) {
					return field[i][j];
				}
			}
		}
		return null;
	}

	public void setMouse(Point mouse) {
		this.mouse.x = mouse.x;
		this.mouse.y = mouse.y;
	}

	public Point getMouse() {
		return mouse;
	}

	public boolean move(int x1, int y1, int x2, int y2) {

		if (x2 < 0 || y2 < 0 || x2 > field.length - 1
				|| y2 > field[0].length - 1)
			return false;

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

	public void toggleSelected() {
		selected = selected ? false : true;
	}

	public void using(TargetingSpell u) {
		this.u = u;
	}

	public boolean using() {
		return u != null;
	}

	public void use() {
		Point[] a = u.getSpellType().getArea();

		if (u.getSpellType().isAnywhere()) {
			for (int i = 0; i < a.length; i++) {
				int xx = a[i].x;
				int yy = a[i].y;
				xx += (int) (mouse.x / CELL_SIZE);
				yy += (int) (mouse.y / CELL_SIZE);

				if (xx < field.length && xx >= 0 && yy < field[0].length
						&& yy >= 0)
					spells.add(new Spell(u.getSpellType(), xx, yy));
			}
		} else {

			Player p = selected ? getMagus() : getChampion();
			double gx = p.getX() * CELL_SIZE + CELL_SIZE / 2;
			double gy = p.getX() * CELL_SIZE + CELL_SIZE / 2;

			int dir = Auxi.getDirFromAngle(Math.toDegrees(Auxi.point_direction(
					gx, gy, mouse.x, mouse.y)));

			Auxi.rotatePointMatrixWithDir(a,dir);
			
			for (int i = 0; i < a.length; i++) {

				int xx = p.getX() + a[i].x;
				int yy = p.getY() + a[i].y;

				if (xx < field.length && xx >= 0 && yy < field[0].length
						&& yy >= 0)
					spells.add(new Spell(u.getSpellType(), xx, yy));
			}
		}
		u = null;
	}

	public TargetingSpell getTargetingSpell() {
		return u;
	}

	public LinkedList<Spell> getSpellsClone() {
		return new LinkedList<Spell>(spells);
	}
}
