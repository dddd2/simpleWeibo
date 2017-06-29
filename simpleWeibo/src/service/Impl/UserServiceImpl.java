package service.Impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import dao.userdao.IUserDao;
import entity.User;
import service.ITouristService;
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
		ITouristService NewUser = new TouristServiceImpl();
		NewUser.register(user);
	}
	
	@Override
	public void updateUser(User user) {
		userdao.updateUser(user);
		sqlSession.commit();
		
	}
	
	@Override
	public void deleteUser(User user) {
		userdao.deleteUser(user);
		sqlSession.commit();
	}
	
	@Override
	public User findUserById(Integer id) {
		return userdao.findUserById(id);
	}
	
	@Override
	public User findUserByPhone(String phone) {
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
		return userdao.findAllUsers();
	}

	@Override
	public List<User> findFansByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		return userdao.findFansByUserId(userId, currentPage, pageSize);
	}

	@Override
	public List<User> findFocusPeoplesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		return userdao.findFocusPeoplesByUserId(userId, currentPage, pageSize);
	}
	
}
