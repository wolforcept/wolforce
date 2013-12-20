package moodish.implementation.client;

import javax.swing.JPanel;

public class Person {

	private String name;
	private String mood;

	public Person(String name, String mood) {
		this.name = name;
		this.mood = mood;

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