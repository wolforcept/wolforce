package objects;

import general.Path;

public class Player extends GameObject {

	private Path path;

	public Player(String name, int numberOfImages, int x, int y) {
		super(name, numberOfImages, x, y);
	}

	public void addPath(Path path) {
		this.path = path;
	}

}
