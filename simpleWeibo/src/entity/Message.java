package entity;

import java.util.Date;

public class Message {
	private int messageId;
	private User user;
	private String text;
	private Date date;
	private Message parentMessage;
	private Integer loveNum;
	private Integer commentNum;
	private Integer forwardNum;
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message getParentMessage() {
		return parentMessage;
	}
	public void setParentMessage(Message parentMessage) {
		this.parentMessage = parentMessage;
	}
	public Integer getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getForwardNum() {
		return forwardNum;
	}
	public void setForwardNum(Integer forwardNum) {
		this.forwardNum = forwardNum;
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
		return "Message [messageId=" + messageId + ", user=" + user + ", text=" + text + ", date=" + date
				+ ", parentMessage=" + parentMessage + ", loveNum=" + loveNum + ", commentNum=" + commentNum
				+ ", forwardNum=" + forwardNum + "]";
	}
}
