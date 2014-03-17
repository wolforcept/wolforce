package code.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

import code.auxis.Auxi;
import code.enums.Mana;
import code.general.Ivory;
import code.general.Spell;
import code.general.SpellButton;
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
	private Font font;

	public Taylor(Ivory ivory) throws IOException {
		this.ivory = ivory;
		data = new TaylorData();

		try {
			font = Font.createFont(Font.TRUETYPE_FONT,//
					getClass().getResourceAsStream("/resources/font.ttf"));

		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void update(Graphics g) {
		try {

			g.setFont(font.deriveFont(20f));
			g.drawString("te", 20, 20);

			final int cz = Ivory.CELL_SIZE, field_width = ivory.getFieldSize().width, field_height = ivory
					.getFieldSize().height;

			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

			{
				SpellButton[] b = ivory.getSpellButtons();
				int y = 0;
				for (int i = 0; i < b.length; i++) {
					if (b[i].isPossible(ivory.getMana())) {
						g.drawImage(b[i].getImage(data),
								ivory.getFieldSize().width * Ivory.CELL_SIZE,
								Ivory.CELL_SIZE * y++, this);
					}
				}
			}
			// for (Button b : ivory.getButtons()) {
			// BufferedImage bimg = (BufferedImage) data
			// .getImages(b.getName()).getImage(b.getCurrentImage());
			// if (b.isPossible(ivory.getMana()))
			// g.drawImage(bimg, b.getX(), b.getY(), this);
			//
			//
			// }

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
				if (s.getType().isAnywhere()) {
					image_x -= s.getType().getImageCentre().x;
					image_y -= s.getType().getImageCentre().y;
					g.drawImage(
							data.getImages(s.getName()).getImage(
									s.getCurrentImage() - 1), image_x, image_y,
							this);

				} else if (s.getType().isEverywhere()) {

					Player p = ivory.getSelected() ? ivory.getMagus() : ivory
							.getChampion();
					image_x = p.getX() * cz + cz / 2
							- s.getType().getImageCentre().x;
					image_y = p.getY() * cz + cz / 2
							- s.getType().getImageCentre().y;
					g.drawImage(
							data.getImages(s.getName()).getImage(
									s.getCurrentImage() - 1), image_x, image_y,
							this);

				} else {
					BufferedImage imageAfterTransform = transformImage(
							data.getImages(s.getName()).getImage(
									s.getCurrentImage() - 1), 1, 1,
							s.getDirection(), 0, 0, 1f);
					switch (s.getDirection()) {
					case 0:
						image_x -= s.getType().getImageCentre().x;
						image_y -= s.getType().getImageCentre().y;
						break;
					case 1:
						image_x -= s.getType().getImageCentre().x;
						image_y += s.getType().getImageCentre().y;
						image_y -= imageAfterTransform.getHeight();
						break;
					case 2:
						image_x += s.getType().getImageCentre().x;
						image_y += s.getType().getImageCentre().y;
						image_y -= imageAfterTransform.getHeight();
						image_x -= imageAfterTransform.getWidth();
						break;

					case 3:
						image_x += s.getType().getImageCentre().x;
						image_y -= s.getType().getImageCentre().y;
						image_x -= imageAfterTransform.getWidth();
						break;

					default:
						break;
					}

					g.drawImage(imageAfterTransform, image_x, image_y, this);
				}
			}

			if (ivory.using()) {

				Point[] a = ivory.getTargetingSpell().getSpellType().getArea();

				int squares_centre_x = 0;
				int squares_centre_y = 0;

				if (ivory.getTargetingSpell().getSpellType().isEverywhere()) {
					for (int i = 0; i < field_width; i++) {
						for (int j = 0; j < field_height; j++) {
							drawTargetSquare(g, i * cz, j * cz, cz);
						}
					}
				}
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
				if (manalist.get(mana) > 0) {
					ammount = manalist.get(mana);
					g.setColor(new Color(255, 255, 0, 130));
					g.drawRect(xx + 1, yy + 1, cz - 2, cz - 2);
				} else {
					g.setColor(new Color(255, 50, 50, 255));
					g.setColor(new Color(150, 150, 150, 180));
					g.fillRect(xx, yy, cz, cz);
				}
				char[] ammountNumbers = ((String) (ammount + "")).toCharArray();
				int x = cz + m * cz - Ivory.NUMBER_DIMENIONS.width;
				int y = cz + //
						cz * field_height + cz - Ivory.NUMBER_DIMENIONS.height;
				for (int j = 0; j < ammountNumbers.length; j++) {

					g.drawImage(
							data.getImages(
									"number_"
											+ ammountNumbers[ammountNumbers.length
													- 1 - j]).getImage(0), x
									- j * Ivory.NUMBER_DIMENIONS.width, y, this);
					// g.drawString(ammountNumbers[j] + ".", x, y);
				}

			}
			String text = "Tutorial: Learning the basics";
			g.setFont(font.deriveFont(30f));
			g.setColor(new Color(1f, 1f, 1f, 0.8f));

			FontMetrics fm = g.getFontMetrics();
			int textWidth = fm.stringWidth(text);
			g.drawString(text, getWidth() / 2 - textWidth / 2, 50);
			g.setColor(new Color(0f, 0f, 0f, 0.1f));
			g.drawString(text, getWidth() / 2 - textWidth / 2 - 2, 50 - 2);
			g.setColor(new Color(0f, 0f, 0f, 0.2f));
			g.drawString(text, getWidth() / 2 - textWidth / 2 - 1, 50 - 1);

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

	private BufferedImage transformImage(Image i, double scalex, double scaley,
			int angle, int centerx, int centery, float alpha) {
		int w = Math.max(i.getWidth(this), i.getHeight(this));
		int h = w;

		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = (Graphics2D) b.getGraphics();

		gg.rotate(-Math.PI * angle / 2, w / 2, h / 2);
		gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		gg.drawImage(i, 0, 0, this);

		return b;
	}
}