package service;

import java.util.List;

import entity.User;

public interface IUserService {
	public void createUser();
	public void updateUser();
	public void deleteUser();
	public User findUserById();
	public List<User> findUsers();
}
