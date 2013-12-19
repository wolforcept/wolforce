package moodish.implementation.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import moodish.implementation.server.ServerCommmunicator;
import moodish.implementation.shared.MessageToClient;
import moodish.implementation.shared.MessageToServer;
import moodish.interfaces.comm.ClientComm;
import moodish.interfaces.comm.ClientSideMessage;

public class ClientCommmunicator implements ClientComm {

	private String username;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	private boolean connected;
	private BlockingQueue<MessageToClient> client_messages = new LinkedBlockingQueue<MessageToClient>();

	public ClientCommmunicator() {
		connected = false;
	}

	@Override
	public void connect(String serverAddress, String username) {
		this.username = username;
		InetAddress address;
		try {
			address = InetAddress.getByName(serverAddress);
			socket = new Socket(address, ServerCommmunicator.PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			MessageToServer first_msg = new MessageToServer(
					MessageToServer.Type.CLIENT_CONNECTED, username, null);
			out.writeObject(first_msg);
			new DealWithServer(this, in).start();
			connected = true;
		} catch (UnknownHostException e) {
			connectionFailure("Não é possível connectar, host desconhecido!");
		} catch (IOException e) {
			connectionFailure("Não é possível connectar, host desconhecido!");
		}
	}

	private void connectionFailure(String text) {
		JOptionPane.showMessageDialog(null, text);
		connected = false;
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void disconnect() {
		MessageToServer msg = new MessageToServer(
				MessageToServer.Type.CLIENT_DISCONNECTED, username, null);
		if (connected)
			try {
				out.writeObject(msg);
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean hasNextMessage() {
		return !client_messages.isEmpty();
	}

	@Override
	public ClientSideMessage getNextMessage() {
		MessageToClient received_msg = null;
		try {
			received_msg = client_messages.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return received_msg;
	}

	@Override
	public void sendMoodishMessage(String moodishMessage) {
		if (connected) {
			MessageToServer msg = new MessageToServer(
					MessageToServer.Type.MOODISH_MESSAGE, username,
					moodishMessage);
			sendMessage(msg);
		}
	}

	@Override
	public void friendship(String nickname) {
		MessageToServer msg = new MessageToServer(
				MessageToServer.Type.FRIENDSHIP, username, nickname);
		sendMessage(msg);
	}

	@Override
	public void unfriendship(String nickname) {
		MessageToServer msg = new MessageToServer(
				MessageToServer.Type.UNFREINDSHIP, username, nickname);
		sendMessage(msg);
	}

	private void sendMessage(MessageToServer msg) {
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			connectionFailure("Foi Desconectado.");
		}
	}

	public void addMessage(MessageToClient msg) {
		try {
			client_messages.put(msg);
			System.out.println("Eu, " + username + " recebi uma mensagem do "
					+ msg.getSendersNickname() + " do tipo " + msg.getType()
					+ " a dizer " + msg.getPayload());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void clearAllMessages() {
		client_messages.clear();
	}
}
