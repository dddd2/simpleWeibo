package dao.commentdao;

import java.util.List;

import entity.Comment;

public interface ICommentDao {
	public void createCommentForMessage(Comment comment);
	public void deleteComment(Integer id);
	public Comment findCommentById(Integer id);
	public List<Comment> findCommentsByUserId(Integer userId);
}
