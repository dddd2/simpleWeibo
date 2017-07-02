package service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.messagedao.IMessageDao;
import entity.Message;
import entity.User;
import service.IMessageService;
import service.IUserService;
import util.MyBatisUtil;

public class MessageServiceImpl implements IMessageService{
	private static IMessageDao messageDao;
	private static SqlSession sqlSession;
	private IUserService userService = new UserServiceImpl();
	
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
		
		String[] arrs = newMessage.split(" ");
		for (String arr : arrs) {
			if(arr.length() > 0) {
				if(arr.charAt(0) == '@') {
					User user = this.userService.findUserByName(arr.substring(1));
					this.userService.haveNewAboutMe(user.getUserId());
				}
			}
		}
		
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
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		return messageDao.findFocusMessagesByUserId(userId, currentPage, pageSize);
	}

	@Override
	public List<Message> findAboutMeMessagesByUserId(Integer userId, String userName, Integer currentPage,
			Integer pageSize) {
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		
		userName = "%@" + userName + "%";
		
		return messageDao.findAboutMeMessagesByUserId(userId, userName, currentPage, pageSize);
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

	@Override
	public void commentThisMessage(Integer messageId) {
		messageDao.commentThisMessage(messageId);
		
		sqlSession.commit();
	}

}
