package service.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import service.ICommentService;
import util.MyBatisUtil;
import dao.commentdao.ICommentDao;
import dao.messagedao.IMessageDao;
import entity.Comment;

public class CommentServiceImpl implements ICommentService{
	private static ICommentDao commentDao;
	private static SqlSession sqlSession;
	
	static {
		sqlSession = MyBatisUtil.getSqlSession();
		commentDao = sqlSession.getMapper(ICommentDao.class);
	}

	public void createCommentForMessage(Comment comment) {
		// TODO Auto-generated method stub
		commentDao.createCommentForMessage(comment);
		sqlSession.commit();	
	}

	public void deleteComment(Integer id) {
		// TODO Auto-generated method stub
		commentDao.deleteComment(id);
		sqlSession.commit();
		
	}

	public Comment findCommentById(Integer id) {
		// TODO Auto-generated method stub
		return commentDao.findCommentById(id);
	}

	public List<Comment> findCommentsByUserId(Integer userId,
			Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return commentDao.findCommentsByUserId(userId, currentPage, pageSize);
	}

	public List<Comment> findCommentsByMessageId(Integer messageId,
			Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return commentDao.findCommentsByMessageId(messageId, currentPage, pageSize);
	}

}
