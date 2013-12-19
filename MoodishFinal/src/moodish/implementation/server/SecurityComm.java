package moodish.implementation.server;

import java.util.HashMap;
import java.util.LinkedList;

import moodish.interfaces.comm.ServerComm;
import moodish.interfaces.comm.ServerSideMessage;

/**
 * The class SecurityComm provides methods that Override the previous to
 * implement security for the continuous mood messages.
 * <p/>
 * This class implements ServerComm to be able to Override the existing methods.
 * 
 * @author Adriano Fernandes
 * @author Miguel Torres
 * 
 */
public class SecurityComm implements ServerComm {

	/**
	 * @serialField
	 *                  MAX_MOOD_CHANGES the field that indicates the maximum
	 *                  number of times that a client can change it's mood.
	 */
	public static final int MAX_MOOD_CHANGES = 2;
	/**
	 * @serialField
	 *                  MOOD_CHANGE_SPAN the field that indicates the time, in
	 *                  seconds, which you can send the maximum number of mood
	 *                  changes.
	 */
	public static final int MOOD_CHANGE_SPAN = 60;
	/**
	 * @serialField
	 *                  BAN_TIME the field that indicates the time, in seconds,
	 *                  which the user has to wait before connecting, after
	 *                  being banned.
	 */
	public static final int BAN_TIME = 30;

	private ServerComm comm;
	private LinkedList<ServerSideMessage> serverMessages;
	private HashMap<String, Client> clients;

	/**
	 * Method that initializes the lists for server and client messages, and the
	 * list of clients.
	 */
	@Override
	public void start() {
		serverMessages = new LinkedList<ServerSideMessage>();
		clients = new HashMap<String, Client>();
		comm = new ServerCommmunicator();
		comm.start();
	}

	/**
	 * Get the next message from the server messages list.
	 * 
	 * @return the message from the top of the list.
	 */
	@Override
	public ServerSideMessage getNextMessage() {
		return serverMessages.pop();
	}

	/**
	 * Verifies the ServerComm for a new message and filters the messages
	 * received from a banned user, so the user can't connect for 30 seconds
	 * after being banned.
	 * <p/>
	 * If the message receive comes from an unbanned user, then the message is
	 * add to the serverMessages list.
	 * 
	 * @return true if the serverMessages list isn't empty.
	 */
	@Override
	public boolean hasNextMessage() {

		while (comm.hasNextMessage()) {
			ServerSideMessage next = comm.getNextMessage();
			String from = next.getClientNickname();

			if (next.getType().equals(ServerSideMessage.Type.CLIENT_CONNECTED)
					&& isBanned(from)) {
				// comm.sendMoodishMessage("Moodish Server", from,
				// "You are still banned.");
				comm.sendError(from, "You are still banned.");
			} else {
				serverMessages.add(next);
			}
		}

		return !serverMessages.isEmpty();
	}

	/**
	 * Method that sends a moodish message, this method provides the ways of
	 * filtering the number of mood messages sent by an user.
	 * <p/>
	 * If the user sends two messages within the time span of 60 seconds, the
	 * server will send a warning to him/her.
	 * <p/>
	 * If the user sends more than two moodish messages within 60 seconds, the
	 * user will be temporarily banned for 30 seconds.
	 */
	@Override
	public void sendMoodishMessage(String from, String to, String msgString) {

		System.out.println("SECURITY CHECK: " + msgString);

		Client client = clients.get(from);
		if (client == null) {
			client = new Client();
			clients.put(from, client);
		}
		int nrMsgs = client.getNumberOfMessages();
		client.updateMessageTimes();

		if (nrMsgs <= MAX_MOOD_CHANGES) {
			comm.sendMoodishMessage(from, to, msgString);

			if (nrMsgs == MAX_MOOD_CHANGES) {
				// SEND WARNING TOO
				String msg = "Cuidado, já mandaste " + MAX_MOOD_CHANGES
						+ " mensagens  nos ultimos 60 segundos.";
				// comm.sendMoodishMessage("Moodish Server", to, msg);
				comm.sendError(from, msg);
			}
		} else {
			// DISCONNECT
			ServerMessage disconnectMessage = new ServerMessage(
					ServerSideMessage.Type.CLIENT_DISCONNECTED, from);
			serverMessages.add(disconnectMessage);
			client.ban();
			comm.sendError(from,
					"Foste banido temporáriamente por enviar mais de "
							+ MAX_MOOD_CHANGES + " mensagens em "
							+ MOOD_CHANGE_SPAN + " segundos.");
		}
	}

