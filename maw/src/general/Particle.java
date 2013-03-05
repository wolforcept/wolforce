package general;

import java.awt.Color;

public class Particle {

	private double x, y, tx, ty;
	private static double speed = 1;
	private Color color;
	private boolean removed;

	public Particle(int x, int y, int tx, int ty, Color color) {
		this.x = x;
		this.y = y;
		this.tx = tx;
		this.ty = ty;
		removed = false;
		this.color = color;
	}

	public void step() {
		double dir = Auxi.point_direction(x, y, tx, ty);
		x += speed * Math.cos(dir);
		y -= speed * Math.sin(dir);
		x += Math.random() * 2 - 1;
		y += Math.random() * 2 - 1;
		if (Auxi.point_distance(x, y, tx, ty) < speed) {
			removed = true;
		}
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public static void setSpeed(double speed) {
		Particle.speed = speed;
	}

	public Color getColor() {
		return color.brighter();
	}

	public boolean isRemoved() {
		return removed;
	}

}
