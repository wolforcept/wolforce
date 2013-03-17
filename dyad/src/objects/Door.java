package objects;

public class Door extends GameObject {

	public Door(int x, int y) {
		super("door", x, y);
	}

	@Override
	public int getStrength() {
		return 5;
	}

}
