package code.enums;

import java.awt.Point;
import java.util.HashMap;

public enum SpellType {

	METEOR(4, true, 19, "heat=2", new Point(40, 80)), //
	FLAME_COBRA(4, false, 16, "heat=2:mind=1")//
	// ,
	;

	private boolean anywhere;
	private int noi, noib, strength;
	private HashMap<Mana, Integer> manacost;
	private Point centre;

	private SpellType(int strength, boolean anywhere, int noi, String allcosts,
			Point centre) {
		this(strength, anywhere, noi, allcosts);
		this.centre = centre;
	}

	private SpellType(int strength, boolean anywhere, int noi, String allcosts) {
		this.anywhere = anywhere;
		this.noi = noi;
		this.noib = 1;
		this.strength = strength;

		manacost = new HashMap<>();

		String[] allcostsarray = allcosts.split(":");
		for (String cost : allcostsarray) {
			int val = Integer.parseInt(cost.split("=")[1]);
			Mana m = Mana.valueOf(cost.split("=")[0].toUpperCase());
			System.out.println(this + " costs " + val + " of " + m);
			manacost.put(m, val);
		}

	}

	public boolean isAnywhere() {
		return anywhere;
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

	public int getManacost(Mana m) {
		if (manacost.containsKey(m))
			return manacost.get(m);
		return 0;
	}

	public Point[] getArea() {
		switch (this) {
		case METEOR:
			return new Point[] { new Point(-1, -1), new Point(0, 0),
					new Point(1, 1), new Point(-1, 1), new Point(1, -1) };
		case FLAME_COBRA:
			return new Point[] { new Point(1, 0), new Point(2, 0),
					new Point(3, 0), new Point(4, 0) };
		default:
			return new Point[] {};
		}
	}

	public Point getImageCentre() {
		return centre;
	}

	public boolean hasImageCentre() {
		return centre != null;
	}
}
