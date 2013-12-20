package moodish.implementation.client;

public class UserDummy {
	private String name;
	private String mood;

	public UserDummy(String name, String mood) {
		this.name = name;
		this.mood = mood;

	}

	public UserDummy(String name) {
		this(name, Client.MOOD_NAMES[0]);
	}

	public String getName() {
		return name;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

}