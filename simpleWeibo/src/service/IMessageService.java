package service;

import java.util.List;

import entity.Message;

public interface IMessageService {
	public void createMessage(Message message);
	public void deleteMessage(Integer messageId);
	public Message findMessageById(Integer messageId);
	public List<Message> findAllMessages();
	public List<Message> findMessagesByUserId(Integer userId);
}
