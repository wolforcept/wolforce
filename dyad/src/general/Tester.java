package general;

import java.io.IOException;


public class Tester {

	public static void main(String[] args) {
		try {
			new Controller();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
