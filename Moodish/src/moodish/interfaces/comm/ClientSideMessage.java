package moodish.interfaces.comm;

/**
 * Messages sent from the server to clients. A message contains a type (enum), 
 * and a sender (String). 
 * <p>
 * <b>Important:</b> <br>
 * A <i>message</i> refers to an object sent from the server to a client.<br>
 * A <i>Moodish message</i> refers to an object sent from a client to his or her friends<br>
 * 
 * <p>
 * When a Moodish message is being sent from one user to his/her friends, the sender
 * is the nickname of the client. For all other messages the sender's nickname should be {@link #SERVER}. 
 * <p>
 * 
 * 
 * @author alc
 * @version 1 
 */
public interface ClientSideMessage {
	
	public static final String SERVER = "server";
	
	/**
	 * Message types.
	 */
	public enum Type { 
		/**
		 * A new client has connected. Message payload = nickname of newly connected client.

		 */
		CONNECTED, 
		/**
		 * A client has disconnected. Message payload = nickname of disconnecting client.
		 */
		DISCONNECTED, 

		/**
		 * A new Moodish message from a friend client. Message payload = Moodish message.

		 */
		MOODISH_MESSAGE, 

		/**
		 * A client received a new friend. Message payload = nickname of friend.

		 */
		FRIENDSHIP, 
		
		/**
		 * A friend decided to not be friend anymore. Message payload = nickname of friend.
		 */
		UNFRIENDSHIP, 

		/**
		 * An error occurred. 
		 */
		ERROR };
	/**
		 * Gets the payload of the message.
		 * 
		 * @return payload.
		 */
		
		public String getPayload();

	/**
	 * Gets the type of the message.
	 * 
	 * @return type of the message.
	 */
	public Type getType();

	
	/**
	 * Gets the sender of the message. The sender is the nickname of the message sender in case the message sent is 
	 * a Moodsih message from one client to another. For all other messages. The sender will be {@link #SERVER}
	 * 
	 * @return nickname of the sender.
	 */
	public String getSendersNickname();
}
