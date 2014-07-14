import java.util.Arrays;
import java.util.List;

public class Ivory {

	private int attempt, unlockedBirthplaces, attributePoints;
	private int weak_strong, simple_complex, sloppy_smart, pliant_rigid,
			careful_debonair, casual_ambitious;

	public Ivory() {
		attempt = 0;
		unlockedBirthplaces = 4;
		attributePoints = 15;
		weak_strong = simple_complex = sloppy_smart = pliant_rigid = careful_debonair = casual_ambitious = 0;
	}

	public void nextAttempt() {
		attempt++;
	}

	public int getAttempt() {
		return attempt;
	}

	public int getUnlockedBirthplaces() {
		return unlockedBirthplaces;
	}

	public int getPts() {
		return attributePoints;
	}

	public void incrementPts() {
		attributePoints++;
	}

	public void decrementPts() {
		attributePoints--;
	}

	public int getWeak_strong() {
		return weak_strong;
	}

	public int getSimple_complex() {
		return simple_complex;
	}

	public int getSloppy_smart() {
		return sloppy_smart;
	}

	public int getCareful_debonair() {
		return careful_debonair;
	}

	public int getPliant_rigid() {
		return pliant_rigid;
	}

	public int getCasual_ambitious() {
		return casual_ambitious;
	}

	public void setWeak_strong(int weak_strong) {
		this.weak_strong = weak_strong;
	}

	public void setSimple_complex(int simple_complex) {
		this.simple_complex = simple_complex;
	}

	public void setSloppy_smart(int sloppy_smart) {
		this.sloppy_smart = sloppy_smart;
	}

	public void setPliant_rigid(int pliant_rigid) {
		this.pliant_rigid = pliant_rigid;
	}

	public void setCareful_debonair(int careful_debonair) {
		this.careful_debonair = careful_debonair;
	}

	public void setCasual_ambitious(int casual_ambitious) {
		this.casual_ambitious = casual_ambitious;
	}

	// /////////////////

	public List<Leader> getLeaders() {
		return Arrays.asList(Leader.values());
	}

	enum Leader {

		Adeen_Thorba, Marco_Selar, Sytin_the_Eagle, Z_Kellion_Muhl;

		private int power;

		public void setPower(int power) {
			this.power = power;
		}

		public int getPower() {
			return power;
		}

		public String getName() {
			return toString().replace('_', ' ');
		}
	}
}
