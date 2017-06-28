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
	
	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		TouristServiceImpl NewUser = new TouristServiceImpl();
		NewUser.register(user);
	}
	
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userdao.updateUser(user);
		sqlSession.commit();
		
	}
	
	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		userdao.deleteUser(user);
		sqlSession.commit();
	}
	
	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return userdao.findUserById(id);
	}
	
	@Override
	public User findUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return userdao.findUserByPhone(phone);
	}
	
	@Override
	public User login(String phone, String password) {
		User user = userdao.findUserByPhone(phone);
		
		if(password.equals(user.getPassword())) {
			return user;
		}
		
		return null;
	}
	
	@Override
	public List<User> findUsers() {
		// TODO Auto-generated method stub
		return userdao.findAllUsers();
	}
	
}
