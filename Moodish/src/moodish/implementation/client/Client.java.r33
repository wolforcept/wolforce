package moodish.implementation.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import moodish.interfaces.client.MoodishClient;
import moodish.interfaces.comm.ClientComm;
import moodish.interfaces.comm.ClientSideMessage;

/**
 * 
 * @author grupo32
 * 
 */
public class Client implements MoodishClient {

	static final String[] MOOD_NAMES = new String[] { "HAPPY", "SAD" };
	/**
	 * Variables needed for the component
	 */
	private UserDummy userDummy;
	// private ClientSideMessage msg;
	private ClientComm clientComm;
	private String myUsername;
	// private StartListeningMessages startListeningMessages = new
	// StartListeningMessages();

	/**
	 * Variables needed for graphical interface
	 */
	private JTextArea usersArea, msgArea, friendsArea, browseResult;
	private JTextField browse;
	private JComboBox<String> moods;
	private JLabel msgs, friendsLabel, usersLabel;
	private JFrame frame;
	private JPanel msgsPanel, labelPanel, eastPanel, peoplePanel, usersPanel,
			friendsPanel, browsePanel, browseResultsPanel, browseButtonsPanel;
	private JButton sendMsg, connectToServers, disconnectFromServers;

	/**
	 * creation of Friends list and Users list
	 */
	private LinkedList<UserDummy> usersList = new LinkedList<UserDummy>();
	private LinkedList<UserDummy> friendList = new LinkedList<UserDummy>();

	/**
	 * Method that starts the graphical interface
	 */
	public void buildGui() {

		frame = new JFrame("Moodish");
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);

		final Image backgroundTextArea = Toolkit.getDefaultToolkit()
				.createImage("smile-1.jpg");
		msgArea = new JTextArea(100, 100) {
			private static final long serialVersionUID = 1L;

			{
				setOpaque(false);
				setLineWrap(true);
				setWrapStyleWord(true);
				setEditable(false);
			}

			public void paintComponent(Graphics g) {
				g.drawImage(backgroundTextArea, 0, 0, 500, 600, this);
				super.paintComponent(g);
			}
		};

