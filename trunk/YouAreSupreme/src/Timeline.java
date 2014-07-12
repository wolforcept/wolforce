import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Timeline extends JPanel {

	private static Image map, islanden;

	private static Polygon[] polygons;
	private static Image[] images = new Image[10];

	private static Object[][] kingdomnames = { { "Islanden", 10, 80 } };
	{
		try {
			map = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(
					"resources/map.png"));
			islanden = ImageIO.read(ClassLoader.getSystemClassLoader()
					.getResource("resources/islanden.png"));

			images[0] = islanden;
		} catch (IOException e) {
			e.printStackTrace();
		}
		makePolygons();
	}

	int selectedKingdom;

	public Timeline(Ivory ivory) {
		selectedKingdom = -1;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 500));

		Map map = new Map();
		add(map);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				checkMouse(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				checkMouse(e.getPoint());
			}
		});

		validate();
		repaint();
	}

	private void checkMouse(Point m) {
		for (int i = 0; i < polygons.length; i++) {
			if (polygons[i].contains(m)) {
				selectedKingdom = i;
				repaint();
				return;
			}
		}
		selectedKingdom = -1;
		repaint();
	}

	private static void makePolygons() {
		polygons = new Polygon[1];
		{
			Polygon islanden = new Polygon();
			islanden = new Polygon(new int[] { 57, 86, 95, 111, 99, 86, 72, 59,
					40, 27, 36, 50 }, new int[] { 97, 88, 104, 115, 122, 150,
					162, 152, 154, 137, 129, 126 }, 12);
			polygons[0] = islanden;

			// TODO NEXT
		}
	}

	private class Map extends JPanel {
		@Override
		public void paint(Graphics g) {
			g.setFont(Main.font.deriveFont(30f));
			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawImage(map, 0, 0, this);
			if (selectedKingdom >= 0) {
				g.drawImage(images[selectedKingdom], 0, 0, this);
				g.setColor(new Color(255, 255, 255, 100));
				g.drawString((String) kingdomnames[selectedKingdom][0],
						(int) kingdomnames[selectedKingdom][1],
						(int) kingdomnames[selectedKingdom][2]);
				g.drawString((String) kingdomnames[selectedKingdom][0],
						(int) kingdomnames[selectedKingdom][1] + 2,
						(int) kingdomnames[selectedKingdom][2] + 2);
				g.setColor(new Color(0, 0, 0, 100));
				g.drawString((String) kingdomnames[selectedKingdom][0],
						(int) kingdomnames[selectedKingdom][1] + 1,
						(int) kingdomnames[selectedKingdom][2] + 1);
			}

		}
	}
}
