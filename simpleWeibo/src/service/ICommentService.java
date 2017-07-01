package service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Comment;

public interface ICommentService {
	public Integer createCommentForMessage(Integer messageId, 
			Integer userId, String puserName, String text);
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
