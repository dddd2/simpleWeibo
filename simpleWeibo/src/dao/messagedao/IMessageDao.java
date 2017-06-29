package dao.messagedao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Message;

public interface IMessageDao {
	public Integer createMessage(Message message);
	public void deleteMessage(Integer messageId);
	public Message findMessageById(Integer messageId);
	public List<Message> findAllMessages(
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	public List<Message> findMessagesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	public List<Message> findFocusMessagesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	public void loveThisMessage(Integer messageId);
	public Integer loveMessage(
			@Param("userId")Integer userId,
			@Param("messageId")Integer messageId,
			@Param("time")Date time);
	public Integer isLoved(
			@Param("userId")Integer userId,
			@Param("messageId")Integer messageId);
}
