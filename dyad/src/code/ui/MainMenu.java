package code.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import code.general.Controller;
import code.general.Level;

public class MainMenu {
	class LevelEntry {

		private String name;
		private Level level;

		public LevelEntry(String name, Level level) {
			this.name = name;
			this.level = level;
		}

		public Level getLevel() {
			return level;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	JFrame frame;
	JList<LevelEntry> levelList;

	public MainMenu() throws IOException {

		frame = new JFrame("Dyad");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel image = new JLabel(new ImageIcon(ImageIO.read(getClass()
				.getClassLoader().getResource("resources/initial_image.png"))));

		frame.add(image, BorderLayout.CENTER);

		JPanel listAndStartButtonPanel = new JPanel();
		listAndStartButtonPanel.setLayout(new BorderLayout());

		LevelEntry[] levels = new LevelEntry[Level.getNumberOfLevels()];
		for (int i = 0; i < levels.length; i++) {
			levels[i] = new LevelEntry("Level " + (i + 1),
					Level.getLevel(i + 1));
		}
		levelList = new JList<>(levels);
		levelList.setSelectedIndex(0);
		levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAndStartButtonPanel.add(levelList, BorderLayout.CENTER);

		JButton startButton = new JButton("Start");
		listAndStartButtonPanel.add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Controller(levelList.getSelectedValue().getLevel());
					frame.setVisible(false);
					frame.dispose();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		frame.add(listAndStartButtonPanel, BorderLayout.EAST);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
