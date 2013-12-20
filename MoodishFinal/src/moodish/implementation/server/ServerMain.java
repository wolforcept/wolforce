package moodish.implementation.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ServerMain {

	private JTextArea textArea;

	public ServerMain() {

		JFrame frame = new JFrame("Moodish Server");
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);

		frame.getContentPane().add(scroll);

		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320, 640);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textArea.append(text);
			}
		});
	}

	public static void main(String[] args) {

		new ServerMain();

		new Server().start(new SecurityCommunicator());
	}
}
