package dao.messagedao;

import java.util.List;

import entity.Message;

public interface IMessageDao {
	public void createMessage(Message message);
	public void deleteMessage(Integer messageId);
	public Message findMessageById(Integer messageId);
	public List<Message> findMessagesByUserId(Integer userId);
}
