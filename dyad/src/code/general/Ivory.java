package code.general;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import code.auxis.Auxi;
import code.enums.Mana;
import code.enums.SpellType;
import code.objects.Champion;
import code.objects.GameObject;
import code.objects.Magus;
import code.objects.Player;



public class Ivory {

	public static final int REFRESH_FREQUENCY = 60;
	public static int CELL_SIZE = 40;

	private Point mouse;

	private GameObject[][] field;

	private boolean selected;

	private Magus magus;
	private Champion champion;
	private LinkedList<Button> buttons;
	private LinkedList<Spell> spells;
	private TargetingSpell u;
	private String objective;
	private ObjectiveType objectivetype;

	private HashMap<Mana, Integer> mana;

	private int width, height;

	public Ivory(Level level) {

		String[] temp = level.getObjective().split(":");
		objective = temp[1];
		objectivetype = ObjectiveType.valueOf(temp[0].toUpperCase());

		mana = new HashMap<>();
		String[] manas = level.getMana().split(":");
		for (String man : manas) {
			Mana m = Mana.valueOf(man.split("=")[0].toUpperCase());
			int ammount = Integer.parseInt(man.split("=")[1]);
			mana.put(m, ammount);
			System.out.println("added " + ammount + " " + m.toString()
					+ " to the manapool");
		}

		width = level.getSize().width;
		height = level.getSize().height;
		field = new GameObject[width][height];

		mouse = new Point(0, 0);

		buttons = new LinkedList<Button>();

		SpellType[] vals = SpellType.values();
		for (int i = 0; i < vals.length; i++) {
			Button b = new Button(CELL_SIZE * 7, CELL_SIZE * i, vals[i]);
			buttons.add(b);
		}

		spells = new LinkedList<Spell>();

		for (int i = 0; i < level.getObjectList().length; i++) {

			String name = level.getObjectList()[i].substring(2);
			int x = Integer.parseInt(level.getObjectList()[i].charAt(0) + "",
					10);
			int y = Integer.parseInt(level.getObjectList()[i].charAt(1) + "",
					10);

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
		System.out.println("properties");
		for (int i = 0; i < level.getProperties().length; i++) {
			int x = Integer.parseInt(level.getProperties()[i].charAt(0) + "",
					10);
			int y = Integer.parseInt(level.getProperties()[i].charAt(1) + "",
					10);
			System.out.println("  on " + x + "," + y);

			String[] properties = level.getProperties()[i].substring(3).split(
					":");
			for (int j = 0; j < properties.length; j++) {
				System.out.println("    " + j + ">" + properties[j]);
				String propertyName = properties[j].split("=")[0];
				int propertyVal = Integer.parseInt(properties[j].split("=")[1]);
				switch (propertyName) {
				case "str":
					field[x][y].setStrength(propertyVal);
					break;
				}
			}
		}
	}

	public void updateSpells() {

		for (Iterator<Spell> iterator = spells.iterator(); iterator.hasNext();) {
			Spell spell = (Spell) iterator.next();

			if (spell.getCurrentImage() == spell.getNumberOfImages()) {
				Point[] area = spell.getArea();
				// Array coordinates of the spell centre
				int x = spell.getX(), y = spell.getY();
				if (!spell.getType().isAnywhere()) {
					Auxi.rotatePointMatrixWithDir(area, spell.getDirection());
				}
				for (int p = 0; p < area.length; p++) {
					int px = x + area[p].x;
					int py = y + area[p].y;
					if (px >= 0 && py >= 0 && px < field.length
							&& py < field[0].length)

						if (spell.interact(field[px][py])) {
							field[px][py] = null;
						}
				}
				iterator.remove();
			}
			spell.incrementImage();
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

	public void move(int x1, int y1, int x2, int y2) {
		field[x2][y2] = field[x1][y1];
		field[x2][y2].setPos(x2, y2);
		field[x1][y1] = null;
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

	public TargetingSpell getUsing() {
		return u;
	}

	public void use() {
		Point[] a = u.getSpellType().getArea();

		int x = 0, y = 0;
		if (u.getSpellType().isAnywhere()) {
			if (mouse.x > 0 && mouse.y > 0 && mouse.x < CELL_SIZE * width
					&& mouse.y < CELL_SIZE * height) {
				x = (int) (mouse.x / CELL_SIZE);
				y = (int) (mouse.y / CELL_SIZE);
			}
		} else {
			Player p = selected ? getMagus() : getChampion();
			x = p.getX();
			y = p.getY();
		}
		if (x < field.length && x >= 0 && y < field[0].length && y >= 0) {
			double dir = Math.toDegrees(Auxi.point_direction(x * CELL_SIZE
					+ CELL_SIZE / 2, y * CELL_SIZE + CELL_SIZE / 2, mouse.x,
					mouse.y));
			spells.add(new Spell(u.getSpellType(), x, y, a, Auxi
					.getDirFromAngle(dir)));
		}
		u = null;
	}

	public TargetingSpell getTargetingSpell() {
		return u;
	}

	public LinkedList<Spell> getSpellsClone() {
		return new LinkedList<Spell>(spells);
	}

	public GameObject getObjectAt(int x, int y) {
		return field[x][y];
	}

	public void setPosition(int x, int y, GameObject object) {
		field[x][y] = object;
	}

	enum ObjectiveType {
		RETRIEVE, DESTROY, KILL;
	}

	public HashMap<Mana, Integer> getMana() {
		return mana;
	}

	public Dimension getFieldSize() {
		return new Dimension(width, height);
	}
}
