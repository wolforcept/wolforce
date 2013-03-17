package general;

import objects.GameObject;

public class Spell extends GameObject {

	SpellType type;

	public Spell(SpellType spellType, int x, int y) {
		super(spellType.toString().toLowerCase(), x, y);
		this.type = spellType;
	}

	/**
	 * 
	 * @param tar
	 * @return Returns if the target was removed
	 */
	public boolean interact(GameObject tar) {
		if (tar != null) {
			if (tar.getStrength() > getStrength())
				return true;
		}
		return false;

	}

	@Override
	public int getStrength() {
		return type.getStrength();
	}

}
