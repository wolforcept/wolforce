package objects;

public class Player extends GameObject {

	public Player(String name, int x, int y) {
		super(name, x, y);
	}

	@Override
	public int getStrength() {
		return 8;
	}

}
