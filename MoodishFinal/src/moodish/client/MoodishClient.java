package moodish.client;

import moodish.comm.ClientComm;
//import moodish.comm.ServerComm;

/**
 * A Moodish client should use a {@link moodish.comm.ClientComm} interface to communicate with the server. 
 * 
 * <p>
 * When a client connects to the server, it will first receive one
 * {@link moodish.comm.ClientSideMessage.Type#CONNECTED CONNECTED} message for each user currently 
 * connected to the client. The client thus receives information about other clients currently connected 
 * to the server when it connects to be able to maintain a list of users online.
 * 
 * <p> 
 * After a client has connected, it can then send Moodish messages ({@link moodish.comm.ClientComm#sendMoodishMessage(String)}), 
 *  friendship request ({@link moodish.comm.ClientComm#friendship(String)}), and  unfriendships requests ({@link moodish.comm.ClientComm#unfriendship(String)}) 
 * to the server. 
 * 
 * <p>
 * The client should use {@link moodish.comm.ClientComm#getNextMessage()} to receive:
 * <ul>
 * <li> Moodish messages from other users (type = {@link moodish.comm.ClientSideMessage.Type#Moodish_MESSAGE MOODISH_MESSAGE}), 
 * <li> notifications about new friendship (type = {@link moodish.comm.ClientSideMessage.Type#FRIENDSHIP FRIENDSHIP})
 * <li> notifications about new unfriendship (type = {@link moodish.comm.ClientSideMessage.Type#UNFRIENDSHIP UNFRIENDSHIP})
 * <li> notifications about new clients online (type = {@link moodish.comm.ClientSideMessage.Type#CONNECTED CONNECTED })
 * <li> notifications about clients who go offline (type = {@link moodish.comm.ClientSideMessage.Type#DISCONNECTED DISCONNECTED})
 * </ul>	
 *  
 *<p>
 * If a client loses its connection to the server, it will not be seeing his/her friends nor will it have any 
 * friends even if it reconnects.
 * 
 * @author alc
 * @version 1 
 *
 */
public interface MoodishClient {

	/**
	 * Starts the client and only returns when (if ever) the client is stopped.
	 * 
	 * @param clientComm the clientComm through which the client should communicate with the server.
	 */

	public void start(ClientComm clientComm);	
}
