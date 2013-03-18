package general;

import java.awt.Point;

public enum SpellType {

	METEOR(10, true, 16), FLAME_COBRA(10, false, 16);

	private boolean anywhere;
	private int noi, noib;
	private int strength;

	private SpellType(int strength, boolean anywhere, int noi) {
		this.anywhere = anywhere;
		this.noi = noi;
		this.noib = 1;
		this.strength = strength;
	}

	public boolean isAnywhere() {
		return anywhere;
	}

	public String getButtonImageName() {
		return this.toString().toLowerCase() + "_button";
	}

	public int getNOIB() {
		return noib;
	}

	public int getNOI() {
		return noi;
	}

	public int getStrength() {
		return strength;
	}

	public Point[] getArea() {
		switch (this) {
		case METEOR:
			return new Point[] { new Point(-1, -1), new Point(0, 0),
					new Point(1, 1), new Point(-1, 1), new Point(1, -1) };
		case FLAME_COBRA:
			return new Point[] { new Point(1, 0), new Point(2, 0),
					new Point(3, 0), new Point(4, 0), new Point(5, 0),
					new Point(6, 0) };
		default:
			return new Point[] {};
		}
	}
}
