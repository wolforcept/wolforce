package code.testers;

import code.general.GameData;
import code.ui.MainMenu;

public class Tester {

	public static void main(String[] args) {

		GameData.init();

		new MainMenu();
	}
}
