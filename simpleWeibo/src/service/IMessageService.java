package service;

import java.util.List;

import entity.Message;

public interface IMessageService {
	public void deleteMessage(Integer messageId);
	public Message findMessageById(Integer messageId);
	public List<Message> findAllMessages(
			Integer currentPage, Integer pageSizes);
	public List<Message> findMessagesByUserId(
			Integer userId, Integer currentPage, Integer pageSize);
	public Integer createMessage(String newMessage, String userId);
	public List<Message> findFocusMessagesByUserId(
			Integer userId, Integer currentPage, Integer pageSize);
	public Integer loveMessage(Message message, Integer userId);
}
