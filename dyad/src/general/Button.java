package general;

import objects.GameObject;

public class Button extends GameObject {
	private SpellType type;

	public Button(int xx, int yy, SpellType type) {
		super(type.getButtonImageName(), xx, yy);
		this.type = type;
	}

	public TargetingSpell getSpell() {
		return new TargetingSpell(type);
	}

	@Override
	public int getStrength() {
		return 1000;
	}

}
