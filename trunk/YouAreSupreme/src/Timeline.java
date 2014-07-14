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
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Timeline extends JPanel {

	private static final long serialVersionUID = 1L;

	private static BufferedImage map;

	private static Polygon[] polygons;
	private static BufferedImage[] images = new BufferedImage[8];

	private static Object[][] kingdomnames = { { "Islanden", 10, 80 },
			{ "Tribes Territory", 10, 80 }, { "Banter", 10, 80 },
			{ "Carthena", 10, 80 }, { "Fardark", 10, 80 },
			{ "Dreamur", 10, 80 }, { "Daradia", 10, 80 },
			{ "Union of City-Estates", 10, 80 } };
	{
		try {
			map = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(
					"resources/map.png"));

			BufferedImage islanden = ImageIO.read(ClassLoader
					.getSystemClassLoader().getResource(
							"resources/islanden.png"));
			BufferedImage tribes_territory = ImageIO.read(ClassLoader
					.getSystemClassLoader().getResource(
							"resources/tribes_territory.png"));
			BufferedImage banter = ImageIO
					.read(ClassLoader.getSystemClassLoader().getResource(
							"resources/banter.png"));
			BufferedImage carthena = ImageIO.read(ClassLoader
					.getSystemClassLoader().getResource(
							"resources/carthena.png"));
			BufferedImage fardark = ImageIO.read(ClassLoader
					.getSystemClassLoader()
					.getResource("resources/fardark.png"));
			BufferedImage dreamur = ImageIO.read(ClassLoader
					.getSystemClassLoader()
					.getResource("resources/dreamur.png"));
			BufferedImage daradia = ImageIO.read(ClassLoader
					.getSystemClassLoader()
					.getResource("resources/daradia.png"));
			BufferedImage union = ImageIO.read(ClassLoader
					.getSystemClassLoader().getResource("resources/union.png"));

			images[0] = islanden;
			images[1] = tribes_territory;
			images[2] = banter;
			images[3] = carthena;
			images[4] = fardark;
			images[5] = dreamur;
			images[6] = daradia;
			images[7] = union;

		} catch (IOException e) {
			e.printStackTrace();
		}
		makePolygons();
	}

	private int overlayedKingdom, selectedKingom;

	private Ivory ivory;

	public Timeline(Ivory ivory) {
		this.ivory = ivory;
		overlayedKingdom = -1;
		selectedKingom = -1;

		setPreferredSize(new Dimension(800, 500));

		setLayout(new BorderLayout());

		add(new Map(), BorderLayout.CENTER);

		add(new Leaders(), BorderLayout.WEST);

	}

	private void checkMouse(Point m) {
		for (int i = 0; i < polygons.length; i++) {
			if (polygons[i].contains(m)) {
				overlayedKingdom = i;
				repaint();
				return;
			}
		}
		overlayedKingdom = -1;
		repaint();
	}

	private static void makePolygons() {
		polygons = new Polygon[8];
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

			Polygon banter = new Polygon();
			banter = new Polygon(new int[] {

			401, 396, 387, 397, 392, 400, 386, 362, 371, 351, 324, 288, 278,
					265, 241, 219, 226, 228, 235, 252, 246, 241, 225, 185, 168,
					146, 145, 158, 157, 185, 190, 206, 252, 266, 287, 304, 328,
					348, 365, 387, 405

			}, new int[] {

			268, 247, 215, 200, 191, 172, 152, 129, 104, 98, 101, 120, 120,
					112, 114, 130, 152, 194, 210, 213, 224, 242, 259, 265, 247,
					234, 259, 267, 309, 316, 335, 363, 351, 338, 338, 352, 362,
					356, 332, 316, 299

			}, 41);
			polygons[2] = banter;

			Polygon carthena = new Polygon();
			carthena = new Polygon(new int[] { 409, 395, 389, 366, 339, 331,
					300, 286, 265, 245, 212, 232, 242, 250, 263, 276, 296, 318,
					346, 385, 410 }, new int[] { 421, 413, 392, 381, 373, 362,
					349, 337, 339, 352, 363, 379, 380, 401, 409, 424, 430, 424,
					439, 434, 429 }, 21);
			polygons[3] = carthena;

			Polygon fardark = new Polygon();
			fardark = new Polygon(new int[] { 725, 713, 689, 675, 668, 661,
					646, 627, 609, 593, 602, 603, 610, 630, 642, 652, 677, 697,
					720, 739, 741 }, new int[] { 426, 409, 397, 397, 391, 377,
					375, 379, 376, 402, 402, 420, 437, 448, 446, 457, 464, 465,
					460, 450, 440 }, 21);
			polygons[4] = fardark;

			Polygon dreamur = new Polygon();
			dreamur = new Polygon(new int[] { 747, 736, 737, 730, 701, 682,
					671, 663, 628, 612, 606, 604, 583, 580, 590, 610, 629, 647,
					664, 722, 714 }, new int[] { 284, 271, 258, 248, 251, 234,
					244, 236, 254, 258, 269, 283, 293, 318, 337, 376, 380, 373,
					378, 335, 312 }, 21);
			polygons[5] = dreamur;

			Polygon daradia = new Polygon();
			daradia = new Polygon(new int[] {

			605, 592, 590, 581, 583, 540, 496, 463, 444, 396, 398, 410, 422,
					417, 413, 412, 409, 409, 427, 444, 487, 493, 499, 504, 519,
					529, 543, 556, 571, 593, 609

			}, new int[] {

			360, 346, 333, 318, 296, 255, 232, 234, 245, 250, 273, 410, 422,
					417, 413, 412, 409, 409, 427, 444, 487, 493, 499, 504, 519,
					529, 543, 556, 571, 593, 609

			}, 31);
			polygons[6] = daradia;

			Polygon union = new Polygon();
			union = new Polygon(new int[] {

			699, 678, 655, 627, 618, 605, 598, 589, 587, 576, 571, 543, 534,
					523, 507, 474, 457, 451, 433, 423, 423, 406, 387, 372, 362,
					385, 388, 399, 392, 397, 388, 388, 398, 395, 441, 466, 495,
					536, 583, 606, 611, 626, 664, 672, 681, 669, 671, 666, 681,
					692, 696, 690,

			}, new int[] {

			98, 82, 76, 78, 96, 97, 104, 80, 57, 51, 34, 35, 45, 46, 65, 72,
					63, 64, 66, 79, 94, 110, 109, 111, 130, 148, 159, 173, 188,
					202, 214, 235, 243, 251, 246, 232, 232, 251, 296, 282, 259,
					256, 237, 243, 233, 207, 174, 162, 146, 137, 122, 112

			}, 42);
			polygons[7] = union;
		}
	}

	private class Leaders extends JPanel {
		private static final long serialVersionUID = 1L;

		/**
		 * Line Height
		 */
		private static final int lh = 20;

		public Leaders() {
			setBorder(BorderFactory.createTitledBorder("Leaders"));
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, 200);
		}

		@Override
		public void paint(Graphics g) {

			g.setFont(Main.font.deriveFont(12f));
			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			int yy = 0;
			for (Ivory.Leader l : ivory.getLeaders()) {
				g.drawString(l.getName(), 0, yy - 5);
				yy += lh;
			}

			if (overlayedKingdom >= 0) {

			}
		}
	}

	private class Map extends JPanel {
		private static final long serialVersionUID = 1L;

		{
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
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1000, 800);
			// return new Dimension(map.getWidth(), map.getHeight());
		}

		@Override
		public void paint(Graphics g) {
			g.setFont(Main.font.deriveFont(30f));
			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawImage(map, 0, 0, this);
			g.fillPolygon(polygons[6]);
			if (overlayedKingdom >= 0) {
				g.drawImage(images[overlayedKingdom], 0, 0, this);
				g.setColor(new Color(255, 255, 255, 100));
				g.drawString((String) kingdomnames[overlayedKingdom][0],
						(int) kingdomnames[overlayedKingdom][1],
						(int) kingdomnames[overlayedKingdom][2]);
				g.drawString((String) kingdomnames[overlayedKingdom][0],
						(int) kingdomnames[overlayedKingdom][1] + 2,
						(int) kingdomnames[overlayedKingdom][2] + 2);
				g.setColor(new Color(0, 0, 0, 100));
				g.drawString((String) kingdomnames[overlayedKingdom][0],
						(int) kingdomnames[overlayedKingdom][1] + 1,
						(int) kingdomnames[overlayedKingdom][2] + 1);
			}

		}
	}
}
