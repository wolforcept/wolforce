package code.general;

import code.enums.SpellType;


public class TargetingSpell {
	
	SpellType type;

	public TargetingSpell(SpellType type) {
		this.type = type;
	}

	public SpellType getSpellType() {
		return type;
	}
}
