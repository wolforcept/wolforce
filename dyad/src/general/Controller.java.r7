package general;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;

import objects.Player;

import ui.Taylor;

public class Controller {

	private JFrame frame;
	private Taylor taylor;
	private Ivory ivory;

	public Controller() throws IOException {

		frame = new JFrame("Dyad");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Ivory.SIZE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		ivory = new Ivory(1);

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					ivory.toggleSelected();
				}
			}
		});

		taylor = new Taylor(ivory);

		taylor.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					movePlayer(
							Math.min(Ivory.WIDTH-1, e.getX() / Ivory.CELL_SIZE),
							Math.min(Ivory.HEIGHT-1, e.getY() / Ivory.CELL_SIZE));
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});

		frame.add(taylor);
		taylor.requestFocus();

		new Thread() {
			public void run() {
				try {
					while (true) {
						setName("Updater");
						taylor.repaint();
						sleep(1000 / Ivory.REFRESH_FREQUENCY);
					}
				} catch (InterruptedException e) {
					System.err
							.println("Updater interrupted\nController.Thread() {...}.run()");
					System.exit(0);
					System.gc();
				}

			};
		}.start();

		frame.setVisible(true);

	}

	private void movePlayer(int xx, int yy) {
		Player selected;
		if (ivory.getSelected()) {
			selected = (Player) ivory.getGameObject("magus");
		} else {
			selected = (Player) ivory.getGameObject("champion");
		}
		int x = selected.getX();
		int y = selected.getY();
		ivory.move(x, y, xx, yy);
	}
}
