package dao.userdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.User;

public interface IUserDao {
	/**
	 * 新建一个用户
	 * @param user
	 */
	public void insertUser(User user);
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
	 * 查找全部用户
	 * @return
	 */
	public List<User> findAllUsers(
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	/**
	 * 根据手机号查找用户
	 * @param phone
	 * @return
	 */
	public User findUserByPhone(String phone);
	/**
	 * 查找用户的fans
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findFansByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
	/**
	 * 查找用户的关注人
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<User> findFocusPeoplesByUserId(
			@Param("userId")Integer userId,
			@Param("currentPage")Integer currentPage,
			@Param("pageSize")Integer pageSize);
}
