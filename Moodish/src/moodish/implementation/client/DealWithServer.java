package moodish.implementation.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import moodish.implementation.shared.MessageToClient;

public class DealWithServer extends Thread {

	private ClientCommmunicator client;
	private ObjectInputStream in;

	public DealWithServer(ClientCommmunicator client, ObjectInputStream in) {
		super(null, null, "DealWithServer_Thread");
		this.client = client;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				MessageToClient msg_client = (MessageToClient) in.readObject();
				System.out.println("Mensagem recebida no cliente: "
						+ client.getUsername() + " : " + msg_client.getType());
				client.addMessage(msg_client);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			this.interrupt();
		}

	}

}
