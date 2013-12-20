package moodish.implementation.server;

import java.util.ArrayList;
import java.util.List;

public class ServerClient {

	/**
	 * Class that represents a Client to the server.
	 * 
	 */

	private String nickName;
	private List<ServerClient> friends;
	private String mood;

	/**
	 * Constructs a new ServerClient.
	 * 
	 * @param nickName
	 *            User's NickName
	 * @param mood
	 *            User's mood, starts null
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */

	public ServerClient(String nickName, String mood) {
		this.friends = new ArrayList<ServerClient>();
		this.nickName = nickName;
		this.mood = mood;

	}

	/**
	 * User friends list.
	 * 
	 * @return List<String> friends list
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */

	public List<ServerClient> getFriends() {
		return friends;
	}

	/**
	 * Getter to mood
	 * 
	 * @return user's mood
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */
	public String getMood() {
		return mood;
	}

	/**
	 * Getter to nickname
	 * 
	 * @return user's nickname
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Verify if is friend
	 * 
	 * @param user
	 *            's nickname to be verified
	 * @return true if it's friend, false if it's not
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */
	public boolean isFriend(String nickName) {
		boolean isFriend = false;
		for (ServerClient nick : friends) {
			if (nickName.equals(nick.getNickName())) {
				isFriend = true;
			}
		}
		return isFriend;
	}

	/**
	 * Add friend to user friend list
	 * 
	 * @param User
	 *            nickname to be added
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */
	public void addFriend(ServerClient friend) {
		this.friends.add(friend);

	}

	/**
	 * Remove friend from friend list
	 * 
	 * @param User
	 *            nickname to be removed
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */

	public void removeFriend(ServerClient friend) {
		this.friends.remove(friend);

	}

	/**
	 * Remove friend from friend list
	 * 
	 * @param User
	 *            nickname to be removed
	 * @author Jo�o Mestre
	 * @author Megal Narenda * @author F�bio Ferreira
	 */

	public ServerClient getFriend(String nickName) {
		for (ServerClient c : friends) {
			if (c.getNickName().equals(nickName)) {
				return c;
			}
		}
		return null;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

}