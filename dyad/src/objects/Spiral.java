package objects;

public class Spiral extends GameObject {

	public Spiral(int x, int y) {
		super("spiral", x, y);
	}

	@Override
	public int getStrength() {
		return 5;
	}

}
