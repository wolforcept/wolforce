package moodish.comm;

/**
 * Messages sent from a clients to the server. A message contains a type (enum), 
 * and the client's nickname (String). 
 * <p>
 * <b>Important:</b> <br>
 * A <i>message</i> refers to an object sent from a client to the server.<br>
 * A <i>Moodish message</i> refers to an object sent from a client to be relayed his or her friends<br>
 * 
 *  <p>
 * The payload varies depending on the message type.
 * 
 * @author alc
 */

public interface ServerSideMessage {
	public enum Type { 
		/**
		 * A new client connected. Payload = <b>null</b>.
		 */
		CLIENT_CONNECTED, 
		
		/**
		 * A client disconnected.   Payload = <b>null</b>.
		 */
		CLIENT_DISCONNECTED, 
		
		/**
		 * A Moodish message to be forwarded to all friends. Payload = Moodish message
		 */
		MOODISH_MESSAGE, 

		/**
		 * A friendship request. The payload = nickname to be friend.
		 */		
		FRIENDSHIP, 

		/**
		 * An unfriendship request. The payload = nickname to be unfriend.
		 */		
		UNFREINDSHIP
	};
	
	/**
	 * Gets the type of the message.
	 * 
	 * @return type of the message.
	 */
	public Type   getType();

	/**
	 * Gets the payload of the message.
	 * 
	 * @return payload.
	 */
	public String getPayload();

	/**
	 * Gets the sender of the message. 
	 * 
	 * @return nickname of the sender.
	 */
	public String getClientNickname(); 
}