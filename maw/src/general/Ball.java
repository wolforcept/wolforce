package general;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Ball {

	private static double speed = 3;
	private Color color;
	private Node tarNode;
	private double x, y;
	private Ivory ivory;
	private int color_var;
	private boolean color_dir;

	public Ball(Node node, Ivory ivory) {

		tarNode = node;
		color = new Color(255, 255, 255);
		x = node.getX();
		y = node.getY();
		this.ivory = ivory;

		color_var = (int) (255 * Math.random());
		color_dir = true;

	}

	public void step() {

		if (color_dir) {
			color_var++;
			if (color_var >= 255) {
				color_dir = false;
			}
		} else {
			color_var--;
			if (color_var <= 0) {
				color_dir = true;
			}
		}

		color = new Color(255 - color_var, 125 + (int) (Math.random() * 100),
				color_var);

		double dir = Auxi.point_direction(x, y, tarNode.getX(), tarNode.getY());
		x += speed * Math.cos(dir);
		y -= speed * Math.sin(dir);
		x += Math.random() * 6 - 3;
		y += Math.random() * 6 - 3;
		if (Auxi.point_distance(x, y, tarNode.getX(), tarNode.getY()) < speed) {
			LinkedList<Node> nodes = ivory.getNodesClone();
			Collections.sort(nodes, new Comparator<Node>() {

				@Override
				public int compare(Node node1, Node node2) {
					double d1 = Auxi.point_distance(node1.getX(), node1.getY(),
							x, y);
					double d2 = Auxi.point_distance(node2.getX(), node2.getY(),
							x, y);

					if (d1 == d2) {
						return 0;
					} else if (d1 > d2) {
						return 1;
					}
					return -1;
				}

			});
			nodes.pop();

			LinkedList<Node> tarNodes = new LinkedList<Node>();
			for (int i = 0; i < 3; i++) {
				tarNodes.add(nodes.pop());
			}
			Collections.shuffle(tarNodes);
			tarNode = tarNodes.pop();

		}

	}

	public Color getColor() {
		return color;
	}

	public int getY() {
		return (int) y;
	}

	public int getX() {
		return (int) x;
	}

}
