package moodish.implementation.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import moodish.interfaces.client.MoodishClient;
import moodish.interfaces.comm.ClientComm;
import moodish.interfaces.comm.ClientSideMessage;

/**
 * 
 * @author grupo32
 * 
 */
public class Client implements MoodishClient {

	public static final String[] MOOD_NAMES = new String[] { "HAPPY", "SAD" };

	private static final int PEOPLE_LISTS_WIDTH = 100,
			PEOPLE_LISTS_HEIGHT = 250, PEOPLE_ELEMENT_HEIGHT = 25;

	/**
	 * Variables needed for the component
	 */
	private ClientComm clientComm;
	private String myUsername;

	/**
	 * Variables needed for graphical interface
	 */
	private JFrame frame;
	private JComboBox<String> moodsComboBox, filterComboBox;
	private JPanel friendsList, usersList;
	private JButton sendButton, connectToServers, disconnectFromServers,
			filterButton, clearFilterButton;
	private boolean filtering = false;
	/**
	 * creation of Friends list and Users list
	 */
	private HashMap<String, Person> persons = new HashMap<String, Person>();

	/**
	 * Starts the client and only returns when (if ever) the client is stopped.
	 * 
	 * @param clientComm
	 *            the clientComm through which the client should communicate
	 *            with the server.
	 */
	@Override
	public void start(ClientComm comm) {
		this.clientComm = comm;

		connectToServers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectToServers();
			}
		});
		disconnectFromServers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				disconnectFromServers();
			}
		});

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clientComm != null && clientComm.isConnected()) {
					clientComm.sendMoodishMessage((String) moodsComboBox
							.getSelectedItem());
				} else {
					JOptionPane.showMessageDialog(null, "Não está Conectado");
				}
			}
		});

		filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseByMood((String) filterComboBox.getSelectedItem());
			}
		});

		clearFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseByMood(null);
			}
		});

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Method that starts the graphical interface
	 */
	public void buildGui() {

		frame = new JFrame("Moodish") {
			private static final long serialVersionUID = 1L;

			@Override
			public void dispose() {
				disconnectFromServers();
				super.dispose();
				System.exit(0);
			}
		};
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);

		// DEFINE PEOPLE LISTS
		usersList = new JPanel();
		usersList.setBorder(BorderFactory.createTitledBorder("Utilizadores"));
		usersList.setLayout(new BoxLayout(usersList, BoxLayout.Y_AXIS));
		usersList.setPreferredSize(new Dimension(PEOPLE_LISTS_WIDTH,
				PEOPLE_LISTS_HEIGHT));

		friendsList = new JPanel();
		friendsList.setBorder(BorderFactory.createTitledBorder("Amigos"));
		friendsList.setLayout(new BoxLayout(friendsList, BoxLayout.Y_AXIS));
		friendsList.setPreferredSize(new Dimension(PEOPLE_LISTS_WIDTH,
				PEOPLE_LISTS_HEIGHT));

		// ADD USER LISTS
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(1, 2));
		peoplePanel.add(usersList);
		peoplePanel.add(friendsList);

		// DEFINE BOTTOM BUTTONS
		moodsComboBox = new JComboBox<String>(MOOD_NAMES);
		sendButton = new JButton("Enviar");
		filterComboBox = new JComboBox<String>(MOOD_NAMES);
		filterButton = new JButton("Filtrar");
		clearFilterButton = new JButton("Todos");

		// ADD BOTTOM BUTTONS
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(moodsComboBox);
		buttonsPanel.add(sendButton);
		buttonsPanel.add(filterComboBox);
		buttonsPanel.add(filterButton);
		buttonsPanel.add(clearFilterButton);

		// DEFINE CONTROL BUTTONS
		connectToServers = new JButton("Ligar aos servidores");
		disconnectFromServers = new JButton("Desligar dos servidores");
		disconnectFromServers.setEnabled(false);

		// ADD CONTROL BUTTONS
		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());
		controls.add(disconnectFromServers);
		controls.add(connectToServers);

		// ///////////////////////////////////////////////////

		// ADD EVERYTHING
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(peoplePanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(controls, BorderLayout.SOUTH);

	}

	private void updateLists() {
		updateUsersList();
		if (filtering) {
			browseByMood((String) filterComboBox.getSelectedItem());
		} else {
			updateFriendsList();
		}
	}

	/**
	 * Method that fills the text area designated for the users
	 */
	private void updateUsersList() {
		usersList.removeAll();
		for (String p : persons.keySet()) {
			if (!persons.get(p).isMyFriend()) {
				usersList.add(Box.createVerticalStrut(5));
				usersList.add(new PeopleElement(persons.get(p), false));
			}
		}
		usersList.add(Box.createVerticalGlue());
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Method that fills the text area designated for the friends
	 */
	private void updateFriendsList() {
		friendsList.removeAll();
		for (String p : persons.keySet()) {
			if (persons.get(p).isMyFriend()) {
				friendsList.add(Box.createVerticalStrut(5));
				friendsList.add(new PeopleElement(persons.get(p), true));
			}
		}
		friendsList.add(Box.createVerticalGlue());
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Method that disconnects the user from the server
	 */
	private void disconnectFromServers() {
		if (clientComm != null && clientComm.isConnected()) {
			clientComm.disconnect();
			persons.clear();
			updateLists();
			frame.setTitle("Moodish - nao conectado");
			JOptionPane.showMessageDialog(null, "Está desconectado!");
			connectToServers.setEnabled(true);
			disconnectFromServers.setEnabled(false);
		}
	}

	/**
	 * Method that connects the user to the server
	 */
	private void connectToServers() {

		try {
			myUsername = JOptionPane
					.showInputDialog("Introduza o seu username.");
			frame.setTitle("Moodish - conectado como " + myUsername);

			if (myUsername != null) {
				clientComm.connect(null, myUsername);
				connectToServers.setEnabled(false);
				disconnectFromServers.setEnabled(true);
				new StartListeningMessages().start();
				persons.clear();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nao foi possivel connectar");
		}
	}

	/**
	 * Method that shows friends by mood. If the input is null it clears the
	 * filter
	 */
	private void browseByMood(String mood) {
		if (mood == null) {
			updateFriendsList();
			filtering = false;
		} else {
			filtering = true;
			friendsList.removeAll();
			for (String p : persons.keySet()) {
				if (persons.get(p).isMyFriend() && //
						persons.get(p).getMood() != null && //
						persons.get(p).getMood().equals(mood)) {
					friendsList.add(Box.createVerticalStrut(5));
					friendsList.add(new PeopleElement(persons.get(p), true));
				}
			}
			friendsList.add(Box.createVerticalGlue());
			frame.revalidate();
			frame.repaint();
		}

	}

	/**
	 * method that connects an user
	 */
	private void dealWithConnectMsg(String sender) {
		Person user = new Person(sender, null);
		if (!sender.equals(myUsername)) {
			addUser(user);
		}
	}

	/**
	 * method that disconnects an user
	 */
	private void dealWithDisconnectMsg(String sender) {
		persons.remove(sender);
		updateLists();
	}

	/**
	 * method responsible for the appearance of the message sent in the window
	 * 
	 * @param msg_
	 */
	private void dealWithMoodishMsg(String sender, String mood) {
		persons.get(sender).setMood(mood);
		updateLists();
	}

	/**
	 * method that adds a friendship
	 */
	private void dealWithFriendshipMsg(String sender) {
		persons.get(sender).addAsFriend();
		updateLists();
	}

	/**
	 * method that deletes a friendship
	 */
	private void dealWithUnfriendshipMsg(String sender) {
		persons.get(sender).removeAsFriend();
		updateLists();
	}

	/**
	 * method that is called when an error appears
	 */
	private void dealWithErrorMsg(String error) {
		if (error.startsWith("Erro 47:")) {
			connectToServers.setEnabled(true);
			disconnectFromServers.setEnabled(false);
		} else if (error.startsWith("Erro 24:") //
				|| error.startsWith("Erro 2:") //
				|| error.startsWith("Erro 87:")) {
			connectToServers.setEnabled(true);
			disconnectFromServers.setEnabled(false);
			clientComm.disconnect();
		}
		JOptionPane.showMessageDialog(null, error, "Error", 1);
	}

	/**
	 * Method that adds a person
	 */
	private void addUser(Person p) {
		persons.put(p.getName(), p);
		updateLists();
	}

	// thread que fica à escuta de mensagens e lida com elas
	private class StartListeningMessages extends Thread {
		@Override
		public void run() {
			while (true) {
				ClientSideMessage msg = clientComm.getNextMessage();
				String sender = msg.getSendersNickname();
				String payload = msg.getPayload();

				if (msg.getType() == ClientSideMessage.Type.CONNECTED) {
					dealWithConnectMsg(payload);
				}

				if (msg.getType() == ClientSideMessage.Type.DISCONNECTED) {
					dealWithDisconnectMsg(payload);
				}

				if (msg.getType() == ClientSideMessage.Type.MOODISH_MESSAGE) {
					dealWithMoodishMsg(sender, payload);
				}

				if (msg.getType() == ClientSideMessage.Type.FRIENDSHIP) {
					dealWithFriendshipMsg(payload);
				}

				if (msg.getType() == ClientSideMessage.Type.UNFRIENDSHIP) {
					dealWithUnfriendshipMsg(payload);
				}

				if (msg.getType() == ClientSideMessage.Type.ERROR) {
					dealWithErrorMsg(payload);
				}
			}
		}
	}

	private class PeopleElement extends JPanel {

		private static final long serialVersionUID = 1L;

		public PeopleElement(final Person person, final boolean friend) {

			setPreferredSize(new Dimension(PEOPLE_LISTS_WIDTH,
					PEOPLE_ELEMENT_HEIGHT));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(Box.createHorizontalStrut(5));
			if (friend)
				add(new JLabel(person.getName() + " - " + person.getMood()));
			else
				add(new JLabel(person.getName()));

			add(Box.createHorizontalGlue());
			add(Box.createHorizontalStrut(5));

			String buttonText = friend ? "-" : "+";
			JButton button = new JButton(buttonText);
			button.setFont(new Font(null, Font.PLAIN, 10));
			button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
			button.setPreferredSize(new Dimension(20, 0));
			button.setBackground(Color.DARK_GRAY);
			button.setForeground(Color.WHITE);

			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (friend) {
						clientComm.unfriendship(person.getName());
					} else {
						clientComm.friendship(person.getName());
					}
				}
			});
			add(button);
			add(Box.createHorizontalStrut(5));
		}

	}
}