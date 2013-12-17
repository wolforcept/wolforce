package moodish.dummy;

import java.io.IOException;
import java.io.ObjectInputStream;

public class DealWithServer extends Thread {

	private ClientCommDummy client;
	private ObjectInputStream in;

	public DealWithServer(ClientCommDummy client, ObjectInputStream in) {
		super();
		this.client = client;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (true) {
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
