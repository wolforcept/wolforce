package moodish.implementation.server;

import java.util.LinkedList;
import java.util.List;

import moodish.interfaces.comm.ServerComm;
import moodish.interfaces.comm.ServerSideMessage;
import moodish.interfaces.server.MoodishServer;

public class Server implements MoodishServer {

	private List<ServerClient> clients = new LinkedList<ServerClient>();
	private boolean serverStopped = false;

	@Override
	public void start(ServerComm serverComm) {
		serverComm.start();

		while (!serverStopped) {

			while (serverComm.hasNextMessage()) {
				ServerSideMessage message = serverComm.getNextMessage();
				switch (message.getType()) {
				case CLIENT_CONNECTED:
					connectClient(serverComm, message);
					break;
				case CLIENT_DISCONNECTED:
					disconnectClient(serverComm, message.getClientNickname());
					break;
				case FRIENDSHIP:
					friendship(serverComm, message);
					break;
				case UNFRIENDSHIP:
					unfriendship(serverComm, message);
					break;
				case MOODISH_MESSAGE:
					sendMoodishMessage(serverComm, message);
					break;

				}

			}
		}
	}

	/**
	 * <li>The method sends a message to all connected clients informing them
	 * that a new client is online.
	 * 
	 * @parameter ServerComm receives the ServerComm variable from start()
	 *            method
	 * @parameter ServerSideMessage receives a message got by the ServerComm
	 *            parameter
	 */
	public void connectClient(ServerComm servercomm, ServerSideMessage message) {
		boolean alreadyExists = false;

		for (ServerClient client : clients) {
			if (client.getNickName().equals(message.getClientNickname())) {
				alreadyExists = true;
				break;
			}
		}

		if (!alreadyExists) {
			clients.add(new ServerClient(message.getClientNickname(), null));
			for (int i = 0; i < clients.size(); i++) {
				if (!clients.get(i).equals(message.getClientNickname())) {
					servercomm.sendClientConnected(
							clients.get(i).getNickName(),
							message.getClientNickname());
					servercomm.sendClientConnected(message.getClientNickname(),
							clients.get(i).getNickName());
				}
			}
		} else {
			servercomm.sendError(message.getClientNickname(),
					"Erro 47: O username já existe!");
		}
	}

	/**
	 * 
	 * <li>This method informs a client resigned and updates the lists of
	 * friends removing that client.
	 * 
	 * @parameter ServerComm receives the ServerComm variable from start()
	 *            method
	 * @parameter ServerSideMessage receives a message got by the ServerComm
	 *            parameter
	 */
	public void disconnectClient(ServerComm serverComm, String nickname) {
		for (int i = 0; i < clients.size(); i++) {
			if (!clients.get(i).getNickName().equals(nickname)) {
				serverComm.sendClientDisconnected(clients.get(i).getNickName(),
						nickname);
			} else {
				clients.remove(i);
			}
		}
		serverComm.disconnectClient(nickname);
	}

	/**
	 * 
	 * <li>The method inform a client that another client is now a friend. It
	 * calls "addFriend(String)" and "isFriend(String)" from the ServerClient
	 * class .
	 * 
	 * @parameter ServerComm receives the ServerComm variable from start()
	 *            method
	 * @parameter ServerSideMessage receives a message got by the ServerComm
	 *            parameter
	 */
	public void friendship(ServerComm serverComm, ServerSideMessage message) {
		ServerClient client = null;
		ServerClient friend = null;

		for (ServerClient c : clients) {
			if (c.getNickName().equals(message.getClientNickname())) {
				client = c;
			}
			if (c.getNickName().equals(message.getPayload())) {
				friend = c;
			}

			if (client != null && friend != null) {
				break;
			}
		}

		if (client != null && friend != null) {
			if (!client.isFriend(friend.getNickName())
					&& !friend.isFriend(client.getNickName())) {
				client.addFriend(friend);
				friend.addFriend(client);

				// ADD EACHOTHER AS FRIENDS
				serverComm.sendNewFriendship(client.getNickName(),
						friend.getNickName());
				serverComm.sendNewFriendship(friend.getNickName(),
						client.getNickName());

				// LET THEM KNOW OF EACHOTHERS MOODS
				serverComm.sendMoodishMessage(client.getNickName(),
						friend.getNickName(), client.getMood());
				serverComm.sendMoodishMessage(friend.getNickName(),
						client.getNickName(), friend.getMood());

			} else {
				serverComm.sendError(message.getClientNickname(),
						"Erro 37: Este utilizador já é seu amigo!");
			}
		}
	}

	/**
	 * <li>The method inform a client that another client is no longer a friend.
	 * It calls "isFriend(String)" and "removeFriend(String)" from the
	 * ServerClient class .
	 * 
	 * @parameter ServerComm receives the ServerComm variable from start()
	 *            method
	 * @parameter ServerSideMessage receives a message got by the ServerComm
	 *            parameter
	 */
	public void unfriendship(ServerComm serverComm, ServerSideMessage message) {
		ServerClient client = null;
		ServerClient friend = null;

		for (ServerClient c : clients) {
			if (c.getNickName().equals(message.getClientNickname())) {
				client = c;
			}
			if (c.getNickName().equals(message.getPayload())) {
				friend = c;
			}

			if (client != null && friend != null) {
				break;
			}
		}

		if (client != null && friend != null) {
			if (client.isFriend(friend.getNickName())
					&& friend.isFriend(client.getNickName())) {
				client.removeFriend(friend);
				friend.removeFriend(client);

				serverComm.sendNewUnfriendship(message.getClientNickname(),
						message.getPayload());
				serverComm.sendNewUnfriendship(message.getPayload(),
						message.getClientNickname());
			} else {
				serverComm.sendError(message.getClientNickname(),
						"Erro 52: Este utilizador não é seu amigo!");
			}
		}
	}

	/**
	 * <li>This method inform a client that another from his friends list had
	 * sent the server a new message.
	 * 
	 * @parameter ServerComm receives the ServerComm variable from start()
	 *            method
	 * @parameter ServerSideMessage receives a message got by the ServerComm
	 *            parameter
	 */
	public void sendMoodishMessage(ServerComm serverComm,
			ServerSideMessage message) {
		ServerClient client = null;

		for (ServerClient c : clients) {
			if (c.getNickName().equals(message.getClientNickname())) {
				client = c;
				break;
			}
		}

		if (client != null) {
			for (ServerClient f : client.getFriends()) {
				serverComm.sendMoodishMessage(client.getNickName(),
						f.getNickName(), message.getPayload());
			}
			client.setMood(message.getPayload());
		}

	}

}
