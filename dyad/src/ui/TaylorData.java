package ui;

import java.awt.Image;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class TaylorData {

	private Hashtable<String, Animation> images;

	public TaylorData() throws IOException {
		images = new Hashtable<>();
		addImage("magus", 1);
		addImage("champion", 1);
		addImage("wall", 1);
		addImage("spiral", 360);
	}

	public Animation getImages(String name) throws Exception {
		if (images.containsKey(name)) {
			return images.get(name);
		} else {
			throw new Exception("Image "+name+" not present in Images Hashtable");
		}
	}

	private void addImage(String name, int numberOfImages) throws IOException {
		String loc = "resources/" + name + ".png";
		System.out.println("reading " + loc);
		Image img = ImageIO.read(this.getClass().getClassLoader()
				.getResource(loc));

		Animation anim = new Animation(img, 1, numberOfImages);
		images.put(name, anim);
	}
}
