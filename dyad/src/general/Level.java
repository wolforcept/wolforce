package general;
public class Level {

	public static String[] getLevel(int i) {
		switch (i) {
		case 1:
			return new String[] { "11magus", "41champion", "14wall", "24wall",
					"34wall", "44wall", "54wall", "04wall" , "15spiral"};
		case 2:
			return new String[] { "11magus", "33wall" };
		default:
			return null;
		}

	}

}
