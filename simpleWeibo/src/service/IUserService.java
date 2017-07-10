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
	 * @param integer
	 */
	public void deleteUser(Integer integer);
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
	public List<User> findFansByUserId(Integer userId, 
			Integer currentPage, Integer pageSize);
	/**
	 * 根据用户id查找关注人
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findFocusPeoplesByUserId(Integer userId, 
			Integer currentPage, Integer pageSize);
	
	public void haveNewAboutMe(Integer userId);
	
	public void cleanAboutMe(Integer userId);
	
	public User findUserByName(String name);
	/**
	 * 关注
	 * @param fansId
	 * @param focusPeopleId
	 */
	public void focusOn(Integer fansId, Integer focusPeopleId);
	/**
	 * 取关
	 * @param fansId
	 * @param focusPeopleId
	 */
	public void takeOf(Integer fansId, Integer focusPeopleId);
	/**
	 * 查看fan是否关注了focuspeople
	 * @param fansId
	 * @param focusPeopleId
	 * @return NULL==没有关注  Integer==关注了
	 */
	public Integer isFocus(Integer fansId, Integer focusPeopleId);
	
	public boolean changePassword(Integer userId, String password, String newPassword);
	public Integer findTotalUsers();
	public User manageAdmin(String phone, String password);
}
