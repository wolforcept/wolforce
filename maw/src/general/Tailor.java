package general;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Tailor extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ivory ivory;
	private double mousex, mousey;

	public Tailor(Ivory ivory) {
		this.ivory = ivory;
		mousex = mousey = 0;
	}

	// @Override
	// public void update(Graphics g) {
	//
	// paint(g);
	//
	// }

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		//
		// g.setColor(Color.LIGHT_GRAY);
		// g.drawString("asdasd", 6, 6);
		//
		// LinkedList<Node> nlist = ivory.getNodesClone();
		// for (Node node : nlist) {
		// g.fillOval(node.getX(), node.getY(), 5, 5);
		// }
		//
		// LinkedList<NodeConnection> clist = ivory.getConnectionsClone();
		// int mexer = 20;
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
		// byte[] a = { 0, 1 };
		byte[] table = new byte[getWidth() * getHeight()];
		Random r = new Random();
		// for (int i = 0; i < table.length; i++) {
		// table[i] = r.nextBytes(a);
		// }
		r.nextBytes(table);
		g.drawBytes(table, 0, 0, getWidth(), getHeight());

		// for (int row = 0; row < getWidth(); row++) {
		// for (int col = 0; col < getHeight(); col++) {
		//
		// // if (Math.random() < 0.1)
		// // g.setColor(new Color((float) Math.random(), (float) Math
		// // .random(), (float) Math.random()));
		//
		// int d = (int) (100 * Auxi.point_distance(row, col, mousex,
		// mousey));
		//
		// g.setColor(new Color(d));
		//
		// g.drawRect(row, col, 1, 1);
		//
		// }
		// }

	}

	public void setMouse(int x, int y) {
		mousex = x;
		mousey = y;
	}
}
