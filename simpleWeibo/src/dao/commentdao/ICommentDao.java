package dao.commentdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Comment;

public interface ICommentDao {
	public void createCommentForMessage(Comment comment);
	public void deleteComment(Integer id);
	public Comment findCommentById(Integer id);
	public List<Comment> findCommentsByUserId(
			@Param("userId")Integer userId, 
			@Param("currentPage")Integer currentPage, 
			@Param("pageSize")Integer pageSize);
	public List<Comment> findCommentsByMessageId(
			@Param("messageId")Integer messageId, 
			@Param("currentPage")Integer currentPage, 
			@Param("pageSize")Integer pageSize);
}
