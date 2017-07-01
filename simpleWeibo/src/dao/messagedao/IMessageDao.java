package dao.messagedao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Message;

public interface IMessageDao {
	/**
	 * 新建一条微博
	 * @param message
	 * @return
	 */
	public Integer createMessage(Message message);
	/**
	 * 删除一条微博
	 * @param messageId
	 */
	public void deleteMessage(Integer messageId);
	/**
	 * 根据微博的id查找该条微博
	 * @param messageId
	 * @return
	 */
	public Message findMessageById(Integer messageId);
	/**
	 * 查找所有微博
	 * @param currentPage //当前页数
	 * @param pageSize	//每页条数
	 * @return
	 */
	public List<Message> findAllMessages(
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	/**
	 * 根据用户id查找该用户发的所有微博
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Message> findMessagesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据用户id查找所有关注的人的微博
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Message> findFocusMessagesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	
	
	public List<Message> findAboutMeMessagesByUserId(
			@Param("userId")Integer userId,
			@Param("userName")String userName,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	/**
	 * 点赞+1
	 * @param messageId
	 */
	public void loveThisMessage(Integer messageId);
	/**
	 * 评论+1
	 * @param messageId
	 */
	public void commentThisMessage(Integer messageId);
	/**
	 * 转发+1
	 * @param messageId
	 */
	public void forwardThisMessage(Integer messageId);
	/***
	 * 点赞
	 * @param userId
	 * @param messageId
	 * @param time
	 * @return
	 */
	public Integer loveMessage(
			@Param("userId")Integer userId,
			@Param("messageId")Integer messageId,
			@Param("time")Date time);
	/**
	 * 判断是否点赞过
	 * @param userId
	 * @param messageId
	 * @return
	 */
	public Integer isLoved(
			@Param("userId")Integer userId,
			@Param("messageId")Integer messageId);
}
