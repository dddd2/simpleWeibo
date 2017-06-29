package service;

import java.util.List;

import entity.User;

public interface IUserService {
	public void createUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User findUserById(Integer id);
	public List<User> findUsers();
	public User login(String phone, String password);
	public User findUserByPhone(String phone);
	public List<User> findFansByUserId(Integer userId, Integer currentPage, Integer pageSize);
	public List<User> findFocusPeoplesByUserId(Integer userId, Integer currentPage, Integer pageSize);
}
