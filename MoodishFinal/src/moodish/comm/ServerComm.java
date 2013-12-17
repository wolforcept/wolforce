package moodish.comm;

/**
 * 
 * Communication interface for a Moodish server. A Moodish server must 
 * communicate clients exclusively through this interface.

 * @author alc
 * @version 1
 */
public interface ServerComm {

	/**
	 * Starts the Moodish server. The method should setup a server socket and begin to accept connections.
	 * This method must return immediately after a server socket has been setup, and another thread should
	 * started to listen for connections.
	 */
	public void start();
	
	/**
	 * Get the next message received from one of the clients. If no message 
	 * is has been received, the method blocks until a message has 
	 * been received. 
	 * 
	 * @return The next message sent by one of the connected clients to the server
	 */
	public ServerSideMessage getNextMessage();
	
	/** 
	 * Checks if a message from a client is pending. If {@link #hasNextMessage()} returns <b>true</b>, a call to {@link #getNextMessage()} 
	 * will return immediately with the oldest message in the queue. If {@link #hasNextMessage()} returns <b>false</b>, a call to {@link #getNextMessage()} 
	 * will block until a message has been received.
	 * 
	 * @return <b>true</b> if a message from a client is currently pending, otherwise <b>false</b>.
	 */
	public boolean hasNextMessage();

   /**
    * Send a message to a client. This method should be used to relay messages received from a client to a friend. This method should 
    * be called once for each friend.
    * 
    * @param fromNickname The nickname of the client from which the message was sent
    * @param toNickname   The nickname of a friend
    * @param moodishMessage      The message 
    */
	public void sendMoodishMessage(String fromNicename, String toNickname, String moodishMessage);

	/**
	 * Inform a client that another client is now a friend.
	 * @param toNickname  nickname of the client who has a new friend
	 * @param newFriendship nickname of the new friend
	 */
	public void sendNewFriendship(String toNickname, String newFriendship);

	/**
	 * Inform a client that a former friend has decided to not be friend anymore.
	 * 
	 * @param toNickname  nickname of the client who has lost a friend
	 * @param unfriendship nickname of the unfriendship
	 */
	public void sendNewUnfriendship(String toNickname, String unfriendship);

	/**
	 * Report an error to a client. An error can, for instance, be trying to be friend of  
	 * a client who is not connected, trying to unfriend of a client not currently our  
	 * friend, and so on.
	 * 
	 * @param toNickname nickname of the client.
	 * @param error description of the error.
	 */
	public void sendError(String toNickname, String error);

	/**
	 * Inform a client that a new client has connected. When a new client connects, the server should call this method 
	 * for each client already connected to inform the newly connected client about the clients currently online. 
	 * 
	 * @param toNickname nickname of the client to whom to send the message.
	 * @param connectedNickname nickname of the connected client.
	 */
	public void sendClientConnected(String toNickname, String connectedNickname);

	/**
	 * Inform a client that another client disconnected. 
	 * 
	 * @param toNickname nickname of the client to whom to send the message.
	 * @param disconnectedNickname nickname of client who disconnected.
	 */
	public void sendClientDisconnected(String toNickname, String disconnectedNickname);

	/**
	 * Checks if a client with a certain nickname is currently connected.
	 * 
	 * @param nickname nickname of the client to check.
	 * @return <b>true</b> if a client with the nickname is currently connected, otherwise <b>false</b>.
	 */
	public boolean clientIsConnected(String nickname);
	
	/**
	 * Disconnect a client.
	 * 
	 * @param nickname nickname of the client to disconnect.
	 */
	public void disconnectClient(String nickname);

}
