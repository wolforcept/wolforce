package general;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JFrame;

import auxis.Auxi;

import objects.Player;
import ui.Taylor;
import ui.TaylorData;

public class Controller {

	private JFrame frame;
	private Taylor taylor;
	private Ivory ivory;

	public Controller() throws IOException {

		TaylorData.init();
		
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
				
				if(ivory.getTargetingSpell() != null){
					ivory.using(ivory.getTargetingSpell());
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					ivory.toggleSelected();
				}

				switch (e.getKeyCode()) {

				case KeyEvent.VK_W:
					movePlayer(0, -1);
					break;

				case KeyEvent.VK_S:
					movePlayer(0, 1);
					break;

				case KeyEvent.VK_A:
					movePlayer(-1, 0);
					break;

				case KeyEvent.VK_D:
					movePlayer(1, 0);
					break;

				}
			}
		});

		taylor = new Taylor(ivory);

		taylor.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				ivory.setMouse(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
		taylor.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (ivory.using()) {
					ivory.use();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					for (Button b : ivory.getButtons()) {
						if (Auxi.collides(e, b)) {
							ivory.using(b.getSpell());
						}
					}
				}
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
						ivory.updateSpells();
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

		ivory.move(x, y, x + xx, y + yy);
	}
}
