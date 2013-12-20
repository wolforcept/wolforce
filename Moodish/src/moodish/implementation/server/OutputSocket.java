package moodish.implementation.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import moodish.implementation.shared.MessageToClient;

public class OutputSocket {

	private HashMap<String, Socket> clients_map_sockets;
	private HashMap<String, ObjectOutputStream> clients_map_out;

	public OutputSocket() {
		clients_map_sockets = new HashMap<String, Socket>();
		clients_map_out = new HashMap<String, ObjectOutputStream>();
	}

	public void add(Socket socket, String username, ObjectOutputStream out)
			throws IOException, ClassNotFoundException {
		clients_map_sockets.put(username, socket);
		clients_map_out.put(username, out);
	}

	public void remove(String username) {
		clients_map_sockets.remove(username);
		clients_map_out.remove(username);
	}

	public boolean isConnected(String username) {
		return clients_map_sockets.containsKey(username);
	}

	public void sendMessage(MessageToClient msg, String toNickname)
			throws IOException {
		System.out.println("Sent Message " + msg.getPayload() + " to "
				+ toNickname + " from " + msg.getSendersNickname());
		ObjectOutputStream out = clients_map_out.get(toNickname);
		out.writeObject(msg);
	}

	@Override
	public String toString() {
		return clients_map_out.values().toString();
	}

}
