package entity;

import java.util.Date;

public class Message {
	private int messageId;
	private User user;
	private String text;
	private Date date;
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", user=" + user + ", text=" + text + ", date=" + date + "]";
	}
}
