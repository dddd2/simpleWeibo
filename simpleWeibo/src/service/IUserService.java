package service;

import java.util.List;

import entity.User;

public interface IUserService {
	/**
	 * 新建用户
	 * @param user
	 */
	public void createUser(User user);
	/**
	 * 修改用户
	 * @param user
	 */
	public void updateUser(User user);
	/**
	 * 删除用户
	 * @param user
	 */
	public void deleteUser(User user);
	/**
	 * 根据id查找指定用户
	 * @param id
	 * @return
	 */
	public User findUserById(Integer id);
	/**
	 * 查找所有用户
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findUsers(Integer currentPage, Integer pageSize);
	/**
	 * 登录
	 * @param phone
	 * @param password
	 * @return
	 */
	public User login(String phone, String password);
	/**
	 * 根据手机号查找用户
	 * @param phone
	 * @return
	 */
	public User findUserByPhone(String phone);
	/**
	 * 根据用户id查找粉丝
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findFansByUserId(Integer userId, Integer currentPage, Integer pageSize);
	/**
	 * 根据用户id查找关注人
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findFocusPeoplesByUserId(Integer userId, Integer currentPage, Integer pageSize);
}
