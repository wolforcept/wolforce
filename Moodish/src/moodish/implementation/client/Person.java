package moodish.implementation.client;


public class Person {

	private String name;
	private String mood;
	private boolean isMyFriend;

	public Person(String name, String mood) {
		this.name = name;
		this.mood = mood;
		isMyFriend = false;
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

	public boolean isMyFriend() {
		return isMyFriend;
	}

	public void addAsFriend() {
		isMyFriend = true;
	}

	public void removeAsFriend() {
		isMyFriend = false;
	}

}