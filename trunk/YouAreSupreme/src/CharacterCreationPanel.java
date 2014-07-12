import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class CharacterCreationPanel extends JPanel {

	enum Birthplace {
		Heart_Caravan, Rainguard, The_Tribes, Royal_Estate, Convent_of_War;

		public String toString() {
			return " " + name().replace('_', ' ') + " ";
		};

		static Birthplace[] getUnlockedBirthplaces(int unlockedValues) {
			Birthplace[] values = values();
			Birthplace[] result = new Birthplace[unlockedValues];
			for (int i = 0; i < unlockedValues; i++) {
				result[i] = values[i];
			}
			return result;
		}
	}

	public CharacterCreationPanel(final Main main) {
		this(new Ivory(), main);
	}

	private AttributeSelector weak_strong, simple_complex, sloppy_smart,
			pliant_rigid, careful_debonair;
	private Ivory ivory;
	private JLabel ptsLabel;

	public CharacterCreationPanel(final Ivory ivory, final Main main) {

		this.ivory = ivory;

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		ptsLabel = new JLabel("", JLabel.RIGHT);
		topPanel.add(ptsLabel, BorderLayout.WEST);
		JLabel attempt = new JLabel("Attempt #" + ivory.getAttempt(),
				JLabel.RIGHT);
		topPanel.add(attempt, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		JPanel botPanel = new JPanel(new BorderLayout());
		botPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JButton advent = new JButton("Advent");
		advent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ADVENT");
				main.setContent(new Timeline(ivory));
			}
		});
		botPanel.add(advent, BorderLayout.EAST);
		add(botPanel, BorderLayout.SOUTH);

		JPanel characteristics = new JPanel();
		characteristics.setLayout(new BoxLayout(characteristics,
				BoxLayout.Y_AXIS));
		add(characteristics, BorderLayout.CENTER);

		JPanel birthplacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		{
			JList birthplaceList = new JList(
					Birthplace.getUnlockedBirthplaces(ivory
							.getUnlockedBirthplaces()));
			DefaultListCellRenderer renderer = (DefaultListCellRenderer) birthplaceList
					.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			birthplaceList.setVisibleRowCount(1);
			birthplaceList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			birthplaceList
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			birthplaceList.setOpaque(false);
			birthplaceList.setSelectedIndex(0);
			birthplacePanel.add(new JLabel("Birthplace: "));
			birthplacePanel.add(birthplaceList);
		}
		characteristics.add(birthplacePanel);

		weak_strong = new AttributeSelector(ivory.getWeak_strong());
		characteristics.add(new AttributePanel("Weak", "Strong", weak_strong));

		simple_complex = new AttributeSelector(ivory.getSimple_complex());
		characteristics.add(new AttributePanel("Simple", "Complex",
				simple_complex));

		sloppy_smart = new AttributeSelector(ivory.getSloppy_smart());
		characteristics
				.add(new AttributePanel("Sloppy", "Smart", sloppy_smart));

		pliant_rigid = new AttributeSelector(ivory.getPliant_rigid());
		characteristics
				.add(new AttributePanel("Pliant", "Rigid", pliant_rigid));

		careful_debonair = new AttributeSelector(ivory.getCareful_debonair());
		characteristics.add(new AttributePanel("Careful", "Debonair",
				careful_debonair));

		updtAll();
	}

	private void updtAll() {
		repaint();
		ptsLabel.setText(ivory.getPts() + " Unspent Points");
	}

	private class AttributePanel extends JPanel {
		public AttributePanel(String left, String right,
				AttributeSelector selector) {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel leftLabel = new JLabel(left + " ", JLabel.RIGHT);
			leftLabel.setPreferredSize(new Dimension(60, 30));
			add(leftLabel);
			add(selector);
			JLabel rightLabel = new JLabel(" " + right, JLabel.LEFT);
			rightLabel.setPreferredSize(new Dimension(60, 30));
			add(rightLabel);
		}
	}

	private class AttributeSelector extends JPanel {

		final int w = 100, h = 20, maxVal = 10, cellWidth = w / maxVal;
		final Color backColor = new Color(220, 230, 255),
				foreColor = new Color(150, 180, 200);
		private int val;

		public AttributeSelector(int val) {
			this.val = val;
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (e.getX() < w / 2) {
							decrement();
						} else {
							increment();
						}
					}
				}
			});
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(w + 1, h);
		}

		private void increment() {
			if (val < maxVal / 2)
				if (val >= 0) {
					if (ivory.getPts() > 0) {
						ivory.decrementPts();
						val++;
					}
				} else {
					ivory.incrementPts();
					val++;
				}
			updtAll();
		}

		private void decrement() {
			if (val > -maxVal / 2)
				if (val > 0) {
					ivory.incrementPts();
					val--;
				} else {
					if (ivory.getPts() > 0) {
						ivory.decrementPts();
						val--;
					}
				}
			updtAll();
		}

		@Override
		public void paint(Graphics g) {
			g.setColor(backColor);
			g.fillRect(0, 2, w, h - 4);
			g.setColor(foreColor);
			g.fillRect(w / 2, 2, val * cellWidth, h - 4);

			g.setColor(backColor.darker());
			for (int i = 0; i < maxVal / 2; i++) {
				g.drawRect(i * cellWidth, 2, cellWidth, h - 4);
			}
			for (int i = maxVal / 2; i < maxVal; i++) {
				g.drawRect(i * cellWidth, 2, cellWidth, h - 4);
			}
			g.setColor(Color.black);
			g.fillRect(w / 2 - 1, 0, 2, h);
			g.drawString(val + "", 0, h - 4);
		}
	}

}
