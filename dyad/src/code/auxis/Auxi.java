package code.auxis;

import java.awt.Point;
import java.awt.event.MouseEvent;

import code.general.SpellButton;

public class Auxi {

	public static double point_distance(double x1, double y1, double x2,
			double y2) {
		return (int) Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public static double point_direction(double x1, double y1, double x2,
			double y2) {
		return Math.atan2((y1 - y2), (x2 - x1));
	}

	public static boolean collides(MouseEvent e, int x, int y) {
		int x1 = e.getX();
		int y1 = e.getY();
		int s = 40;

		if (x1 > x && y1 > y && x1 < x + s && y1 < y + s)
			return true;
		return false;
	}

	public static int getDirFromAngle(double dir) {
		if (dir < 45 && dir > -45) {
			return 0;
		} else if (dir > 45 && dir < 45 + 90) {
			return 1;
		} else if (dir > 45 + 90 || dir < -45 - 90) {
			return 2;
		} else {
			return 3;
		}
	}

	public static void rotatePointMatrixWithDir(Point[] a, int dir) {
		switch (dir) {
		case 1:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(a[i].y, -a[i].x);
			}
			break;
		case 2:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(-a[i].x, -a[i].y);
			}
			break;
		case 3:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(-a[i].y, a[i].x);
			}
			break;
		}
	}
}
