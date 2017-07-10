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
	 * @param userId
	 */
	public void deleteUser(Integer userId);
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
	/**
	 * 查找用户用姓名
	 * @param name
	 * @return
	 */
	public User findUserByName(String name);
	/**
	 * 新的@我的
	 * @param userId
	 */
	public void haveNewAboutMe(Integer userId);
	/**
	 * 读过@我的
	 * @param userId
	 */
	public void cleanAboutMe(Integer userId);
	/**
	 * 取关
	 * @param fansId
	 * @param focusPeopleId
	 */
	public void takeOf(
			@Param("fansId")Integer fansId, 
			@Param("focusPeopleId")Integer focusPeopleId);
	/**
	 * 关注
	 * @param fansId
	 * @param focusPeopleId
	 */
	public void focusOn(
			@Param("fansId")Integer fansId, 
			@Param("focusPeopleId")Integer focusPeopleId);
	/**
	 * 涨粉
	 * @param userId
	 */
	public void increaseFans(Integer userId);
	/**
	 * 掉粉
	 * @param userId
	 */
	public void reduceFans(Integer userId);
	/**
	 * 增加关注
	 * @param userId
	 */
	public void increaseFocusPeople(Integer userId);
	/**
	 * 减少关注
	 * @param userId
	 */
	public void reduceFocusPeople(Integer userId);
	/**
	 * 查看是否已经关注了此人
	 * @param fansId
	 * @param focusPeopleId
	 * @return 没有 -- null  有 -- id;
	 */
	public Integer isFocus(
			@Param("fansId")Integer fansId, 
			@Param("focusPeopleId")Integer focusPeopleId);
	
	public User findUserByIdChange(Integer userId);
	public Integer findTotalUsers();
}
