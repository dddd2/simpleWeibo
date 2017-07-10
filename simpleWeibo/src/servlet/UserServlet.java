package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import entity.User;
import service.IUserService;
import service.Impl.UserServiceImpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private IUserService userService = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		
		if(method.equals("login")) {
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			
			User user = this.userService.login(phone, password);
			
			out.print(JSON.toJSON(user));
		} else if(method.equals("findUserById")) {
			String userId = request.getParameter("userId");
			
			User user = this.userService.findUserById(Integer.valueOf(userId));
			
			out.print(JSON.toJSON(user));
		} else if(method.equals("findFansByUserId")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			String userId = request.getParameter("userId");
			List<User> list;
			if(currentPage == null && pageSize == null) {
				list = this.userService.findFansByUserId(
						Integer.valueOf(userId), null, null);
			} else {
				list = this.userService.findFansByUserId(
							Integer.valueOf(userId), 
							Integer.valueOf(currentPage), 
							Integer.valueOf(pageSize));
			}
			out.print(JSON.toJSON(list));
		} else if(method.equals("findFocusPeoplesByUserId")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			String userId = request.getParameter("userId");
			List<User> list;
			if(currentPage == null && pageSize == null) {
				list = this.userService.findFocusPeoplesByUserId(
						Integer.valueOf(userId), null, null);
			} else {
				list = this.userService.findFocusPeoplesByUserId(
							Integer.valueOf(userId), 
							Integer.valueOf(currentPage), 
							Integer.valueOf(pageSize));
			}
			out.print(JSON.toJSON(list));
		} else if(method.equals("cleanAboutMe")) {
			String userId = request.getParameter("userId");
			
			this.userService.cleanAboutMe(Integer.valueOf(userId));
			
		} else if(method.equals("isFocus")) {
			String fansId = request.getParameter("fansId");
			String focusPeopleId = request.getParameter("focusPeopleId");
			
			Integer result = this.userService.isFocus(
					Integer.valueOf(fansId), 
					Integer.valueOf(focusPeopleId));
			
			out.print(result != null);
		} else if(method.equals("focusOn")) {
			String fansId = request.getParameter("fansId");
			String focusPeopleId = request.getParameter("focusPeopleId");
			
			this.userService.focusOn(
					Integer.valueOf(fansId), 
					Integer.valueOf(focusPeopleId));
		} else if(method.equals("takeOf")) {
			String fansId = request.getParameter("fansId");
			String focusPeopleId = request.getParameter("focusPeopleId");
			
			this.userService.takeOf(
					Integer.valueOf(fansId), 
					Integer.valueOf(focusPeopleId));
		} else if(method.equals("changePassword")) {
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			
			boolean result = this.userService.changePassword(
					Integer.valueOf(userId),password,newPassword);
			
			out.print(result);
		} else if(method.equals("updateUser")) {
			String userStr = request.getParameter("user");
			
			User user = JSON.parseObject(userStr, User.class);
			
			this.userService.updateUser(user);
		} else if(method.equals("findAllUsers")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			
			List<User> list = this.userService.findUsers(
					Integer.valueOf(currentPage), 
					Integer.valueOf(pageSize));
			
			Integer total = this.userService.findTotalUsers();
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", total);
			map.put("data", list);
			
			out.print(JSON.toJSON(map));
					
		} else if(method.equals("managerAdmin")) {
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			
			User user = this.userService.manageAdmin(phone, password);
			
			out.print(JSON.toJSON(user));
		} else if(method.equals("deleteUser")) {
			String userId = request.getParameter("userId");
			
			this.userService.deleteUser(Integer.valueOf(userId));
		} else if(method.equals("creatUser")) {
			String userStr = request.getParameter("user");
			
			User user = JSON.parseObject(userStr, User.class);
			this.userService.createUser(user);
		}
		 
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
