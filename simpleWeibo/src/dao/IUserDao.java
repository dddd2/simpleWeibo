package dao;

import java.util.List;

import entity.User;

public interface IUserDao {
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(Integer id);
	public User findUserById(Integer id);
	public List<User> findAllUsers();
}
