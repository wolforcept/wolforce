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

	private static Image map;

	private static Polygon[] polygons;
	private static Image[] images = new Image[3];

	private static Object[][] kingdomnames = { { "Islanden", 10, 80 },
			{ "Tribes Territory", 10, 80 }, { "Carthena", 10, 80 } };
	{
		try {
			map = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(
					"resources/map.png"));

			Image islanden = ImageIO.read(ClassLoader.getSystemClassLoader()
					.getResource("resources/islanden.png"));
			Image tribes_territory = ImageIO.read(ClassLoader
					.getSystemClassLoader().getResource(
							"resources/tribes_territory.png"));
			Image carthena = ImageIO.read(ClassLoader.getSystemClassLoader()
					.getResource("resources/carthena.png"));

			images[0] = islanden;
			images[1] = tribes_territory;
			images[2] = carthena;

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
		polygons = new Polygon[3];
		{
			Polygon islanden = new Polygon();
			islanden = new Polygon(new int[] { 57, 86, 95, 111, 99, 86, 72, 59,
					40, 27, 36, 50 }, new int[] { 97, 88, 104, 115, 122, 150,
					162, 152, 154, 137, 129, 126 }, 12);
			polygons[0] = islanden;

			Polygon tribes_territory = new Polygon();
			tribes_territory = new Polygon(new int[] { 63, 68, 74, 89, 94, 123,
					136, 117, 106, 133, 154, 179, 194, 208, 241, 249, 292, 319,
					349, 391, 426, 444, 496, 505, 520, 514, 502, 494, 493, 507,
					494 }, new int[] { 500, 474, 447, 446, 425, 414, 395, 374,
					333, 303, 310, 311, 340, 362, 381, 401, 430, 421, 442, 433,
					426, 408, 404, 421, 426, 437, 437, 447, 466, 481, 500 }, 31);
			polygons[1] = tribes_territory;

			Polygon carthena = new Polygon();
			carthena = new Polygon(new int[] { 409, 395, 389, 366, 339, 331,
					300, 286, 265, 245, 212, 232, 242, 250, 263, 276, 296, 318,
					346, 385, 410, }, new int[] {

			421, 413, 392, 381, 373, 362, 349, 337, 339, 352, 363, 379, 380,
					401, 409, 424, 430, 424, 439, 434, 429

			}, 21);
			polygons[2] = carthena;
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
