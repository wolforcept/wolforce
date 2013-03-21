package code.testers;

import java.io.IOException;

import code.ui.MainMenu;



public class Tester {

	public static void main(String[] args) {
		try {
			new MainMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