	@Override
	public void sendNewFriendship(String fromNickname, String toNickName) {
		comm.sendNewFriendship(fromNickname, toNickName);
	}

	@Override
	public void sendNewUnfriendship(String toNickname, String unfriendship) {
		comm.sendNewUnfriendship(toNickname, unfriendship);
	}

	@Override
	public void sendError(String toNickname, String error) {
		comm.sendError(toNickname, error);
	}

	@Override
	public void sendClientConnected(String toNickname, String connectedNickname) {
		comm.sendClientConnected(toNickname, connectedNickname);
	}

	@Override
	public void sendClientDisconnected(String toNickname,
			String disconnectedNickname) {
		comm.sendClientDisconnected(toNickname, disconnectedNickname);
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		return comm.clientIsConnected(nickname);
	}

	@Override
	public void disconnectClient(String nickname) {
		comm.disconnectClient(nickname);
	}

	/**
	 * Class ServerMessage that implements ServerSideMessage.
	 * <p/>
	 * This class is used to communicate with the server.
	 * 
	 * @author Adriano Fernandes
	 * @author Miguel Fernandes
	 * 
	 */
	class ServerMessage implements ServerSideMessage {

		Type type;
		String nickname;

		/**
		 * Constructor of the class ServerMessage
		 * 
		 * @param type
		 *            argument that specifies the type of message.
		 * @param nick
		 *            argument that specifies the nickname of the user.
		 */
		public ServerMessage(Type type, String nick) {
			this.type = type;
			this.nickname = nick;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public String getPayload() {
			return null;
		}

		@Override
		public String getClientNickname() {
			return nickname;
		}
	}

	/**
	 * Class Client.
	 * <p/>
	 * This class constructs a new client.
	 * 
	 * @author Adriano Fernandes
	 * @author Miguel Fernandes
	 * 
	 */
	private class Client {

		private LinkedList<Long> messageTimes;
		private boolean banned;
		private long banTime;

		/**
		 * Constructor of the class client.
		 */
		public Client() {
			messageTimes = new LinkedList<Long>();
			banned = false;
		}

		/**
		 * Method that returns the number of mood messages sent by the user
		 * within the time span.
		 * 
		 * @return the number of mood messages sent within the time span.
		 */
		public int getNumberOfMessages() {
			int number = 0;
			for (Long time : messageTimes) {
				if (getTimeDifferenceInSeconds(time) <= SecurityComm.MOOD_CHANGE_SPAN)
					number++;
			}
			return number;
		}

		/**
		 * Method that changes the value of the attribute banned to true and
		 * saves the time when the user is banned.
		 */
		public void ban() {
			banned = true;
			banTime = System.currentTimeMillis();
		}

		/**
		 * Method that updates the status of the client (banned or not banned).
		 */
		public void updateBannedStatus() {
			if (banned) {
				if (toSeconds(System.currentTimeMillis()) - toSeconds(banTime) > BAN_TIME) {
					banned = false;
				}
			}
		}

		/**
		 * Method that updates the list of times from messages.
		 */
		void updateMessageTimes() {
			if (banned) {
				if (toSeconds(System.currentTimeMillis()) - toSeconds(banTime) > BAN_TIME) {
					banned = false;
				}
			}

			if (messageTimes.size() > SecurityComm.MAX_MOOD_CHANGES) {
				messageTimes.removeLast();
			}
			messageTimes.addFirst(System.currentTimeMillis());
		}

		/**
		 * Method that returns the difference between the current time and the
		 * argument time.
		 * 
		 * @param time
		 *            argument time in milliseconds.
		 * @return the difference between current time and argument time in
		 *         seconds.
		 */
		private int getTimeDifferenceInSeconds(long time) {
			Long now = System.currentTimeMillis();
			return toSeconds(now - time);
		}

	}

	/**
	 * Method that converts a variable of time in milliseconds to seconds.
	 * 
	 * @param time
	 *            parameter of time in milliseconds.
	 * @return time in seconds.
	 */
	private static int toSeconds(long time) {
		return (int) (time / 1000);
	}

	/**
	 * Method that returns true if the client is banned and false if not.
	 * 
	 * @param the
	 *            nickname of the user.
	 * @return the value of the attribute banned of the class Client.
	 */
	private boolean isBanned(String nick) {
		if (clients.containsKey(nick)) {
			clients.get(nick).updateBannedStatus();
			return clients.get(nick).banned;
		}
		return false;
	}
}
