package dao.userdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.User;

public interface IUserDao {
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User findUserById(Integer id);
	public List<User> findAllUsers();
	public User findUserByPhone(String phone);
	public List<User> findFansByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	public List<User> findFocusPeoplesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
}
