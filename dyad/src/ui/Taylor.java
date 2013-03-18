package ui;

import general.Button;
import general.Ivory;
import general.Spell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JPanel;

import auxis.Auxi;

import objects.Champion;
import objects.GameObject;
import objects.Magus;
import objects.Player;

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
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

			for (Button b : ivory.getButtons()) {
				g.drawImage(
						data.getImages(b.getName()).getImage(
								b.getCurrentImage()), b.getX(), b.getY(), this);

			}
			// g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.01f));

			int cz = Ivory.CELL_SIZE;
			GameObject[][] field = ivory.getField();

			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[0].length; j++) {

					GameObject obj = field[i][j];
					if (obj != null) {
						obj.incrementImage();
						String s = obj.getClass().getSimpleName().toString()
								.toLowerCase();

						if ((obj instanceof Magus && ivory.getSelected())
								|| (obj instanceof Champion && !ivory
										.getSelected())) {
							g.setColor(Color.WHITE);
							g.drawOval(i * cz, j * cz, cz, cz);
							/*
							 * g.setColor(Color.BLACK); g.fillOval(i * cellSz+2,
							 * j * cellSz+2, cellSz-4, cellSz-4); for (int k =
							 * 0; k < 12; k += 2) { g.drawOval(i * cellSz + k, j
							 * cellSz + k, cellSz - k * 2, cellSz - k * 2); }
							 */
						}
						g.drawImage(
								data.getImages(s).getImage(
										obj.getCurrentImage()), i * cz, j * cz,
								this);

					}
					g.setColor(new Color(1f, 1f, 1f, 0.2f));

					g.drawRect(i * cz, j * cz, cz, cz);

				}
			}

			for (Spell s : ivory.getSpellsClone()) {
				g.drawImage(
						data.getImages(s.getName()).getImage(
								s.getCurrentImage()), s.getX() * cz, s.getY()
								* cz, this);
			}

			g.setColor(new Color(255, 0, 0, 50));
			if (ivory.using()) {

				Point[] a = ivory.getTargetingSpell().getSpellType().getArea();

				if (ivory.getTargetingSpell().getSpellType().isAnywhere()) {
					for (int i = 0; i < a.length; i++) {
						int xx = a[i].x;
						int yy = a[i].y;
						if (getMousePosition() != null) {
							int mx = 40 * (int) (getMousePosition().x / 40);
							int my = 40 * (int) (getMousePosition().y / 40);
							int xxx = mx + xx * cz;
							int yyy = my + yy * cz;
							// if (xxx > 0 && yyy > 0 && xxx < ivory.WIDTH * cz
							// && yyy < ivory.HEIGHT * cz)
							g.fillRect(xxx, yyy, cz, cz);
						}
					}
				} else {
					Player p = ivory.getSelected() ? ivory.getMagus() : ivory
							.getChampion();
					double gx = p.getX() * cz + cz / 2;
					double gy = p.getX() * cz + cz / 2;

					if (getMousePosition() != null) {
						int dir = Auxi.getDirFromAngle(Math.toDegrees(Auxi
								.point_direction(gx, gy, getMousePosition().x,
										getMousePosition().y)));

						Auxi.rotatePointMatrixWithDir(a, dir);

						for (int i = 0; i < a.length; i++) {
							int xx = p.getX() + a[i].x;
							int yy = p.getY() + a[i].y;
							// if (xx > 0 && yy > 0 && xx < ivory.WIDTH * cz &&
							// yy < ivory.HEIGHT * cz)
							g.fillRect(xx * cz, yy * cz, cz, cz);
						}
					}
				}
			}

		} catch (Exception e) {
			System.err.println("UI Update Failed.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
