package code.general;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import code.auxis.Auxi;
import code.enums.MagusMana;
import code.enums.ObjectiveType;
import code.enums.SpellType;
import code.general.Level.UnbuiltObject;
import code.objects.Champion;
import code.objects.FieldObject;
import code.objects.Magus;
import code.objects.Player;
import code.objects.Touchable;
import code.objects.Touchable.TouchableType;
import code.ui.Measures;

public class Ivory {

	public static final int REFRESH_FREQUENCY = 60;
	public static final Dimension NUMBER_DIMENIONS = new Dimension(12, 12);
	// private static final int NUMBER_OF_SWITCHES = 2;
	public static int CELL_SIZE = 40;

	private Point mouse;

	private FieldObject[][] field;

	private boolean selected;

	private Magus magus;
	private Champion champion;
	private LinkedList<Spell> spells;
	private TargetingSpell u;
	private String target;
	private ObjectiveType objective;

	private HashMap<MagusMana, Integer> manapool;

	private int width, height;
	private SpellButton[] spellButtons;

	private Measures m;

	// private boolean[] switches;

	public Ivory(Level level) {

		m = new Measures();

		target = level.getTarget();
		objective = level.getObjective();

		manapool = new HashMap<>();
		for (Entry<String, Integer> e : level.getMana().entrySet()) {
			manapool.put(MagusMana.valueOf(e.getKey().toUpperCase()),
					e.getValue());
		}
		for (MagusMana m : MagusMana.values()) {
			if (!manapool.containsKey(m))
				manapool.put(m, 0);
		}

		width = level.getGridSize().width;
		height = level.getGridSize().height;
		field = new FieldObject[width][height];

		mouse = new Point(0, 0);

		SpellType[] vals = SpellType.values();
		spellButtons = new SpellButton[vals.length];
		for (int i = 0; i < vals.length; i++)
			spellButtons[i] = //
			new SpellButton(vals[i], i, 230, 100, 40, 40, m);

		spells = new LinkedList<Spell>();

		// switches = new boolean[Ivory.NUMBER_OF_SWITCHES];

		for (UnbuiltObject o : level.getObjectList()) {

			System.out.println("putting " + o.obj + " on " + o.x + "," + o.y);
			field[o.x][o.y] = FieldObject.makeByName(o.obj, o.x, o.y);
			if (o.obj.equals("champion")) {
				champion = (Champion) field[o.x][o.y];
				selected = true;
			}
			if (o.obj.equals("magus")) {
				magus = (Magus) field[o.x][o.y];
				selected = false;
			}
			String[] properties = o.properties;
			if (o.properties != null)
				for (int j = 0; j < properties.length; j++) {
					System.out.println("    " + j + ">" + properties[j]);
					String propertyName = properties[j].split("=")[0];
					int propertyVal = Integer
							.parseInt(properties[j].split("=")[1]);
					switch (propertyName) {
					case "str":
						field[o.x][o.y].setStrength(propertyVal);
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

	public SpellButton[] getSpellButtons() {
		return spellButtons;
	}

	public FieldObject[][] getField() {
		return field;
	}

	public Magus getMagus() {
		return magus;
	}

	public Champion getChampion() {
		return champion;
	}

	public FieldObject getFieldObject(String name) {
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
		// boolean spellPossible = true; HashMap<MagusMana, Integer> manalist =
		// getMana(); MANA_TEST: for (MagusMana mana : MagusMana.values()) { int
		// needs = u.getSpellType().getManacost(mana); int has =
		// manalist.get(mana);if (needs > has) { spellPossible = false;break
		// MANA_TEST;}} if (spellPossible) {}

		for (MagusMana mana : MagusMana.values()) {
			int cost = u.getSpellType().getManacost(mana);
			if (cost > 0) {
				System.out.println("reducing " + mana + " by " + cost);
				reduceMana(mana, cost);
			}
		}

		Point[] a = u.getSpellType().getArea();

		int x = 0, y = 0;
		if (u.getSpellType().isAnywhere()) {
			if (mouse.x > 0 && mouse.y > 0 && mouse.x < CELL_SIZE * width
					&& mouse.y < CELL_SIZE * height) {
				x = (int) ((mouse.x - m.fieldX) / CELL_SIZE);
				y = (int) ((mouse.y - m.fieldY) / CELL_SIZE);
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

	private void reduceMana(MagusMana m, int manacost) {
		int newValue = manapool.get(m) - manacost;
		manapool.put(m, newValue);
	}

	public TargetingSpell getTargetingSpell() {
		return u;
	}

	public LinkedList<Spell> getSpellsClone() {
		return new LinkedList<Spell>(spells);
	}

	public FieldObject getObjectAt(int x, int y) {
		return field[x][y];
	}

	public void setPosition(int x, int y, FieldObject object) {
		field[x][y] = object;
	}

	public HashMap<MagusMana, Integer> getMana() {
		return manapool;
	}

	public Dimension getFieldSize() {
		return new Dimension(width, height);
	}

	public void switchSwitch(int i) {
		// switches[i] = !switches[i];
		Point p = exists(TouchableType.valueOf("SWITCH" + i + "_DOOR"));
		if (p != null) {
			field[p.x][p.y] = null;
		}
	}

	private Point exists(TouchableType t) {
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[0].length; y++) {
				if (field[x][y] instanceof Touchable
						&& ((Touchable) field[x][y]).getType().equals(t))
					return field[x][y].getPosition();
			}
		}
		return null;
	}

	public Measures getMeasures() {
		return m;
	}
}
