package general;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Launcher extends Thread {

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.generateNodes();
		launcher.start();
		launcher.setVisible();

	}

	private JFrame frame;
	private Ivory ivory;
	private Tailor tailor;
	public Dimension Size;

	public Launcher() {

		Size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		ivory = new Ivory();
		frame = new JFrame("Throne of Maw");
		frame.setUndecorated(true);

		tailor = new Tailor(ivory);

		frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				ivory.setMouse(e.getX(), e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				ivory.setMouse(e.getX(), e.getY());
			}
		});

		frame.add(tailor);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void setVisible() {
		GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(frame);
		frame.setVisible(true);
	}

	public void generateNodes() {

		int number = 5000 + (int) (10 * Math.random());
		int tries = 0;

		for (int i = 0; i < number; i++) {
			int xx, yy;

			xx = 50 + (int) (Math.random() * (Size.width - 100));
			yy = 50 - Ivory.getThickness()
					+ (int) (Math.random() * (Size.height - 100));

			LinkedList<Node> tempList = ivory.getNodesClone();

			int minDistance = 100;
			double d = minDistance + 1;
			for (Node node : tempList) {
				d = Math.min(d,
						Auxi.point_distance(node.getX(), node.getY(), xx, yy));
			}

			if (d < minDistance) {

				if (tries < 5000) {
					i--;
					tries++;
				}

			} else {

				ivory.addNode(new Node(xx, yy));// , ivory));
				tries = 0;
			}
		}

		for (final Node node1 : ivory.getNodesClone()) {

			LinkedList<Node> tempList = ivory.getNodesClone();

			Comparator<Node> comparator = new Comparator<Node>() {

				@Override
				public int compare(Node n1, Node n2) {

					double d1 = Auxi.point_distance(node1.getX(), node1.getY(),
							n1.getX(), n1.getY());
					double d2 = Auxi.point_distance(node1.getX(), node1.getY(),
							n2.getX(), n2.getY());

					if (d1 == d2) {
						return 0;
					} else if (d1 > d2) {
						return 1;
					}
					return -1;

				}
			};

			Collections.sort(tempList, comparator);
			tempList.pop();
			int maxConnections = 3;
			for (int i = 0; i < maxConnections; i++) {
				Node node2 = tempList.pop();
				if (!node2.isConnected(node1))
					ivory.connect(node1, node2);
			}

		}

		int nrOfBalls = 10 + (int) (Math.random() * 10);
		for (int i = 0; i < nrOfBalls; i++) {
			LinkedList<Node> nodes = ivory.getNodesClone();
			Collections.shuffle(nodes);
			ivory.addBall(new Ball(nodes.pop(), ivory));
		}

	}

	@Override
	public void run() {
		System.out.println("Launcher Started");
		try {
			while (true) {

				tailor.repaint();

				LinkedList<Ball> balls = ivory.getBallsClone();
				for (Ball v : balls) {
					v.step();
				}

				LinkedList<Node> list = ivory.getNodesClone();
				for (Node node : list) {
					node.step();
				}

				sleep(1000 / 60);
			}
		} catch (InterruptedException e) {
			System.err.println("Launcher interrupted.");
			frame.dispose();
		}
	}

	public Tailor getTailor() {
		return tailor;
	}

}
