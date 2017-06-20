package service.Impl;

import org.apache.ibatis.session.SqlSession;

import service.ITouristService;
import util.MyBatisUtil;
import dao.userdao.IUserDao;
import entity.User;

public class TouristServiceImpl implements ITouristService {
	private static IUserDao userdao;
	private static SqlSession sqlSession;
	
	static {
		sqlSession = MyBatisUtil.getSqlSession();
		userdao = sqlSession.getMapper(IUserDao.class);
	}
	
	public void TouristServiceImpl(){
		
	}
	
	public void register(User user) {
		userdao.insertUser(user);
		sqlSession.commit();
	}


}
