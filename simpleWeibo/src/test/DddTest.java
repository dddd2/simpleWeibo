package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.commentdao.ICommentDao;
import dao.messagedao.IMessageDao;
import dao.userdao.IUserDao;
import entity.Comment;
import entity.Message;
import entity.User;
import util.MyBatisUtil;

public class DddTest {
	private SqlSession sqlSession;
	private IUserDao dao;
	private ICommentDao commentDao;
	private IMessageDao messageDao;
	@Before
	public void before() {
		sqlSession = MyBatisUtil.getSqlSession();
		
		dao = sqlSession.getMapper(IUserDao.class);
		commentDao = sqlSession.getMapper(ICommentDao.class);
		messageDao = sqlSession.getMapper(IMessageDao.class);
	}
	
	@After
	public void after() {
		MyBatisUtil.closeSqlSession(sqlSession);
	}
	
	@Test
	public void test() {
		User user = new User();
		user.setName("ddd");
		dao.insertUser(user);
		sqlSession.commit();
	}
	
	@Test
	public void test1() {
		User user = new User();
		user.setUserId(7);
		user.setName("sss");
		dao.updateUser(user);
		sqlSession.commit();
	}
	
	@Test
	public void test3() {
		User stu = dao.findUserById(7);
		System.out.println(stu);
	}
	@Test
	public void test4() {
		List<User> users = dao.findAllUsers();
		System.out.println(users);
	}
	@Test
	public void test5() {
		Message message = new Message();
		message.setText("ssss");
		message.setDate(new Date());
		User user = new User();
		user.setUserId(7);
		message.setUser(user);
		messageDao.createMessage(message);
		sqlSession.commit();
	}
	
	@Test
	public void test6() {
		Comment comment = new Comment();
		Message message = new Message();
		User user = new User();
		User pUser = new User();
		pUser.setUserId(8);
		user.setUserId(7);
		System.out.println(user.getUserId());
		message.setMessageId(1);
		comment.setMessage(message);
		comment.setText("dddsds");
		comment.setUser(user);
		comment.setPuser(pUser);
		commentDao.createCommentForMessage(comment);
		sqlSession.commit();
		
	}
	
	@Test
	public void test7() {
		Comment comment = commentDao.findCommentById(7);
		System.out.println(comment);
	}
	@Test
	public void test8() {
		commentDao.findCommentsByUserId(7, 0, 1);
	}
	@Test
	public void test9() {
		commentDao.findCommentsByMessageId(1, 0, 5);
	}
	@Test
	public void test10() {
		messageDao.findMessagesByUserId(7);
	}
}
