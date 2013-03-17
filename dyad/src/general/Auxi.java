package general;

import java.awt.event.MouseEvent;

public class Auxi {

	public static double point_distance(double x1, double y1, double x2,
			double y2) {
		return (int) Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public static double point_direction(double x1, double y1, double x2,
			double y2) {
		return Math.atan2((y1 - y2), (x2 - x1));
	}

	public static boolean collides(MouseEvent e, Button b) {
		int x1 = e.getX(), x2 = b.getX();
		int y1 = e.getY(), y2 = b.getY();
		int s = 40;

		if (x1 > x2 && y1 > y2 && x1 < x2 + s && y1 < y2 + s)
			return true;
		return false;
	}
}
