package test;

import java.io.File;
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
import util.ImageUtils;
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
//		List<User> users = dao.findAllUsers();
//		System.out.println(users);
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
		List<Message> list = messageDao.findFocusMessagesByUserId(11, 0, 20);
		for(Message message : list) {
			System.out.println(message);
		}
		
		commentDao.createCommentForMessage(new Comment());
		sqlSession.commit();
		List<Message> list1 = messageDao.findFocusMessagesByUserId(11, 0, 20);
		for(Message message : list1) {
			System.out.println(message);
		}
	}
	@Test
	public void test11() {
		User user = dao.findUserByName("管理员");
		System.out.println(user);
	}
	
	@Test
	public void test12() {
		List<User> list = dao.findAllUsers(null, null);
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void test13() {
		File file = new File("D:/2017-07-03/1499064688694.jpg");
		System.out.println(ImageUtils.img2Base64(file));
	}
	
	@Test
	public void test14() {
		System.out.println(new User().getName().charAt(0));
	}
	@Test
	public void test15() {
		System.out.println(dao.findUserById(11));
		System.out.println(dao.findUserById(10));
	}
}
