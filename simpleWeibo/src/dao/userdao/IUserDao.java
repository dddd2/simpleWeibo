package dao.userdao;

import java.util.List;

import entity.User;

public interface IUserDao {
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User findUserById(Integer id);
	public List<User> findAllUsers();
	public User findUserByPhone(String phone);
}
