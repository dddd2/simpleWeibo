package service;

import java.util.List;

import entity.Message;

public interface IMessageService {
	/**
	 * 删除指定id的微博
	 * @param messageId
	 */
	public void deleteMessage(Integer messageId);
	/**
	 * 根据微博id查找微博
	 * @param messageId
	 * @return
	 */
	public Message findMessageById(Integer messageId);
	/**
	 * 查找所有微博
	 * @param currentPage //当前页数
	 * @param pageSizes  //每页条数
	 * @return
	 */
	public List<Message> findAllMessages(
			Integer currentPage, Integer pageSizes);
	/**
	 * 根据用户id查找ta发的所有微博
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Message> findMessagesByUserId(
			Integer userId, Integer currentPage, Integer pageSize);
	/**
	 * 新建一条微博
	 * @param newMessage
	 * @param userId
	 * @return
	 */
	public Integer createMessage(String newMessage, String userId);
	/***
	 * 查找所关注的人发的微博
	 * @param userId
	 * @param currentPage  //当前页数
	 * @param pageSize     //每页条数
	 * @return
	 */
	public List<Message> findFocusMessagesByUserId(
			Integer userId, Integer currentPage, Integer pageSize);
	
	/**
	 * 点赞微博
	 * @param message
	 * @param userId
	 * @return
	 */
	public Integer loveMessage(Message message, Integer userId);
}
