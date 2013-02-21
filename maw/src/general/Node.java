package general;

import java.awt.Color;

public class Node {

	private int x, y;
	private String name;

	// private boolean movingLeft;
	// private int initX;
	// private double direction;

	private int r, g, b;
	private boolean augmenting;

	public Node(int xx, int yy) {
		x = xx;
		y = yy;
		// direction = Math.PI * 2 * Math.random();
		// movingLeft = x > 320;
		// initX = x - 320;
		augmenting = true;
		r = (int) (Math.random() * 255);
		g = (int) (Math.random() * 255);
		b = (int) (Math.random() * 255);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public void update() {

		if (Math.random() > 0.5) {
			if (augmenting) {
				r++;
				if (r >= 255)
					augmenting = false;
			} else {
				r--;
				if (r <= 0)
					augmenting = true;
			}
		}
		// System.out.println("red:"+r);
		// x += Math.cos(direction);
		// y += Math.sin(direction);

		// if(movingLeft){
		// x*=0.99;
		//
		// if(x<320-initX)
		// movingLeft=false;
		//
		// }else {
		// x*=1.01;
		// if(x>320+initX)
		// movingLeft=true;
		// }

	}

	public Color getColor() {
		return new Color(r, (int) (Math.random() * 255), 255 - r);
	}

}
