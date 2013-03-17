package ui;

import general.SpellType;

import java.awt.Image;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class TaylorData {

	private static Hashtable<String, Animation> images;

	public static void init() throws IOException {
		images = new Hashtable<>();
		addImage("magus", 1);
		addImage("champion", 1);
		addImage("woodwall", 1);
		addImage("spiral", 360);

		SpellType[] vals = SpellType.values();
		for (int i = 0; i < vals.length; i++) {
			addSpellImage(vals[i].toString().toLowerCase(), vals[i].getNOI());
			addSpellImage(vals[i].toString().toLowerCase() + "_button",
					vals[i].getNOIB());
		}

	}

	public Animation getImages(String name) throws Exception {
		if (images.containsKey(name)) {
			return images.get(name);
		} else {
			throw new Exception("Image " + name
					+ " not present in Images Hashtable");
		}
	}

	private static void addSpellImage(String name, int noi) throws IOException {
		String loc = "resources/spells/" + name + ".png";
		System.out.println("reading " + loc);
		Image img = ImageIO.read(new TaylorData().getClass().getClassLoader()
				.getResource(loc));

		Animation anim = new Animation(img, 1, noi);
		images.put(name, anim);
	}

	private static void addImage(String name, int numberOfImages)
			throws IOException {
		String loc = "resources/" + name + ".png";
		System.out.println("reading " + loc);
		Image img = ImageIO.read(new TaylorData().getClass().getClassLoader()
				.getResource(loc));

		Animation anim = new Animation(img, 1, numberOfImages);
		images.put(name, anim);
	}

	public static int getNumberOfImages(String name) {
		return images.get(name).getLen();
	}
}
