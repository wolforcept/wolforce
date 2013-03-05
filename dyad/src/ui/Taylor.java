package ui;

import general.Auxi;
import general.Ivory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JPanel;

import objects.GameObject;
import objects.Magus;

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

			// g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.01f));
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

			int cellSize = Ivory.CELL_SIZE;
			GameObject[][] field = ivory.getField();

			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[0].length; j++) {

					GameObject obj = field[i][j];
					if (obj != null) {
						obj.incrementImage();
						String s = obj.getClass().getSimpleName().toString()
								.toLowerCase();
						g.drawImage(
								data.getImages(s).getImage(
										obj.getCurrentImage()), i * cellSize, j
										* cellSize, this);

						if (obj instanceof Magus) {

							Point m = ivory.getMouse();

							double dis = Auxi.point_distance(
							//
									i * cellSize + cellSize / 2,//
									j * cellSize + cellSize / 2,//
									m.getX(), m.getY());

							if (dis < cellSize) {
								g.drawLine((int) m.getX(), (int) m.getY(), i
										* cellSize + cellSize / 2,//
										j * cellSize + cellSize / 2);
							}

						}

					}
					g.setColor(new Color(1f, 1f, 1f, 0.2f));

					g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);

				}
			}
		} catch (Exception e) {
			System.err.println("UI Update Failed");
			e.printStackTrace();
		}
	}
}
