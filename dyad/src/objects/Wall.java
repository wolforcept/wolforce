package objects;

public class Wall extends GameObject {

	private String walltype;

	public Wall(int x, int y, String walltype) {
		super(walltype + "wall", x, y);
		this.walltype = walltype;
	}

	@Override
	public int getStrength() {
		switch (walltype) {
		case "wood":
			return 1;
		default:
			return 0;

		}
	}

}
