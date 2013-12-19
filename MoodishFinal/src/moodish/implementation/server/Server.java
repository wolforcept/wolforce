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
					disconnectClient(serverComm, message);
					break;
				case FRIENDSHIP:
					friendship(serverComm, message);
					break;
				case UNFREINDSHIP:
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
		clients.add(new ServerClient(message.getClientNickname(), null));
		for (int i = 0; i < clients.size(); i++) {
			if (!clients.get(i).equals(message.getClientNickname())) {
				servercomm.sendClientConnected(clients.get(i).getNickName(),
						message.getClientNickname());
				servercomm.sendClientConnected(message.getClientNickname(),
						clients.get(i).getNickName());
			}
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
	public void disconnectClient(ServerComm servercomm,
			ServerSideMessage message) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).isFriend(message.getClientNickname())) {
				clients.get(i).removeFriend(message.getClientNickname());
			}
		}
		for (int i = 0; i < clients.size(); i++) {
			if (!clients.get(i).getNickName()
					.equals(message.getClientNickname())) {
				servercomm.sendClientDisconnected(clients.get(i).getNickName(),
						message.getClientNickname());
			} else {
				clients.remove(i);
			}
		}
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
		for (ServerClient client : clients) {
			if (client.getNickName().equals(message.getClientNickname())) {
				List<String> friends = client.getFriends();
				boolean foundFriend = false;
				int f = 0;

				while (!foundFriend && f != friends.size()) {
					if (friends.get(f).equals(message.getPayload())) {
						foundFriend = true;
					}
					f++;
				}

				if(foundFriend){
					
				}else{
					
				}
			}
		}

		// for (int i = 0; i < clients.size(); i++) {
		//
		// if (clients.get(i).getNickName().equals(message.getPayload())) {
		// // TODO
		//
		// }
		// if (clients.get(i).isFriend(message.getPayload())) {
		// serverComm.sendError(message.getClientNickname(),
		// "This User is Already Your Friend");
		// } else {
		// clients.get(i).addFriend(message.getClientNickname());
		// serverComm.sendNewFriendship(message.getClientNickname(),
		// message.getPayload());
		// }
		// }
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
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getNickName()
					.equals(message.getClientNickname())) {
				if (clients.get(i).isFriend(message.getPayload())) {
					serverComm.sendNewUnfriendship(message.getClientNickname(),
							message.getPayload());
					clients.get(i).removeFriend(message.getPayload());
				} else {
					serverComm.sendError(message.getClientNickname(),
							"This User it's not your friend");
				}
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
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getNickName()
					.equals(message.getClientNickname())) {
				for (String name : clients.get(i).getFriends()) {
					System.out.println("Server.sendMoodishMessage()");
					serverComm.sendMoodishMessage(message.getClientNickname(),
							name, message.getPayload());
				}
			}
		}
	}

}
