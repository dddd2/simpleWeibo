package dao.commentdao;

import java.util.List;

import entity.Comment;

public interface ICommentDao {
	public void createComment();
	public void deleteComment();
	public Comment findCommentById(Integer id);
	public List<Comment> findCommentsByUserId(Integer userId);
}
