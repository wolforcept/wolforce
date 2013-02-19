package general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Launcher extends Thread {

	public static final Dimension SIZE = new Dimension(640, 640);

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.generateNodes();
		launcher.setVisible();
		launcher.start();
	}

	private JFrame frame;
	private Ivory ivory;
	private Tailor tailor;

	public Launcher() {

		ivory = new Ivory();

		frame = new JFrame("Throne of Maw");

		tailor = new Tailor(ivory);
		tailor.setPreferredSize(SIZE);

		frame.setLayout(new BorderLayout());
		frame.add(tailor, BorderLayout.CENTER);

		frame.pack();

		frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				tailor.setMouse(e.getX(), e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				tailor.setMouse(e.getX(), e.getY());
			}
		});

		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Teste");

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu1.add(exitMenuItem);

		menuBar.add(menu1);

		frame.setResizable(false);
		frame.setMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void setVisible() {
		frame.setVisible(true);
	}

	private void generateNodes() {

		int number = 10 + (int) (10 * Math.random());

		for (int i = 0; i < number; i++) {

			int xx, yy;

			xx = (int) (SIZE.width * 0.1 + Math.random() * SIZE.width * 0.8);
			yy = (int) (SIZE.height * 0.1 + Math.random() * SIZE.height * 0.8);

			LinkedList<Node> tempList = ivory.getNodesClone();

			int minDistance = 100;
			double d = minDistance + 1;
			for (Node node : tempList) {
				d = Math.min(d,
						Auxi.point_distance(node.getX(), node.getY(), xx, yy));
			}

			if (d < minDistance) {

				i--;

			} else {

				ivory.addNode(new Node(xx, yy));
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
				ivory.connect(node1, node2);
			}

		}

	}

	@Override
	public void run() {
		System.out.println("Launcher Started");
		try {
			while (true) {
				tailor.repaint();

				LinkedList<Node> list = ivory.getNodesClone();
				for (Node node : list) {
					node.update();
				}

				sleep(1000 / 60);
			}
		} catch (InterruptedException e) {
			System.err.println("Launcher interrupted.");
			frame.dispose();
		}
	}

}
