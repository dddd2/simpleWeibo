package service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.messagedao.IMessageDao;
import entity.Message;
import entity.User;
import service.IMessageService;
import util.MyBatisUtil;

public class MessageServiceImpl implements IMessageService{
	private static IMessageDao messageDao;
	private static SqlSession sqlSession;
	
	static {
		sqlSession = MyBatisUtil.getSqlSession();
		messageDao = sqlSession.getMapper(IMessageDao.class);
	}

	@Override
	public Integer createMessage(String newMessage, String userId) {
		Message message = new Message();
		message.setText(newMessage);
		message.setDate(new Date());
		message.setUser(new User(Integer.valueOf(userId)));
		message.setLoveNum(0);
		message.setCommentNum(0);
		message.setForwardNum(0);
		
		Integer messageId = messageDao.createMessage(message);
		sqlSession.commit();
		
		return messageId;
	}
	
	@Override
	public void deleteMessage(Integer messageId) {
		messageDao.deleteMessage(messageId);
		sqlSession.commit();
	}
	
	@Override
	public Message findMessageById(Integer messageId) {
		return messageDao.findMessageById(messageId);
	}

	@Override
	public List<Message> findMessagesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		
		return messageDao.findMessagesByUserId(userId, currentPage, pageSize);
	}

	@Override
	public List<Message> findAllMessages(Integer currentPage, Integer pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		
		return messageDao.findAllMessages(currentPage, pageSize);
	}

	@Override
	public List<Message> findFocusMessagesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		
		return messageDao.findFocusMessagesByUserId(userId, currentPage, pageSize);
	}

	@Override
	public Integer loveMessage(Message message, Integer userId) {
		Integer messageId = message.getMessageId();
		
		if(messageDao.isLoved(userId, messageId) == null) {
			messageDao.loveThisMessage(messageId);
			Integer id = messageDao.loveMessage(userId, messageId, new Date());
			
			sqlSession.commit();
			return id;
		}
		return 0;
	}

}
