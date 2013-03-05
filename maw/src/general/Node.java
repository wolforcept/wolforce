package general;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

public class Node {

	private int x, y;
	private String name;

	private int colorVariable;// , g, b;
	private boolean augmenting;
	private LinkedList<Node> connections;
	// private Ivory ivory;
	private LinkedList<Particle> particles;

	public Node(int xx, int yy) {// , Ivory ivory) {
		x = xx;
		y = yy;
		augmenting = true;
		colorVariable = (int) (Math.random() * 255);
		connections = new LinkedList<Node>();
		// this.ivory = ivory;
		particles = new LinkedList<Particle>();

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

	public void step() {

		if (Math.random() > 0.5) {
			if (augmenting) {
				colorVariable++;
				if (colorVariable >= 255)
					augmenting = false;
			} else {
				colorVariable--;
				if (colorVariable <= 0)
					augmenting = true;
			}
		}

		LinkedList<Node> conns = getConnectionsClone();
		for (Node n : conns) {

			if (Math.random() < 0.1) {

				Particle p = new Particle(
						x + (int) (Math.random() * Ivory.getThickness()),//
						y + (int) (Math.random() * Ivory.getThickness()), //
						n.getX() + (int) (Math.random() * Ivory.getThickness()), //
						n.getY() + (int) (Math.random() * Ivory.getThickness()),
						getColor());
				particles.add(p);

			}
		}

		for (Iterator<Particle> iterator = particles.iterator(); iterator
				.hasNext();) {
			Particle p = (Particle) iterator.next();
			p.step();
			if (p.isRemoved())
				iterator.remove();
		}

	}

	public Color getColor() {
		return new Color(colorVariable, (int) (Math.random() * 255),
				255 - colorVariable);
	}

	public void connectTo(Node node) {
		connections.add(node);
	}

	public LinkedList<Node> getConnectionsClone() {
		return new LinkedList<Node>(connections);
	}

	public boolean isConnected(Node node1) {

		LinkedList<Node> conns = getConnectionsClone();
		for (Node node : conns) {
			if (node.equals(node1))
				return true;
		}
		return false;
	}

	public LinkedList<Particle> getParticleListClone() {
		return new LinkedList<Particle>(particles);
	}
}
