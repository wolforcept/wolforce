package moodish.implementation.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import moodish.implementation.shared.MessageToClient;
import moodish.implementation.shared.MessageToServer;
import moodish.interfaces.comm.ServerComm;
import moodish.interfaces.comm.ServerSideMessage;


public class ServerCommmunicator implements ServerComm {

	public static final int PORT = 60500;
	private String server_address;
	private BlockingQueue<MessageToServer> server_messages = new LinkedBlockingQueue<MessageToServer>();
	private OutputSocket outs = new OutputSocket();
	private MessageToClient msg;

	@Override
	public void start() {
		ServerSocket server_socket = null;
		try {
			server_socket = new ServerSocket(PORT);
			server_address = server_socket.getInetAddress().getHostAddress();
			System.out.println("SERVIDOR INICIALIZADO");
			new StartConnections(server_socket, outs, this).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// } finally {
		// try {
		// server_socket.close();
		// } catch (IOException e) {
		// System.err.println("Erro: problemas a fechar o socket: "
		// + e.getMessage());
		// }
		// }
	}

	@Override
	public boolean hasNextMessage() {
		return !server_messages.isEmpty();
		
	}

	@Override
	public ServerSideMessage getNextMessage() {
		MessageToServer received_msg = null;
		try {
			received_msg = server_messages.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return received_msg;

	}

	@Override
	public void sendNewFriendship(String toNickname, String newFriendship) {
		msg = new MessageToClient(MessageToClient.Type.FRIENDSHIP, null,
				newFriendship);
		sendMessage(msg, toNickname);
	}

	@Override
	public void sendError(String toNickname, String error) {
		msg = new MessageToClient(MessageToClient.Type.ERROR, null, error);
		sendMessage(msg, toNickname);
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		return outs.isConnected(nickname);
	}

	@Override
	public void sendClientConnected(String toNickname, String userConnected) {
		msg = new MessageToClient(MessageToClient.Type.CONNECTED, null,
				userConnected);
		sendMessage(msg, toNickname);
	}

	@Override
	public void sendClientDisconnected(String toNickname,
			String userDisconnected) {
		msg = new MessageToClient(MessageToClient.Type.DISCONNECTED, null,
				userDisconnected);
		sendMessage(msg, toNickname);
	}

	@Override
	public void disconnectClient(String nickname) {
		outs.remove(nickname);
	}

	@Override
	public void sendMoodishMessage(String fromNicename, String toNickname,
			String message) {
		msg = new MessageToClient(MessageToClient.Type.MOODISH_MESSAGE,
				fromNicename, message);
		sendMessage(msg, toNickname);

	}

	@Override
	public void sendNewUnfriendship(String toNickname, String newFriendship) {
		msg = new MessageToClient(MessageToClient.Type.UNFRIENDSHIP, null,
				newFriendship);
		sendMessage(msg, toNickname);
	}

	public void addMessage(MessageToServer msg_server) {
		try {
			server_messages.put(msg_server);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	private void sendMessage(MessageToClient msg, String toNickname) {
		try {
			outs.sendMessage(msg, toNickname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getServerAddress(){
		return server_address;
	}
	
	public void clearAllMessages(){
		server_messages.clear();
	}
}
