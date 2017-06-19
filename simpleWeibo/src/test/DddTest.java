package test;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.commentdao.ICommentDao;
import dao.messagedao.IMessageDao;
import dao.userdao.IUserDao;
import entity.Message;
import entity.User;
import util.MyBatisUtil;

public class DddTest {
	private SqlSession sqlSession;
	private IUserDao dao;
//	private ICommentDao commentDao;
	private IMessageDao messageDao;
	@Before
	public void before() {
		sqlSession = MyBatisUtil.getSqlSession();
		
		dao = sqlSession.getMapper(IUserDao.class);
//		commentDao = sqlSession.getMapper(ICommentDao.class);
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
}
