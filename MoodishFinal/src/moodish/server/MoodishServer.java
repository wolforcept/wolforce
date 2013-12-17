package moodish.server;

import moodish.comm.ServerComm;

/**
 * A Moodish server should use a {@link moodish.comm.ServerComm} interface to communicate with clients. The server is responsible 
 * for:
 * <ul>
 * <li> maintaining information about who is friend of whom
 * <li> for relaying Moodish messages appropriately
 * <li> for updating clients with information regarding who is currently online
 * <li> for updating clients on friends
 * </ul> 
 *
 * <p>
 * When a client connects, the server send one {@link moodish.comm.ClientSideMessage.Type#CONNECTED CONNECTED} message 
 * ({@link moodish.comm.ServerComm#sendClientConnected(String toNickname, String connectedNickname)
 * ServerComm.sendClientConnected()}) for each connected client so that the newly connected client can maintain a list 
 * of clients currently connected to the server. 
 *
 *<p>
 * When a client disconnects, all clients should be sent a {@link moodish.comm.ClientSideMessage.Type#DISCONNECTED DISCONNECTED} message 
 * ({@link moodish.comm.ServerComm#sendClientDisconnected(String toNickname, String disconnectedNickname)
 * ServerComm.sendClientDisconnected()}). When a client disconnects, it loses all its friends and automatically
 * unfreinds all other clients. The server should thus not maintain a memory server regarding friends of disconnected clients. 
 * 
 * <p>
 * The server should use {@link moodish.comm.ServerComm#getNextMessage()} to receive:
 * <ul>
 * <li> Moodish messages to be relayed (type = {@link moodish.comm.ServerSideMessage.Type#MOODISH_MESSAGE MOODISH_MESSAGE}), 
 * <li> notifications about new friendship request (type = {@link moodish.comm.ServerSideMessage.Type#FRIENDSHIP FRIENDSHIP})
 * <li> notifications about new unfriendship request (type = {@link moodish.comm.ServerSideMessage.Type#UNFRIENDSHIP UNFRIENDSHIP})
 * <li> notifications about a new client connection (type = {@link moodish.comm.ServerSideMessage.Type#CLIENT_CONNECTED CONNECTED})
 * <li> notifications about clients who go offline (type = {@link moodish.comm.ServerSideMessage.Type#CLIENT_DISCONNECTED DISCONNECTED})
 * </ul>	
 * 
 * @author alc
 * @version 1
 */
public interface MoodishServer {
	/**
	 * Starts the server and only returns when (if ever) the server is stopped.
	 * 
	 * @param serverComm the serverComm through which the server should communicate with clients.
	 */
	public void start(ServerComm serverComm);
}