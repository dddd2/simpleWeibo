package service.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.messagedao.IMessageDao;
import dao.userdao.IUserDao;
import entity.Message;
import service.ITouristService;
import util.MyBatisUtil;

public class MessageServiceImpl implements IMessageDao{
	private static IMessageDao messageDao;
	private static SqlSession sqlSession;
	
	static {
		sqlSession = MyBatisUtil.getSqlSession();
		messageDao = sqlSession.getMapper(IMessageDao.class);
	}

	public void createMessage(Message message) {
		// TODO Auto-generated method stub
		messageDao.createMessage(message);
		sqlSession.commit();
		
	}

	public void deleteMessage(Integer messageId) {
		// TODO Auto-generated method stub
		messageDao.deleteMessage(messageId);
		sqlSession.commit();
	}

	public Message findMessageById(Integer messageId) {
		// TODO Auto-generated method stub
		return messageDao.findMessageById(messageId);
	}

	public List<Message> findMessagesByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return messageDao.findMessagesByUserId(userId);
	}

}
