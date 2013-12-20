package moodish.implementation.shared;

import java.io.Serializable;

import moodish.interfaces.comm.ServerSideMessage;

public class MessageToServer implements ServerSideMessage, Serializable {

	private static final long serialVersionUID = 1L;

	private Type type;
	private String payload;
	private String nickname;

	public MessageToServer(Type type, String nickname, String payload) {
		super();
		this.type = type;
		this.nickname = nickname;
		this.payload = payload;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getPayload() {
		return payload;
	}

	@Override
	public String getClientNickname() {
		return nickname;
	}

}
