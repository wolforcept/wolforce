package code.objects;

import code.ui.TaylorData;
import code.enums.Material;
import code.objects.Collectable.CollectableType;
import code.objects.Touchable.TouchableType;

public abstract class GameObject {

	private int currentImage;
	private String name;
	private int numberOfImages;
	private int x, y, strength;

	public GameObject(String name, int x, int y, int str) {
		try {
			this.name = name;
			currentImage = 0;
			numberOfImages = TaylorData.getNumberOfImages(name);
			this.x = x;
			this.y = y;
			strength = str;
		} catch (Exception e) {
			System.err.println(//
					"Failed to create GameObject "
							+ this.getClass().getSimpleName() + " with name: "
							+ name);
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

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

		// PLAYERS
		case "magus":
			return new Magus(x, y);
		case "champion":
			return new Champion(x, y);

			// HUMANOIDS
		case "humanoid":
			return new Humanoid(x, y);

			// COLLECTABLES
		case "red_key":
			return new Collectable(x, y, CollectableType.RED_KEY);
		case "blue_key":
			return new Collectable(x, y, CollectableType.BLUE_KEY);

			// COLLECTABLES
		case "red_door":
			return new Touchable(x, y, TouchableType.RED_DOOR, Material.WOOD);
		case "blue_door":
			return new Touchable(x, y, TouchableType.BLUE_DOOR, Material.WOOD);

			// WALLS
		case "wood_wall":
			return new Wall(x, y, Material.WOOD);
			// case "stonewall":
			// return new Wall(x, y, Material.STONE);
		case "brick_wall":
			return new Wall(x, y, Material.BRICK);
			// case "everflamewall":
			// return new Wall(x, y, Material.EVERFLAME);
			// case "evericewall":
			// return new Wall(x, y, Material.EVERICE);

			// OBJECTS
		case "core":
			return new Core(x, y);
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

	public int getStrength() {
		return strength;
	}

	public void setStrength(int s) {
		strength = s;
	}

}
