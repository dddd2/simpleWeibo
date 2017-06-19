package test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.userdao.IUserDao;
import entity.User;
import util.MyBatisUtil;

public class DddTest {
	private SqlSession sqlSession;
	private IUserDao dao;

	@Before
	public void before() {
		sqlSession = MyBatisUtil.getSqlSession();
		
		dao = sqlSession.getMapper(IUserDao.class);
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
}
