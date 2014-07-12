import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public static final Font font = new Font("Georgia", Font.BOLD, 20);

	private JFrame frame;
	private JPanel content;

	public Main() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());

		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem fileNew = new JMenuItem("New");
		fileNew.addActionListener(fileNew());
		fileMenu.add(fileNew);

		JMenuItem fileTitleScreen = new JMenuItem("Title Screen");
		fileTitleScreen.addActionListener(fileTitleScreen());
		fileMenu.add(fileTitleScreen);

		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(fileExit());
		fileMenu.add(fileExit);

		menu.add(fileMenu);
		frame.add(menu, BorderLayout.NORTH);
		content = new JPanel();
		frame.add(content, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		setContent(getTitleScreen());

		frame.setVisible(true);
	}

	public JPanel getTitleScreen() {
		JPanel firstText = new JPanel();
		firstText.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JTextArea text = new JTextArea("The world is at war.\n"
				+ "In it, a youngling is born.\n" + "You.\n" + "Who are you?\n"
				+ "What are you good at? And bad?\n" + "How do you behave?\n"
				+ "What will be your impact in this world?\n" + "\n"
				+ "You Are Supreme is a game about you.\n"
				+ "However, you have no control of your actions.\n"
				+ "You can only input the initial conditions.\n"
				+ "The rest... is chaos.");
		text.setEditable(false);
		text.setOpaque(false);
		text.setHighlighter(null);
		text.setFont(font.deriveFont(14f));
		text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		centerPanel.add(text, BorderLayout.SOUTH);

		firstText.add(centerPanel, BorderLayout.CENTER);

		JPanel titlePanel = new JPanel();

		titlePanel.setLayout(new BorderLayout());
		JLabel title = new JLabel("You Are Supreme", JLabel.CENTER);
		title.setFont(font.deriveFont(40f));
		title.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
		JLabel description = new JLabel("a game by Motivated Designs",
				JLabel.RIGHT);
		description.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 30));
		description.setFont(font.deriveFont(12f));
		titlePanel.add(title, BorderLayout.NORTH);
		titlePanel.add(description, BorderLayout.CENTER);

		firstText.add(titlePanel, BorderLayout.NORTH);
		return firstText;
	}

	public void setContent(JPanel toAdd) {
		content.removeAll();
		content.add(toAdd);
		frame.validate();
		frame.pack();
		frame.repaint();
		frame.setLocationRelativeTo(null);
	}

	private ActionListener fileNew() {
		final Main main = this;
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if (JOptionPane.showConfirmDialog(null, "Are you sure?") ==
				// 0) {
				setContent(new CharacterCreationPanel(main));
				// }
			}
		};
	}

	private ActionListener fileTitleScreen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if (JOptionPane.showConfirmDialog(null, "Are you sure?") ==
				// 0) {
				setContent(getTitleScreen());
				// }
			}
		};
	}

	private ActionListener fileExit() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure?") == 0) {
					System.exit(0);
				}
			}
		};
	}
}
