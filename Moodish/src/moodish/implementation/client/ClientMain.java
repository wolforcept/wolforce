package moodish.implementation.client;

public class ClientMain {
	public static void main(String[] args) {
		Client client = new Client();
		client.buildGui();
		client.start(new ClientCommmunicator());
	}
}
