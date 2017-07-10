package service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.userdao.IUserDao;
import entity.User;
import service.ITouristService;
import service.IUserService;
import util.ImageUtils;
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
		String touxiang = user.getTouxiang();
		
		if(touxiang != null) {
			Date today = new Date();
			String path ="D:/weiboPics/touxiang/" + new SimpleDateFormat("yyyy-MM-dd").format(today) + "/" + today.getTime() + ".jpg";
			
			ImageUtils.base64ToImg(touxiang, path);
			user.setTouxiang(path);
		}
		
		userdao.updateUser(user);
		sqlSession.commit();
		
	}
	
	@Override
	public void deleteUser(Integer userId) {
		userdao.deleteUser(userId);
		sqlSession.commit();
	}
	
	@Override
	public User findUserById(Integer id) {
		User user = userdao.findUserById(id);
		
		return parseTouxiang(user);
	}
	
	@Override
	public User findUserByPhone(String phone) {
		return userdao.findUserByPhone(phone);
	}
	
	@Override
	public User login(String phone, String password) {
		User user = userdao.findUserByPhone(phone);
		
		if(user != null) {
			if(password.equals(user.getPassword())) {
				return user;
			}
		}
		
		return null;
	}
	
	@Override
	public List<User> findFansByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		if(currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		List<User> list = userdao.findFansByUserId(userId, currentPage, pageSize);
		
		return parseTouxiang(list);
	}

	@Override
	public List<User> findFocusPeoplesByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		if(currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		List<User> list = userdao.findFocusPeoplesByUserId(userId, currentPage, pageSize);
		
		return parseTouxiang(list);
	}

	@Override
	public List<User> findUsers(Integer currentPage, Integer pageSize) {
		if(currentPage != null) {
			currentPage = (currentPage - 1) * pageSize;
		}
		List<User> list = userdao.findAllUsers(currentPage, pageSize);
		
		return parseTouxiang(list);
	}

	@Override
	public void haveNewAboutMe(Integer userId) {
		userdao.haveNewAboutMe(userId);
		
		sqlSession.commit();
	}

	@Override
	public void cleanAboutMe(Integer userId) {
		userdao.cleanAboutMe(userId);
		
		sqlSession.commit();
	}

	@Override
	public User findUserByName(String name) {
		return userdao.findUserByName(name);
	}

	@Override
	public void focusOn(Integer fansId, Integer focusPeopleId) {
		userdao.focusOn(fansId, focusPeopleId);
		userdao.increaseFans(focusPeopleId);
		userdao.increaseFocusPeople(fansId);
		
		sqlSession.commit();
	}

	@Override
	public void takeOf(Integer fansId, Integer focusPeopleId) {
		userdao.takeOf(fansId, focusPeopleId);
		userdao.reduceFans(focusPeopleId);
		userdao.reduceFocusPeople(fansId);
		
		sqlSession.commit();
	}

	@Override
	public Integer isFocus(Integer fansId, Integer focusPeopleId) {
		return userdao.isFocus(fansId, focusPeopleId);
	}

	@Override
	public boolean changePassword(Integer userId, String password, String newPassword) {
		User user = userdao.findUserByIdChange(userId);
		
		if(!password.equals(user.getPassword())) {
			return false;
		}
		
		user.setPassword(newPassword);
		user.setTouxiang(null);
		this.updateUser(user);
		
		return true;
	}
	
	private User parseTouxiang(User user) {
		String touxiang = user.getTouxiang();
		
		if(touxiang != null) {
			if(!touxiang.equals("") && touxiang.charAt(0) == 'D') {
				String newTouxiang = ImageUtils.img2Base64(new File(touxiang));
				user.setTouxiang(newTouxiang);
			}
		
		}
		return user;
	}
	
	private List<User> parseTouxiang(List<User> list) {
		for(User user : list) {
			String touxiang = user.getTouxiang();
			
			if(touxiang != null) {
				if(!touxiang.equals("") && touxiang != null && touxiang.charAt(0) == 'D') {
					String newTouxiang = ImageUtils.img2Base64(new File(touxiang));
					user.setTouxiang(newTouxiang);
				}
			}
		}
		
		return list;
	}

	@Override
	public Integer findTotalUsers() {
		return userdao.findTotalUsers();
	}

	@Override
	public User manageAdmin(String phone, String password) {
		User user = userdao.findUserByPhone(phone);
		
		if(user != null && user.getType() != null) {
			if(password.equals(user.getPassword())) {
				return user;
			}
		}
		
		return null;
	}
}
