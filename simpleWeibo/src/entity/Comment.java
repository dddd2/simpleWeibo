package entity;

import java.util.Date;

/**
 * 评论
 * @author 刘洋
 *
 */
public class Comment {
	private int commentId;
	private String text; //内容
	private Message message;  //被评论微博
	private User user;  //评论人
	private User puser; //被评论人
	private Date time;//时间
	public Comment() {
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getPuser() {
		return puser;
	}
	public void setPuser(User puser) {
		this.puser = puser;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", text=" + text + ", message=" + message + ", user=" + user
				+ ", puser=" + puser + ", time=" + time + "]";
	}
}		
