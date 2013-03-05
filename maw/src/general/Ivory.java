package general;

import java.awt.Point;
import java.util.LinkedList;

public class Ivory {

	private static int thickness = 25;
	private static int transparency = 50;
	private int mousex, mousey;

	private LinkedList<Node> nodes;
	private LinkedList<Ball> balls;

	public Ivory() {

		nodes = new LinkedList<Node>();
		balls = new LinkedList<Ball>();
		mousex = mousey = 0;
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public LinkedList<Ball> getBallsClone() {
		return new LinkedList<Ball>(balls);
	}

	public void addBall(Ball ball) {
		balls.add(ball);
	}

	public LinkedList<Node> getNodesClone() {
		return new LinkedList<Node>(nodes);
	}

	public void connect(Node node1, Node node2) {
		node1.connectTo(node2);
	}

	public void setMouse(int x, int y) {
		mousex = x;
		mousey = y;
	}

	public int getMousex() {
		return mousex;
	}

	public int getMousey() {
		return mousey;
	}

	public Point getMouse() {
		return new Point(mousex, mousey);
	}

	public static int getThickness() {
		return thickness;
	}

	public static int getTransparency() {
		return transparency;
	}

}
