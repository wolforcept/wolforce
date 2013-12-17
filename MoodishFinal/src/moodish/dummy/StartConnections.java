package moodish.dummy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StartConnections extends Thread {

	private ServerSocket server_socket;
	private OutputSocket outs = new OutputSocket();
	private ServerCommDummy server;
	
	public StartConnections(ServerSocket server_socket, OutputSocket outs, ServerCommDummy server) {
		this.server_socket = server_socket;
		this.outs = outs;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("À espera de uma ligação...");
				Socket socket = server_socket.accept();
				System.out.println("Ligação efetuada");
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				MessageToServer first_msg = (MessageToServer) in.readObject();
				System.out.println("Mensagem recebida: " + first_msg.getType());
				server.addMessage(first_msg);
				String username = first_msg.getClientNickname();
				outs.add(socket, username, out);
				new DealWithClient(server, in).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
