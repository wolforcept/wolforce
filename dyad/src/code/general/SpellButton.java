package code.general;

import java.awt.Image;
import java.util.HashMap;

import code.enums.MagusMana;
import code.enums.SpellType;
import code.ui.TaylorData;

public class SpellButton {
	private SpellType type;

	public SpellButton(SpellType type) {
		this.type = type;
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
			return data.getImages(type.getButtonName()).getImage(0);
		} catch (Exception e) {
			System.err.println("Unable to get Button Image for " + type.name());
			System.exit(0);
			return null;
		}
	}

}
