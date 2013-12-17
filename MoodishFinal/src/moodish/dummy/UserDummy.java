package moodish.dummy;

public class UserDummy {
	private String name;
	private Mood mood;

	public UserDummy(String name, Mood mood) {
		super();
		this.name = name;
		this.mood = mood;

	}

	public String getName() {
		return name;
	}

	public Mood getMood() {
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public enum Mood {
		HAPPY(0), UNHAPPY(1), TIRED(2);

		private final int valor;

		Mood(int valorOpcao) {
			valor = valorOpcao;
		}

		public int getValor() {
			return valor;
		}

	}
}