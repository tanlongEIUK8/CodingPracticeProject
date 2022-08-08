	package models;

public class Message {
	private static int count = 0;
	private String id;
	private String content;
	private String senderId;
	private String receiverId;
	private UserType receiverType;

	public Message(String content, String senderId, String receiverId) {
		id = "msg"+count++;
		this.content = content;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public UserType getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(UserType receiverType) {
		this.receiverType = receiverType;
	}

}