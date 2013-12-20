package moodish.implementation.server;

import java.io.IOException;
import java.io.ObjectInputStream;

import moodish.implementation.shared.MessageToServer;

public class DealWithClient extends Thread {

	private ServerCommmunicator server;
	private ObjectInputStream in;

	public DealWithClient(ServerCommmunicator serverCommDummy,
			ObjectInputStream inputStream) {
		server = serverCommDummy;
		in = inputStream;
	}

	@Override
	public void run() {
		try {
			while (true) {
				MessageToServer msg_server = (MessageToServer) in.readObject();
				System.out
						.println("Mensagem recebida: " + msg_server.getType());
				server.addMessage(msg_server);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			this.interrupt();
		}
	}

}
