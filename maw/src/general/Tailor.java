package general;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Tailor extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ivory ivory;

	private static int background_color_var = (int) (Math.random() * 255);
	private static boolean background_color_dir = true;

	public Tailor(Ivory ivory) {
		this.ivory = ivory;
	}

	// @Override
	// public void update(Graphics g) {
	//
	// paint(g);
	//
	// }

	@Override
	public void paint(Graphics g) {

		g.setColor(new Color((int) (Math.random() * 10), background_color_var,
				255 - background_color_var));

		if (background_color_dir)
			background_color_var++;
		else
			background_color_var--;
		if (background_color_var > 255) {
			background_color_dir = false;
			background_color_var = 255;
		}
		if (background_color_var < 0) {
			background_color_dir = true;
			background_color_var = 0;
		}

		g.fillRect(0, 0, getWidth(), getHeight());

		// g.setColor(Color.LIGHT_GRAY);
		// g.drawLine(0, 0, getWidth(), getHeight());

		int transparency = Ivory.getTransparency();

		LinkedList<Node> nlist = ivory.getNodesClone();
		for (Node node : nlist) {

			// g.fillOval(node.getX(), node.getY(), 5, 5);
			LinkedList<Node> conns = node.getConnectionsClone();

			for (Node conn : conns) {
				for (int i = 0; i < transparency; i++) {

					g.setColor(node.getColor());
					g.drawLine(
							node.getX()
									+ (int) (Math.random() * Ivory
											.getThickness()),
							node.getY()
									+ (int) (Math.random() * Ivory
											.getThickness()),
							conn.getX()
									+ (int) (Math.random() * Ivory
											.getThickness()),
							conn.getY()
									+ (int) (Math.random() * Ivory
											.getThickness()));
				}
			}

			LinkedList<Particle> parts = node.getParticleListClone();
			for (Particle p : parts) {
				g.setColor(p.getColor());
				g.fillOval(p.getX(), p.getY(), 6, 6);
			}

		}

		LinkedList<Ball> balls = ivory.getBallsClone();
		for (Ball b : balls) {
			g.setColor(b.getColor());
			g.fillOval(b.getX(), b.getY(), 15, 15);
		}

		// LinkedList<NodeConnection> clist = ivory.getConnectionsClone();

		// for (NodeConnection c : clist) {
		// for (int i = 0; i < 30; i++) {
		//
		// g.setColor(c.getNode1().getColor());
		// g.drawLine(c.getNode1().getX() + (int) (Math.random() * mexer),
		// c.getNode1().getY() + (int) (Math.random() * mexer), c
		// .getNode2().getX()
		// + (int) (Math.random() * mexer), c.getNode2()
		// .getY() + (int) (Math.random() * mexer));
		// }
		// }
	}
}
