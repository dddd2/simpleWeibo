package entity;

import java.util.Date;
import java.util.LinkedHashSet;

public class Message {
	private Integer messageId;
	private User user;
	private String text;
	private Date date;
	private String imgs;
	private Message parentMessage;
	private LinkedHashSet<Comment> comments;
	private Integer loveNum;
	private Integer commentNum;
	private Integer forwardNum;
	
	public Message() {
		super();
	}
	public Message(Integer messageId) {
		this.messageId = messageId;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public LinkedHashSet<Comment> getComments() {
		return comments;
	}
	public void setComments(LinkedHashSet<Comment> comments) {
		this.comments = comments;
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
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
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
		return "Message [messageId=" + messageId + ", user=" + user + ", text=" + text + ", date=" + date + ", imgs="
				+ imgs + ", parentMessage=" + parentMessage + ", comments=" + comments + ", loveNum=" + loveNum
				+ ", commentNum=" + commentNum + ", forwardNum=" + forwardNum + "]";
	}
}
