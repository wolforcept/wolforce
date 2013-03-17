package objects;

import ui.TaylorData;

public abstract class GameObject {

	ObjectType type;

	private int currentImage;
	private String name;
	int numberOfImages;
	int x, y;

	public GameObject(String name, int x, int y) {
		this.name = name;
		currentImage = 0;
		numberOfImages = TaylorData.getNumberOfImages(name);
		this.x = x;
		this.y = y;

	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCurrentImage() {
		return currentImage;
	}

	public void incrementImage() {
		currentImage = (currentImage == numberOfImages) ? 0 : currentImage + 1;
	}

	public static GameObject makeByName(String name, int x, int y) {

		switch (name) {
		case "magus":
			return new Magus(x, y);
		case "champion":
			return new Champion(x, y);
		case "woodwall":
			return new Wall(x, y, "wood");
		case "spiral":
			return new Spiral(x, y);
		case "door":
			return new Door(x, y);
		default:
			return null;
		}

	}

	public String getName() {
		return name;
	}

	public int getNumberOfImages() {
		return numberOfImages;
	}

	public abstract int getStrength();
}
