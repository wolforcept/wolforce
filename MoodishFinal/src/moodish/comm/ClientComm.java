package moodish.comm;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Communication interface for {@link moodish.client.MoodishClient Moodish clients}. Moodish clients must 
 * communicate with a MoodishServer exclusively through this interface. 
 * 
 * @author alc
 * @version 1
 */

public interface ClientComm {

	/**
	 * Establish a connection to a Moodish server. This method should be the first one called by 
	 * the client as no other operation (expect for {@link #isConnected()}) can be performed before
	 * a connection to the server has been established.
	 * 
	 * @param host                    Address of the server. For instance "localhost", "192.168.1.1", and so on.
	 * @param nickname                The nickname to use for the client.
	 * @throws UnknownHostException   Thrown if the host cannot be found.
	 * @throws IOException            Thrown if a connection cannot be established.
	 */
	public void connect(String host, String nickname) throws UnknownHostException, IOException;

	/**
	 * Check if currently connected to a host.
	 * 
	 * @return <b>true</b> if currently connected to a host, <b>false</b> otherwise.
	 */
	public boolean isConnected();

	/**
	 * Disconnect from a Moodish server. Any sockets should be closed immediately. 
	 * Pending messages are not guaranteed to be delivered before disconnecting.
	 */	
	public void disconnect();
		
	/**
	 * Get the next message received from the server. If no message 
	 * is has been received, the method blocks until a message has 
	 * been received. If no message will ever be received (connection is down), null is returned.
	 * 
	 * @return The next message sent by the server to the client or null if no message will ever be received (disconnected from server).
	 */
	public ClientSideMessage getNextMessage();
	
	/** 
	 * Check if a message from the server is pending. If {@link #hasNextMessage()} returns <b>true</b>, a call to {@link #getNextMessage()} 
	 * will return immediately with the oldest message in the queue. If {@link #hasNextMessage()} returns <b>false</b>, a call to {@link #getNextMessage()} 
	 * will block until a message has been received.
	 * 
	 * @return <b>true</b> if a message from the server is currently pending, otherwise <b>false</b>.
	 */
	public boolean hasNextMessage();

	/**
	 * Send a Moodish message from this client to the server which should then relay the message to all freinds.
	 * 
	 * @param moodishMessage The Moodish message to be sent.
	 */
	public void sendMoodishMessage(String moodishMessage);

	/**
	 * Request to be friend of another client.
	 * 
	 * @param nickname the nickname of the client that should be friend.
	 */
	public void friendship(String nickname);
	
	
	/**
	 * Request to unfriend of  a client.
	 * 
	 * @param nickname the nickname of the client currently friend that should be unfriended. 
	 */
	public void unfriendship(String nickname);
}
