package moodish.implementation.shared;

import java.io.Serializable;

import moodish.interfaces.comm.ClientSideMessage;

public class MessageToClient implements ClientSideMessage, Serializable {

	private static final long serialVersionUID = 1L;

	private String payload;
	private String nickname;
	private Type type;

	public MessageToClient(Type type, String nickname, String payload) {
		super();
		this.payload = payload;
		if (type == Type.MOODISH_MESSAGE) {
			this.nickname = nickname;
		} else {
			this.nickname = SERVER;
		}
		this.type = type;
	}

	@Override
	public String getPayload() {
		return payload;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getSendersNickname() {
		return nickname;
	}

}
