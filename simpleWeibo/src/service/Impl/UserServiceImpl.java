package service.Impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import dao.userdao.IUserDao;
import entity.User;
import service.IUserService;
import util.MyBatisUtil;

public class UserServiceImpl implements IUserService {
	private static SqlSession sqlSession;
	private static IUserDao userdao;

	static {
		sqlSession = MyBatisUtil.getSqlSession();
		userdao = sqlSession.getMapper(IUserDao.class);
	}
	
	public void createUser(User user) {
		// TODO Auto-generated method stub
		TouristServiceImpl NewUser = new TouristServiceImpl();
		NewUser.register(user);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userdao.updateUser(user);
		sqlSession.commit();
		
	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		userdao.deleteUser(user);
		sqlSession.commit();
	}

	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return userdao.findUserById(id);
	}

	public List<User> findUsers() {
		// TODO Auto-generated method stub
		return userdao.findAllUsers();
	}
	
}
