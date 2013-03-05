package objects;

public class GameObject {

	private int numberOfImages, currentImage;
	private String name;
	int x, y;

	public GameObject(String name, int numberOfImages, int x, int y) {
		this.name = name;
		currentImage = 0;
		this.numberOfImages = numberOfImages;
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
			return new Magus(1, x, y);
		case "champion":
			return new Champion(1, x, y);
		case "wall":
			return new Wall(1, x, y);
		case "spiral":
			return new Spiral(360, x, y);
		default:
			return null;
		}

	}

	public String getName() {
		return name;
	}

}
