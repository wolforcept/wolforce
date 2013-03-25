package code.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

import code.ui.TaylorData;
import code.auxis.Auxi;
import code.enums.Mana;
import code.general.Button;
import code.general.Ivory;
import code.general.Spell;
import code.objects.Champion;
import code.objects.Collectable;
import code.objects.GameObject;
import code.objects.Humanoid;
import code.objects.Magus;
import code.objects.Player;

public class Taylor extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ivory ivory;
	private TaylorData data;

	public Taylor(Ivory ivory) throws IOException {
		this.ivory = ivory;
		data = new TaylorData();
	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void update(Graphics g) {
		try {
			final int cz = Ivory.CELL_SIZE, field_width = ivory.getFieldSize().width, field_height = ivory
					.getFieldSize().height;

			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

			for (Button b : ivory.getButtons()) {
				g.drawImage(
						data.getImages(b.getName()).getImage(
								b.getCurrentImage()), b.getX(), b.getY(), this);

				if (!b.isPossible(ivory.getMana())) {
					for (int i = 0; i < 10; i++) {
						g.setColor(new Color(250, 0, 0, 250 - i * 25));
						g.drawRect(b.getX() + i, b.getY() + i, cz - i * 2, cz
								- i * 2);
					}
					g.setColor(new Color(100, 100, 100, 100));
					g.fillRect(b.getX(), b.getY(), cz, cz);
				} else {

					for (int i = 0; i < 10; i++) {
						g.setColor(new Color(255, 255, 0, 100 - i * 10));
						g.drawRect(b.getX() + i, b.getY() + i, cz - i * 2, cz
								- i * 2);
					}
				}

			}
			// g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.01f));

			GameObject[][] field = ivory.getField();

			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[0].length; j++) {

					GameObject obj = field[i][j];
					if (obj != null) {
						obj.incrementImage();

						if ((obj instanceof Magus && ivory.getSelected())
								|| (obj instanceof Champion && !ivory
										.getSelected())) {
							for (int jj = 0; jj < 20; jj++) {
								g.setColor(new Color(255, 255, 255,
										(int) (2.5 * jj)));
								g.fillOval(i * cz + jj, j * cz + jj, cz - jj
										* 2, cz - jj * 2);
							}
						}
						if (obj instanceof Magus && ivory.getSelected()) {
							g.drawImage(data.getImages(obj.getName() + "_glow")
									.getImage(obj.getCurrentImage()), i * cz, j
									* cz, this);
						} else if (obj instanceof Champion
								&& !ivory.getSelected()) {
							g.drawImage(data.getImages(obj.getName() + "_glow")
									.getImage(obj.getCurrentImage()), i * cz, j
									* cz, this);
						} else {
							g.drawImage(
									data.getImages(obj.getName()).getImage(
											obj.getCurrentImage()), i * cz, j
											* cz, this);
						}
						if (obj instanceof Humanoid) {
							g.setColor(Color.WHITE);
							g.drawString(((Humanoid) obj).getHumanoidName(),
									obj.getX() * cz + 7, obj.getY() * cz + cz
											- 5);
						}
					}
					g.setColor(new Color(1f, 1f, 1f, 0.2f));

					g.drawRect(i * cz, j * cz, cz, cz);
				}
			}

			for (Spell s : ivory.getSpellsClone()) {
				int image_x = s.getX() * cz + cz / 2;
				int image_y = s.getY() * cz + cz / 2;
				if (s.getType().hasImageCentre()) {
					image_x -= s.getType().getImageCentre().x;
					image_y -= s.getType().getImageCentre().y;
				}

				g.drawImage(
						data.getImages(s.getName()).getImage(
								s.getCurrentImage() - 1), image_x, image_y,
						this);
			}

			if (ivory.using()) {

				Point[] a = ivory.getTargetingSpell().getSpellType().getArea();

				int squares_centre_x = 0;
				int squares_centre_y = 0;

				if (ivory.getTargetingSpell().getSpellType().isAnywhere()) {
					squares_centre_x = (int) (ivory.getMouse().x / cz);
					squares_centre_y = (int) (ivory.getMouse().y / cz);
				} else {
					Player p = ivory.getSelected() ? ivory.getMagus() : ivory
							.getChampion();
					int dir = Auxi.getDirFromAngle(Math.toDegrees(//
							Auxi.point_direction(p.getX() * cz + cz / 2,
									p.getY() * cz + cz / 2, ivory.getMouse().x,
									ivory.getMouse().y)));
					Auxi.rotatePointMatrixWithDir(a, dir);

					squares_centre_x = p.getX();
					squares_centre_y = p.getY();
				}
				for (int i = 0; i < a.length; i++) {
					int xx = squares_centre_x + a[i].x;
					int yy = squares_centre_y + a[i].y;
					if (xx >= 0 && yy >= 0 && xx < field_width
							&& yy < field_height)
						drawTargetSquare(g, xx * cz, yy * cz, cz);
				}
			}

			// DRAW INV
			int invWidth = cz * (1 + Player.INVENTORY_ROOM);
			g.setColor(Color.WHITE);
			g.drawRect(0, cz * field_height, invWidth, cz);
			LinkedList<Collectable> collectablesList;
			if (ivory.getSelected()) {
				g.drawImage(data.getImages("magus").getImage(0), 0, cz
						* field_height, this);
				collectablesList = ivory.getMagus().getInventoryClone();
			} else {
				g.drawImage(data.getImages("champion").getImage(0), 0, cz
						* field_height, this);
				collectablesList = ivory.getChampion().getInventoryClone();
			}
			int i = 0;
			if (collectablesList != null)
				for (Collectable c : collectablesList) {
					i++;
					g.drawImage(data.getImages(c.getName()).getImage(0),
							i * cz, cz * field_height, this);
				}
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(1, cz * field_height + cz - 11, 20, 11);
			g.setColor(new Color(255, 255, 255, 200));
			g.drawString("INV", 1, cz * field_height + cz - 1);

			// DRAW MANA POOL
			g.setColor(Color.WHITE);
			g.drawString("MANA:", 0, cz * (field_height + 2));
			HashMap<Mana, Integer> manalist = ivory.getMana();
			int m = 0;
			for (Mana mana : Mana.values()) {
				m++;
				g.drawImage(data.getImages(mana.toString().toLowerCase())
						.getImage(0), m * cz, cz + cz * field_height, this);
				int ammount = 0, xx = m * cz, yy = cz + cz * field_height;
				if (manalist.containsKey(mana)) {
					ammount = manalist.get(mana);
					g.setColor(new Color(255, 255, 0, 130));
					g.drawRect(xx + 1, yy + 1, cz - 2, cz - 2);
				} else {
					g.setColor(new Color(255, 50, 50, 255));
					// g.drawLine(xx + 2, yy + 2, xx + cz - 4, yy + cz - 4);
					g.setColor(new Color(150, 150, 150, 180));
					g.fillRect(xx, yy, cz, cz);
				}
				g.drawImage(data.getImages("number_" + ammount).getImage(0), m
						* cz, cz + cz * field_height, this);

			}
		} catch (Exception e) {
			System.err.println("UI Update Failed.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void drawTargetSquare(Graphics g, int x, int y, int cz) {
		g.setColor(new Color(255, 0, 0, 50));
		g.fillRect(x, y, cz, cz);
		g.setColor(new Color(255, 100, 0, 100));
		g.drawRect(x, y, cz, cz);
		for (int i = 0; i < (int) (cz / 2); i++) {
			g.setColor(new Color(255, 100, 0, (int) (cz) - i * 2));
			g.drawRect(x + i, y + i, cz - 2 * i, cz - 2 * i);
		}

	}
}