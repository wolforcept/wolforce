package code.general;

import java.awt.Image;
import java.util.HashMap;

import code.enums.MagusMana;
import code.enums.SpellType;
import code.ui.Measures;
import code.ui.TaylorData;

public class SpellButton {

	private SpellType type;
	private int x, y, w, h, orderNr;
	private Measures m;

	public SpellButton(SpellType type, int orderNr, int x, int y, int w, int h,
			Measures m) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.m = m;
		this.orderNr = orderNr;
	}

	public TargetingSpell getSpell() {
		return new TargetingSpell(type);
	}

	public boolean isPossible(HashMap<MagusMana, Integer> mana_avaliable) {
		for (MagusMana m : MagusMana.values()) {

			int required = type.getManacost(m);
			if (mana_avaliable.containsKey(m)) {
				if (mana_avaliable.get(m) < required)
					return false;
			} else {
				if (required > 0)
					return false;
			}
		}
		return true;
	}

	public Image getImage(TaylorData data) {
		try {
			return data.getImage(type.getButtonName());
		} catch (Exception e) {
			System.err.println("Unable to get Button Image for " + type.name());
			System.exit(0);
			return null;
		}
	}

	public int getX() {
		return m.centerX + x;
	}

	public int getY() {
		return y + m.cz * orderNr;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