		msgsPanel = new JPanel();
		msgsPanel.setPreferredSize(new Dimension(500, 600));
		msgsPanel.setLayout(new BorderLayout());
		msgsPanel.add(msgArea, BorderLayout.CENTER);

		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());
		labelPanel.setPreferredSize(new Dimension(50, 40));

		msgs = new JLabel("Digite uma mensagem:");
		labelPanel.add(msgs);

		moods = new JComboBox<String>(MOOD_NAMES);
		labelPanel.add(moods);

		sendMsg = new JButton("Enviar");

		labelPanel.add(sendMsg);
		msgsPanel.add(labelPanel, BorderLayout.SOUTH);
		frame.add(msgsPanel, BorderLayout.CENTER);

		sendMsg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (clientComm != null && clientComm.isConnected()) {

					String msgSend = (String) moods.getSelectedItem();

					// if (msgSend.length() > 7
					// && msgSend.startsWith("/friend ")) {
					if (msgSend.startsWith("/friend ")) {
						clientComm.friendship(msgSend.substring(8));
						// } else if (msgSend.length() > 9
						// && msgSend.startsWith("/unfriend ")) {
					} else if (msgSend.startsWith("/unfriend ")) {
						clientComm.unfriendship(msgSend.substring(10));
					} else if (msgSend.startsWith("/mood ")) {
						clientComm.sendMoodishMessage(msgSend.substring(6));
					}
					// else {
					// msgArea.append(myUsername + ": " + msgSend + "\n");
					// clientComm.sendMoodishMessage(msgSend);
					// }

				} else {
					JOptionPane.showMessageDialog(null,
							"N�o est� Conectado");
				}
			}
		});

		eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setPreferredSize(new Dimension(500, 200));

		peoplePanel = new JPanel();
		peoplePanel.setPreferredSize(new Dimension(250, 200));
		peoplePanel.setLayout(new GridLayout(1, 2));

		usersPanel = new JPanel();
		usersPanel.setPreferredSize(new Dimension(100, 200));

		friendsPanel = new JPanel();
		friendsPanel.setPreferredSize(new Dimension(100, 200));

		usersLabel = new JLabel("Utilizadores");
		usersPanel.add(usersLabel);

		usersArea = new JTextArea(20, 20);
		usersArea.setEditable(false);

		friendsLabel = new JLabel("Amigos");
		friendsPanel.add(friendsLabel);

		friendsArea = new JTextArea(20, 20);
		friendsArea.setEditable(false);

		JScrollPane usersAreaPane = new JScrollPane(usersArea);
		JScrollPane friendsPane = new JScrollPane(friendsArea);

		usersPanel.add(usersAreaPane);
		friendsPanel.add(friendsPane);

		peoplePanel.add(usersPanel);
		peoplePanel.add(friendsPanel);

		eastPanel.add(peoplePanel, BorderLayout.CENTER);

		browsePanel = new JPanel();
		browsePanel.setPreferredSize(new Dimension(450, 350));

		browseButtonsPanel = new JPanel();
		browseButtonsPanel.setPreferredSize(new Dimension(200, 60));
		browseButtonsPanel.setLayout(new FlowLayout());

		browseResultsPanel = new JPanel();
		browseResultsPanel.setPreferredSize(new Dimension(400, 250));

		browse = new JTextField(15);
		browse.setEditable(true);
		browse.setText("Pesquise aqui por um mood.");
		JButton browseButton = new JButton("Pesquisar");

		browseButtonsPanel.add(browse);
		browseButtonsPanel.add(browseButton);

		browseResult = new JTextArea(10, 30);
		browseResult.setEditable(false);

		JScrollPane moodsArea = new JScrollPane(browseResult);

		browseResultsPanel.add(moodsArea);

		browsePanel.add(browseButtonsPanel);
		browsePanel.add(browseResultsPanel);
		eastPanel.add(browsePanel, BorderLayout.SOUTH);

		frame.add(eastPanel, BorderLayout.EAST);

		connectToServers = new JButton("Ligar aos servidores");
		disconnectFromServers = new JButton("Desligar dos servidores");
		disconnectFromServers.setEnabled(false);

		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseByMood(browse.getText());
			}
		});
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

		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());

		controls.add(disconnectFromServers);
		controls.add(connectToServers);

		frame.add(controls, BorderLayout.SOUTH);
		frame.pack();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Method that disconnects the user from the server
	 */
	public void disconnectFromServers() {
		if (clientComm != null && clientComm.isConnected()) {
			clientComm.disconnect();
			JOptionPane.showMessageDialog(null, "Est� desconectado!");
			connectToServers.setEnabled(true);
			disconnectFromServers.setEnabled(false);
		}
	}

	/**
	 * Method that connects the user to the server
	 */
	public void connectToServers() {
		myUsername = JOptionPane.showInputDialog("Introduza o seu username.");
		System.out.println(myUsername);

		clientComm = new ClientCommmunicator();
		start(clientComm);
		new StartListeningMessages().start();
	}

	/**
	 * Method that return the users list
	 * 
	 * @return usersList
	 */
	public LinkedList<UserDummy> getUsersList() {
		return usersList;
	}

	/**
	 * Method that return the friends list
	 * 
	 * @return friendList
	 */
	public LinkedList<UserDummy> getFriendList() {
		return friendList;
	}

	/**
	 * Method that shows friends by mood
	 */
	public void browseByMood(String mood) {
		for (UserDummy friend : friendList) {
			if (mood.equals(friend.getMood())) {
				browseResult.append(friend.getMood().toString());
			}
		}
	}

	/**
	 * Starts the client and only returns when (if ever) the client is stopped.
	 * 
	 * @param clientComm
	 *            the clientComm through which the client should communicate
	 *            with the server.
	 */
	@Override
	public void start(ClientComm clientComm) {
		try {
			clientComm.connect(null, myUsername);
			connectToServers.setEnabled(false);
			disconnectFromServers.setEnabled(true);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Could not connect");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect");
		}
	}

	/**
	 * Method that fills the text area designated for the users
	 */
	public void fillUsersList() {
		usersArea.setText("");
		for (UserDummy username : usersList) {
			usersArea.append(username.getName() + "\n");
		}
	}

	/**
	 * Method that fills the text area designated for the friends
	 */
	public void fillFriendsList() {
		friendsArea.setText("");
		for (UserDummy username : friendList) {
			friendsArea.append(username.getName() + " - " + username.getMood()
					+ "\n");

		}
	}

	/**
	 * method that connects an user
	 */
	public void dealWithConnectMsg(String sender, String mood) {
		userDummy = new UserDummy(sender, mood);
		if (!sender.equals(myUsername)) {
			addUser(userDummy);
		}

	}

	/**
	 * method that disconnects an user
	 */
	public void dealWithDisconnectMsg(String sender) {

		for (UserDummy user : usersList) {
			if (user.getName().equals(sender) && !sender.equals(myUsername)) {
				removeUser(user);
			}
		}

	}

	/**
	 * method responsible for the appearance of the message sent in the window
	 * 
	 * @param msg_
	 */
	public void dealWithMoodishMsg(ClientSideMessage msg) {
		for (UserDummy friend : friendList) {
			if (friend.getName().equals(msg.getSendersNickname())) {
				friend.setMood(msg.getPayload());
			}
		}
		// msgArea.append(msg_.getSendersNickname() + ": " + msg_.getPayload()
		// + "\n");
	}

	/**
	 * method that adds a friendship
	 */
	public void dealWithFriendshipMsg(String sender) {
		for (UserDummy user : usersList) {
			if (user.getName().equals(sender) && !sender.equals(myUsername)) {
				addFriendship(user);
			}
		}
	}

	/**
	 * method that deletes a friendship
	 */
	public void dealWithUnfriendshipMsg(String sender) {
		for (UserDummy user : friendList) {
			if (user.getName().equals(sender) && !sender.equals(myUsername)) {
				removeFriendship(user);

			}
		}
	}

	/**
	 * method that is called when an error appears
	 */
	public void dealWithErrorMsg(String error) {
		if (error.startsWith("Erro 24") || error.startsWith("Erro 17")) {
			connectToServers.setEnabled(true);
			disconnectFromServers.setEnabled(false);
		}
		JOptionPane.showMessageDialog(null, error, "Error", 1);
	}

	/**
	 * Method that removes a user
	 */
	public void removeUser(UserDummy user) {
		usersList.remove(user);
		fillUsersList();

	}

	/**
	 * Method that adds a user
	 */
	public void addUser(UserDummy user) {
		userDummy = user;
		usersList.add(userDummy);
		usersArea.append(user.getName() + "\n");
		fillUsersList();

	}

	/**
	 * Method that adds a friend
	 */
	public void addFriendship(UserDummy user) {
		friendList.add(user);
		fillFriendsList();
		usersList.remove(user);

		fillUsersList();
	}

	/**
	 * Method that removes a friend
	 */
	public void removeFriendship(UserDummy user) {
		friendList.remove(user);
		usersList.add(user);
		usersArea.append(user.getName() + "\n");
		fillFriendsList();
	}

	// thread que fica � escuta de mensagens e lida com elas
	public class StartListeningMessages extends Thread {
		@Override
		public void run() {
			while (true) {
				ClientSideMessage msg = clientComm.getNextMessage();
				String sender = msg.getPayload();

				if (msg.getType() == ClientSideMessage.Type.CONNECTED) {
					dealWithConnectMsg(sender, msg.getPayload());
				}

				if (msg.getType() == ClientSideMessage.Type.DISCONNECTED) {
					dealWithDisconnectMsg(sender);
				}

				if (msg.getType() == ClientSideMessage.Type.MOODISH_MESSAGE) {
					dealWithMoodishMsg(msg);
				}

				if (msg.getType() == ClientSideMessage.Type.FRIENDSHIP) {
					dealWithFriendshipMsg(sender);
				}

				if (msg.getType() == ClientSideMessage.Type.UNFRIENDSHIP) {
					dealWithUnfriendshipMsg(sender);
				}

				if (msg.getType() == ClientSideMessage.Type.ERROR) {
					dealWithErrorMsg(msg.getPayload());
				}
			}
		}

	}
}