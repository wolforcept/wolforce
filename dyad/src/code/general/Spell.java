package code.general;

import java.awt.Point;

import code.enums.SpellType;
import code.objects.GameObject;


public class Spell extends GameObject {

	private SpellType type;
	private Point[] area;
	private int dir;

	public Spell(SpellType spellType, int x, int y, Point[] area, int dir) {
		super(spellType.toString().toLowerCase(), x, y, spellType.getStrength());
		this.type = spellType;
		this.area = area;
		this.dir = dir;
	}

	/**
	 * @param tar
	 * @return Returns if the target was removed
	 */
	public boolean interact(GameObject tar) {
		if (tar != null) {
			if (tar.getStrength() < getStrength())
				return true;
		}
		return false;

	}

	public SpellType getType() {
		return type;
	}

	public Point[] getArea() {
		return area;
	}

	public int getDirection() {
		return dir;
	}

}
