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
import code.enums.Facing;
import code.enums.MagusMana;
import code.enums.SpellType;
import code.general.Ivory;
import code.general.Spell;
import code.general.SpellButton;
import code.objects.Champion;
import code.objects.Collectable;
import code.objects.FieldObject;
import code.objects.Humanoid;
import code.objects.Magus;
import code.objects.Player;

public class Taylor extends JPanel {

	private static final long serialVersionUID = 1L;

	private Ivory ivory;
	private TaylorData data;
	private Font font;
	private Measures m;

	private boolean showFinish;

	public Taylor(Ivory ivory) throws IOException {
		this.ivory = ivory;
		this.m = ivory.getMeasures();
		data = new TaylorData();

		try {
			font = Font.createFont(Font.TRUETYPE_FONT,//
					getClass().getResourceAsStream("/resources/font.ttf"));

		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		showFinish = false;

	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void update(Graphics graphics) {

		try {

			m.cz = Ivory.CELL_SIZE;
			m.fieldX = getWidth() / 2 - ivory.getFieldSize().width * m.cz / 2;
			m.fieldY = getHeight() / 2 - 4 - ivory.getFieldSize().height * m.cz
					/ 2;
			m.fieldX2 = m.fieldX + m.fieldWidth * m.cz;
			m.fieldY2 = m.fieldY + m.fieldHeight * m.cz;
			m.fieldWidth = ivory.getFieldSize().width;
			m.fieldHeight = ivory.getFieldSize().height;
			m.centerX = getWidth() / 2;
			m.centerY = getHeight() / 2;

			graphics.setColor(Color.darkGray);
			graphics.fillRect(0, 0, getWidth(), getHeight());

			BufferedImage background = data.getImage("back");
			graphics.drawImage(background, m.centerX - background.getWidth()
					/ 2, m.centerY - background.getHeight() / 2, this);

			graphics.setColor(new Color(1, 1, 1, 0.03f));
			graphics.fillRect(m.fieldX, m.fieldY, m.fieldWidth * m.cz,
					m.fieldHeight * m.cz);

			if (showFinish) {
				drawFinish(graphics);
				return;
			}

			drawButtons(graphics);

			drawField(graphics);

			drawUsing(graphics);

			drawSpell(graphics);

			drawInventory(graphics);

			drawManaPool(graphics);

			Graphics g = graphics;

			//
			g.setFont(font.deriveFont(40f));
			FontMetrics fm = g.getFontMetrics();

			// DRAW TITLE
			String title = "Tutorial: Learning the basics";

			int titleWidth = fm.stringWidth(title);

			g.setColor(new Color(1f, 1f, 1f, 0.3f));

			int titleX = getWidth() / 2 - titleWidth / 2;
			int titleY = m.centerY - 256;

			g.drawString(title, titleX - 1, titleY - 1);
			// g.drawString(text, getWidth() / 2 - textWidth / 2 + 2, 50 + 2);
			// g.setColor(new Color(1f, 1f, 1f, 0.6f));
			// g.drawString(text, getWidth() / 2 - textWidth / 2 + 4, 50 + 4);

			g.setColor(new Color(0f, 0f, 0f, 0.3f));

			g.drawString(title, titleX, titleY);

			// DRAW SUBTITLE

			String subtitle = ivory.getTitle();
			g.setFont(font.deriveFont(25f));
			fm = g.getFontMetrics();

			g.setColor(new Color(1f, 1f, 1f, 0.3f));

			int subtitleX = getWidth() / 2 - fm.stringWidth(subtitle) / 2 - 1;

			graphics.drawString(subtitle, subtitleX, m.centerY - 216);

			// g.setColor(new Color(1, 0, 0, 0.1f));
			// g.drawLine(getWidth() / 2, 0, getWidth() / 2, 1000);
			// g.fillRect(0, 0, getWidth(), getHeight());

		} catch (Exception e) {
			System.err.println("UI Update Failed.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void drawFinish(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		String endingPhrase = "Good Job!";

		g.setFont(font.deriveFont(60f));
		FontMetrics fm = g.getFontMetrics();

		g.setColor(new Color(1f, 1f, 1f, 0.3f));

		int endingPhraseX = getWidth() / 2 - fm.stringWidth(endingPhrase) / 2
				- 1;

		g.drawString(endingPhrase, endingPhraseX, m.centerY - 216);
	}

	private void drawUsing(Graphics g) {
		if (ivory.using()) {

			Point[] area = ivory.getUsing().getArea();
			SpellType type = ivory.getUsing();
			if (type.isAnywhere()) {

				int mx = ivory.getMouse().x, my = ivory.getMouse().y;

				for (int i = 0; i < area.length; i++) {
					int tarX = m.fieldX + m.cz
							* (int) ((mx - m.fieldX + m.cz * area[i].x) / m.cz);
					int tarY = m.fieldY + m.cz
							* (int) ((my - m.fieldY + m.cz * area[i].y) / m.cz);

					if (mx + m.cz * area[i].x > m.fieldX && //
							my + m.cz * area[i].y > m.fieldY && //
							mx + m.cz * area[i].x < m.fieldX2 && //
							my + m.cz * area[i].y < m.fieldY2) {

						drawTargetSquare(g, tarX, tarY);
					}
				}
			} else {
				for (Point point : area) {
					switch (ivory.getCurrentFacing()) {
					case NORTH:
						point.setLocation(point.y, point.x);
					case WEST:
						point.setLocation(-point.y, point.x);
					case SOUTH:
						point.setLocation(-point.y, point.x);
					default:
					}
					Player p = ivory.getSelected() ? ivory.getMagus() : ivory
							.getChampion();

					int xx = p.getX() + point.x;
					int yy = p.getY() + point.y;
					int sx = m.fieldX + xx * m.cz;
					int sy = m.fieldY + yy * m.cz;
					if (sx >= m.fieldX && sy >= m.fieldY && sx < m.fieldX2
							&& sy < m.fieldY2) {

						drawTargetSquare(g, sx, sy);
					}
				}
			}
		}
	}

	private void drawManaIcon(Graphics g, int ix, int iy, Integer ammount,
			BufferedImage image) {

		int x = m.centerX + ix - image.getHeight() / 2;
		int y = m.centerY + iy - image.getWidth() / 2;

		g.drawImage(image, x, y, this);

		BufferedImage nrimg = data.getImage("number_" + ammount);
		g.drawImage(nrimg,//
				x + image.getWidth() - nrimg.getWidth(), //
				y + image.getHeight() - nrimg.getHeight(), this);

		// char[] ammountNumbers = ((String) (ammount + "")).toCharArray();
		//
		// for (int i = 0; i < ammountNumbers.length; i++) {
		// g.drawImage(
		// data.getImages(
		// "number_"
		// + ammountNumbers[ammountNumbers.length - 1
		// - i]).getImage(0), x - i
		// * Ivory.NUMBER_DIMENIONS.width, y, this);
		// }
	}

	private void drawManaPool(Graphics g) {

		HashMap<MagusMana, Integer> manapool = ivory.getMana();

		drawManaIcon(g, -100, 232, manapool.get(MagusMana.HEAT), data
				.getImages("heat").getImage(0));
		drawManaIcon(g, -50, 226, manapool.get(MagusMana.COLD),
				data.getImages("cold").getImage(0));
		drawManaIcon(g, 50, 226, manapool.get(MagusMana.MIND),
				data.getImages("mind").getImage(0));
		drawManaIcon(g, 100, 232, manapool.get(MagusMana.EARTH), data
				.getImages("earth").getImage(0));

		// ammount = manalist.get(mana);
		// g.setColor(new Color(255, 255, 0, 130));
		// g.drawRect(xx + 1, yy + 1, m.m.cz - 2, m.m.cz - 2);
		// } else {
		// g.setColor(new Color(255, 50, 50, 255));
		// g.setColor(new Color(150, 150, 150, 180));
		// g.fillRect(xx, yy, m.m.cz, m.m.cz);
		// }
		//
		// int x = m.m.cz + m * m.m.cz - Ivory.NUMBER_DIMENIONS.width;
		// int y = m.m.cz + //
		// m.m.cz * m.fieldHeight + m.m.cz - Ivory.NUMBER_DIMENIONS.height;
		// for (int j = 0; j < ammountNumbers.length; j++) {
		//

		// // g.drawString(ammountNumbers[j] + ".", x, y);
		// }
		//
		// }
	}

	private void drawInventory(Graphics g) {
		// DRAW INV
		int invWidth = m.cz * (Player.INVENTORY_ROOM);
		int invX = m.centerX - invWidth / 2;
		int invY = m.centerY + 260;
		g.setColor(Color.WHITE);
		g.drawRect(invX, invY, invWidth, m.cz);

		BufferedImage selectedImage = ivory.getSelected() ? data
				.getImage("magus") : data.getImage("champion");
		LinkedList<Collectable> inventory = ivory.getSelected() ? ivory
				.getMagus().getInventoryClone() : ivory.getChampion()
				.getInventoryClone();
		g.drawImage(selectedImage, m.centerX - selectedImage.getWidth() / 2,
				m.centerY + 200, this);

		int i = 0;
		if (inventory != null)
			for (Collectable c : inventory) {
				g.drawImage(data.getImage(c.getName()), invX + i * m.cz, invY,
						this);
				i++;
			}
	}

	private void drawSpell(Graphics g) {

		Spell spell = ivory.getSpell();
		if (spell == null)
			return;

		SpellType spelltype = spell.getType();

		Player player = ivory.getSelected() ? ivory.getMagus() : ivory
				.getChampion();

		int playerx = m.fieldX + player.getX() * m.cz;
		int playery = m.fieldY + player.getY() * m.cz;

		BufferedImage img = data.getImages(spell.getName()).getImage(
				spell.getCurrentImage() - 1);

		if (spelltype.isAnywhere()) {

			int spellx = m.fieldX + spell.getX() * m.cz //
					+ m.cz / 2 - spelltype.getImageCentre().x;
			int spelly = m.fieldY + spell.getY() * m.cz //
					+ m.cz / 2 - spelltype.getImageCentre().y;

			g.drawImage(img, spellx, spelly, this);

		} else if (spelltype.isEverywhere()) {

			float darkness = (float) spell.getCurrentImage()
					/ (float) spelltype.getNOI();
			g.setColor(new Color(0, 0, 0, darkness));
			g.fillRect(0, 0, getWidth(), getHeight());

			g.drawImage(img, //
					getWidth() / 2 - spelltype.getImageCentre().x, //
					getHeight() / 2 - spelltype.getImageCentre().y, //
					this);

		} else {
			Facing facing = spell.getFacing();
			BufferedImage imageAfterTransform = transformImage(img, 1, 1,
					facing, 0, 0, 1f);

			switch (facing) {
			case SOUTH:
			case EAST:
				g.drawImage(imageAfterTransform, playerx, playery, this);
				break;

			case NORTH:
				g.drawImage(imageAfterTransform, playerx, playery + m.cz
						- imageAfterTransform.getHeight(), this);
				break;

			case WEST:
				g.drawImage(imageAfterTransform, playerx + m.cz
						- imageAfterTransform.getWidth(), playery, this);
				break;

			}

		}

		// TODO REWRITE ALL THIS CODE

		// int image_x = m.fieldX;
		// int image_y = m.fieldY;
		//
		// image_x += s.getX() * m.cz + m.cz / 2;
		// image_y += s.getY() * m.cz + m.cz / 2;
		// if (s.getType().isAnywhere()) {
		// image_x -= s.getType().getImageCentre().x;
		// image_y -= s.getType().getImageCentre().y;
		// g.drawImage(
		// data.getImages(s.getName()).getImage(
		// s.getCurrentImage() - 1), image_x, image_y, this);
		//
		// } else if (s.getType().isEverywhere()) {
		//
		// Player p = ivory.getSelected() ? ivory.getMagus() : ivory
		// .getChampion();
		// image_x += p.getX() * m.cz + m.cz / 2
		// - s.getType().getImageCentre().x;
		// image_y += p.getY() * m.cz + m.cz / 2
		// - s.getType().getImageCentre().y;
		// g.drawImage(
		// data.getImages(s.getName()).getImage(
		// s.getCurrentImage() - 1), image_x, image_y, this);
		//
		// } else {
		// BufferedImage imageAfterTransform = transformImage(
		// data.getImages(s.getName()).getImage(
		// s.getCurrentImage() - 1), 1, 1, s.getDirection(),
		// 0, 0, 1f);
		// switch (s.getDirection()) {
		// case EAST:
		// image_x -= s.getType().getImageCentre().x;
		// image_y -= s.getType().getImageCentre().y;
		// break;
		// case NORTH:
		// image_x -= s.getType().getImageCentre().x;
		// image_y += s.getType().getImageCentre().y;
		// image_y -= imageAfterTransform.getHeight();
		// break;
		// case WEST:
		// image_x += s.getType().getImageCentre().x;
		// image_y += s.getType().getImageCentre().y;
		// image_y -= imageAfterTransform.getHeight();
		// image_x -= imageAfterTransform.getWidth();
		// break;
		//
		// case SOUTH:
		// image_x += s.getType().getImageCentre().x;
		// image_y -= s.getType().getImageCentre().y;
		// image_x -= imageAfterTransform.getWidth();
		// break;
		//
		// default:
		// break;
		// }
		//
		// g.drawImage(imageAfterTransform, image_x, image_y, this);
		// }
	}

	private void drawField(Graphics graphics) {

		FieldObject[][] field = ivory.getField();

		graphics.setColor(new Color(1, 1, 1, 0.5f));
		graphics.drawRect(m.fieldX, m.fieldY, m.fieldWidth * m.cz,
				m.fieldHeight * m.cz);

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {

				FieldObject obj = field[i][j];
				graphics.setColor(new Color(1, 1, 1, 0.1f));
				graphics.drawRect(m.fieldX + i * m.cz, m.fieldY + j * m.cz,
						m.cz, m.cz);

				if (obj == null)
					continue;

				obj.incrementImage();

				String imageName = obj.getName();

				if ((obj instanceof Magus && ivory.getSelected())
						|| (obj instanceof Champion && !ivory.getSelected())) {
					for (int jj = 0; jj < 20; jj++) {
						graphics.setColor(new Color(255, 255, 255,
								(int) (2.5 * jj)));
						graphics.fillOval(m.fieldX + i * m.cz + jj, m.fieldY
								+ j * m.cz + jj, m.cz - jj * 2, m.cz - jj * 2);
					}
					imageName = obj.getClass().getSimpleName().toString()
							.toLowerCase()
							+ "_glow";
				}

				graphics.drawImage(
						data.getImages(imageName).getImage(
								obj.getCurrentImage()), m.fieldX + i * m.cz,
						m.fieldY + j * m.cz, this);

				if (obj instanceof Humanoid) {
					graphics.setColor(Color.WHITE);
					graphics.drawString(((Humanoid) obj).getHumanoidName(),
							obj.getX() * m.cz + 7, obj.getY() * m.cz + m.cz - 5);
				}

				graphics.setColor(new Color(1f, 1f, 1f, 0.2f));

			}
		}
	}

	private void drawButtons(Graphics g) {
		{
			SpellButton[] b = ivory.getSpellButtons();
			// int yy = 0;
			for (int i = 0; i < b.length; i++) {
				// int x = m.centerX + 230;
				// int y = 100 + Ivory.CELL_SIZE * yy++;
				int x = b[i].getX();
				int y = b[i].getY();
				g.drawImage(b[i].getImage(data), x, y, this);
				if (!b[i].isPossible(ivory.getMana())) {
					g.drawImage(data.getImage("spell_not_available"), x, y,
							this);
				}
			}
		}
	}

	private void drawTargetSquare(Graphics g, int x, int y) {
		g.setColor(new Color(255, 0, 0, 50));
		g.fillRect(x, y, m.cz, m.cz);
		g.setColor(new Color(255, 100, 0, 100));
		g.drawRect(x, y, m.cz, m.cz);
		for (int i = 0; i < (int) (m.cz / 2); i++) {
			g.setColor(new Color(255, 100, 0, (int) (m.cz) - i * 2));
			g.drawRect(x + i, y + i, m.cz - 2 * i, m.cz - 2 * i);
		}

	}

	private BufferedImage transformImage(BufferedImage i, double scalex,
			double scaley, Facing facing, int centerX, int centerY, float alpha) {

		int w = i.getWidth();
		int h = i.getHeight();

		if (facing == Facing.NORTH || facing == Facing.SOUTH) {
			w = i.getHeight();
			h = i.getWidth();
		}

		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) b.getGraphics();

		switch (facing) {
		case NORTH:

			g2d.rotate(-Math.toRadians(90), m.cz / 2, m.cz / 2);
			g2d.drawImage(i, -i.getWidth(), 0, this);

			break;

		case EAST:
			return i;

		case SOUTH:
			g2d.rotate(Math.toRadians(90), m.cz / 2, m.cz / 2);
			g2d.drawImage(i, 0, 0, this);
			break;
		case WEST:
			g2d.rotate(Math.toRadians(180), m.cz / 2, m.cz / 2);
			g2d.drawImage(i, -i.getWidth(), 0, this);
			break;
		}
		//
		// g2d.setColor(new Color(1, 1, 1, 0.3f));
		// g2d.fillRect(0, 0, b.getWidth(), b.getHeight());
		//
		// int angle = Auxi.getAngleFromFacing(facing);
		//
		// g2d.rotate(-Math.toRadians(angle), m.cz / 2, b.getHeight() - m.cz /
		// 2);
		//
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// alpha));

		return b;
	}

	public void showFinish(boolean f) {
		showFinish = f;
	}
}