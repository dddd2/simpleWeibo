package service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.messagedao.IMessageDao;
import entity.Comment;
import entity.Message;
import entity.User;
import service.IMessageService;
import service.IUserService;
import util.ImageUtils;
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
	public Integer createMessage(String newMessage, String userId, String imgs, String keywords) {
		Message message = new Message();
		message.setText(newMessage);
		message.setDate(new Date());
		message.setUser(new User(Integer.valueOf(userId)));
		message.setLoveNum(0);
		message.setCommentNum(0);
		message.setForwardNum(0);
		
		if(!imgs.equals("{}")) {
			StringBuilder imgPath = new StringBuilder();
			JSONObject imgsObj = JSON.parseObject(imgs);
	
			String[] keys = keywords.split(",");
			for(String key : keys) {
				Date today = new Date();
				String path ="D:/weiboPics/messageImgs/" + new SimpleDateFormat("yyyy-MM-dd").format(today) + "/" + today.getTime() + ".jpg";
				String text = (String)((JSONObject)imgsObj.get(key)).get("imgSrc");
				
				ImageUtils.base64ToImg(text, path);
				imgPath.append(path + ",");
			}
			
			imgPath.deleteCharAt(imgPath.length() - 1);
			message.setImgs(imgPath.toString());
		}
		
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
		Message message = messageDao.findMessageById(messageId);
		
		return parseMessage(message);
	}

	@Override
	public List<Message> findMessagesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		
		List<Message> list = messageDao.findMessagesByUserId(userId, currentPage, pageSize);
//		for (Message message : list) {
//			String imgs = message.getImgs();
//			StringBuilder base64Imgs = new StringBuilder();
//			
//			if(imgs != null && imgs.charAt(0) == 'D') {
//				String[] imgPaths = imgs.split(",");
//				for (String path : imgPaths) {
//					String img = ImageUtils.img2Base64(new File(path));
//					base64Imgs.append(img + "$$$");
//				}
//				base64Imgs.delete(base64Imgs.length() - 3, base64Imgs.length() - 1);
//				message.setImgs(base64Imgs.toString());
//			}
//		}
		
		return parseMessages(list);
	}

	@Override
	public List<Message> findAllMessages(Integer currentPage, Integer pageSize) {
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		return messageDao.findAllMessages(currentPage, pageSize);
	}

	@Override
	public List<Message> findFocusMessagesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		List<Message> list = messageDao.findFocusMessagesByUserId(userId, currentPage, pageSize);
//		for (Message message : list) {
//			String imgs = message.getImgs();
//			StringBuilder base64Imgs = new StringBuilder();
//			
//			if(imgs != null && imgs.charAt(0) == 'D') {
//				String[] imgPaths = imgs.split(",");
//				for (String path : imgPaths) {
//					String img = ImageUtils.img2Base64(new File(path));
//					base64Imgs.append(img + "$$$");
//				}
//				base64Imgs.delete(base64Imgs.length() - 3, base64Imgs.length());
//				message.setImgs(base64Imgs.toString());
//			}
//		}
		
		return parseMessages(list);
	}

	@Override
	public List<Message> findAboutMeMessagesByUserId(Integer userId, String userName, Integer currentPage,
			Integer pageSize) {
		if (currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		
		userName = "%@" + userName + "%";
		
		List<Message> list = messageDao.findAboutMeMessagesByUserId(userId, userName, currentPage, pageSize);
		
		return parseMessages(list);
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
	
	private List<Message> parseMessages(List<Message> list) {
		for(Message message : list) {
			message = parsePic(message);
			
			User user = message.getUser();
			message.setUser(parseTouxiang(user));
			
			LinkedHashSet<Comment> comments = message.getComments();
			message.setComments(parseComments(comments));
		}
		
		return list;
	}
	
	private Message parseMessage(Message message) {
		message = parsePic(message);
		
		User user = message.getUser();
		message.setUser(parseTouxiang(user));
		
		LinkedHashSet<Comment> comments = message.getComments();
		message.setComments(parseComments(comments));
		
		return message;
	}
	
	private Message parsePic(Message message) {
		String imgs = message.getImgs();
		StringBuilder base64Imgs = new StringBuilder();
		
		if(imgs != null) {
			if(!imgs.equals("") && imgs.charAt(0) == 'D') {
				String[] imgPaths = imgs.split(",");
				for (String path : imgPaths) {
					String img = ImageUtils.img2Base64(new File(path));
					base64Imgs.append(img + "$$$");
				}
				base64Imgs.delete(base64Imgs.length() - 3, base64Imgs.length());
				message.setImgs(base64Imgs.toString());
			}
		}
		
		return message;
	}
	
	private LinkedHashSet<Comment> parseComments(LinkedHashSet<Comment> comments) {
		for(Comment comment : comments) {
			User user = comment.getUser();
			
			parseTouxiang(user);
			
			comment.setUser(user);
		}
		
		return comments;
	}
	
	private User parseTouxiang(User user) {
		String touxiang = user.getTouxiang();
		
		if(touxiang != null) {
			if(!touxiang.equals("") && touxiang.charAt(0) == 'D') {
				String newTouxiang = ImageUtils.img2Base64(new File(touxiang));
				user.setTouxiang(newTouxiang);
			}
		}
		
		return user;
	}

	@Override
	public Integer findTotalNum(Integer userId) {
		return messageDao.findTotalNum(userId);
	}

	@Override
	public Integer findTotalNumFocus(Integer userId) {
		return messageDao.findTotalNumFocus(userId);
	}

	@Override
	public Integer findAllTotalNum() {
		return messageDao.findAllTotalNum();
	}
}
