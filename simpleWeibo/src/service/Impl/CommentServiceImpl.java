package service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.commentdao.ICommentDao;
import entity.Comment;
import entity.Message;
import entity.User;
import service.ICommentService;
import service.IMessageService;
import service.IUserService;
import util.MyBatisUtil;

public class CommentServiceImpl implements ICommentService{
	private static ICommentDao commentDao;
	
	private static SqlSession sqlSession;
	
	private IUserService userService = new UserServiceImpl();
	
	private IMessageService messageService = new MessageServiceImpl();
	
	static {
		sqlSession = MyBatisUtil.getSqlSession();
		commentDao = sqlSession.getMapper(ICommentDao.class);
	}

	@Override
	public Integer createCommentForMessage(Integer messageId, Integer userId, String puserName, String text) {
		Comment comment = new Comment();
		comment.setMessage(new Message(messageId));
		comment.setUser(new User(userId));
		comment.setText(text);
		comment.setTime(new Date());
		
		if(!puserName.equals("undefined")) {
			User puser = this.userService.findUserByName(puserName);
			comment.setPuser(puser);
			
			userService.haveNewAboutMe(puser.getUserId());
		}
		
		Integer result = commentDao.createCommentForMessage(comment);
		sqlSession.commit();	
		
		if(result != null) {
			messageService .commentThisMessage(Integer.valueOf(messageId));
		}
		
		return result;
	}
	@Override
	public void deleteComment(Integer id) {
		commentDao.deleteComment(id);
		sqlSession.commit();
		
	}
	@Override
	public Comment findCommentById(Integer id) {
		return commentDao.findCommentById(id);
	}
	@Override
	public List<Comment> findCommentsByUserId(Integer userId,
			Integer currentPage, Integer pageSize) {
		return commentDao.findCommentsByUserId(userId, currentPage, pageSize);
	}
	@Override
	public List<Comment> findCommentsByMessageId(Integer messageId,
			Integer currentPage, Integer pageSize) {
		return commentDao.findCommentsByMessageId(messageId, currentPage, pageSize);
	}

}
