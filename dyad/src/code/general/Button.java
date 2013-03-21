package code.general;

import java.util.HashMap;

import code.enums.Mana;
import code.enums.SpellType;
import code.objects.GameObject;


public class Button extends GameObject {
	private SpellType type;

	public Button(int xx, int yy, SpellType type) {
		super(type.toString().toLowerCase() + "_button", xx, yy, 100);
		this.type = type;
	}

	public TargetingSpell getSpell() {
		return new TargetingSpell(type);
	}

	@Override
	public int getStrength() {
		return 1000;
	}

	public boolean isPossible(HashMap<Mana, Integer> mana_avaliable) {
		for (Mana m : Mana.values()) {

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

}
