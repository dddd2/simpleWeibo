package service;

import java.util.List;

import entity.User;

public interface IUserService {
	public void createUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User findUserById(Integer id);
	public List<User> findUsers();
}
