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

import entity.Message;
import service.IMessageService;
import service.Impl.MessageServiceImpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private IMessageService messageService = new MessageServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		
		if(method.equals("createMessage")) {
			String newMessage = request.getParameter("newMessage");
			String userId = request.getParameter("userId");
			String imgs = request.getParameter("imgs");
			String keywords = request.getParameter("keywords");
			
			Integer messageId = this.messageService.createMessage(newMessage, userId, imgs, keywords);
			
			out.print(messageId != 0);
		} else if(method.equals("findFocusMessagesByUserId")) {
			String userId = request.getParameter("userId");
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			List<Message> list = null;
			
			if(currentPage == null && pageSize == null) {
				list = this.messageService.findFocusMessagesByUserId(
						Integer.valueOf(userId), null, null);
			} else {
				list = this.messageService.findFocusMessagesByUserId(
						Integer.valueOf(userId),
						Integer.valueOf(currentPage),
						Integer.valueOf(pageSize));
			}
			
			out.print(JSON.toJSON(list));
		} else if(method.equals("loveMessage")) {
			String userId = request.getParameter("userId");
			String messageStr = request.getParameter("message");
			
			Message message = JSON.parseObject(messageStr, Message.class);
			
			Integer result = this.messageService.loveMessage(message, Integer.valueOf(userId));
			
			out.print(result);
		} else if(method.equals("findAboutMeMessagesByUserId")) {
			String userId = request.getParameter("userId");
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			String userName = request.getParameter("userName");
			List<Message> list = null;
			
			if(currentPage == null && pageSize == null) {
				list = this.messageService.findAboutMeMessagesByUserId(
						Integer.valueOf(userId), userName, null, null);
			} else {
				list = this.messageService.findAboutMeMessagesByUserId(
						Integer.valueOf(userId), userName,
						Integer.valueOf(currentPage),
						Integer.valueOf(pageSize));
			}
			
			out.print(JSON.toJSON(list));
		} else if(method.equals("findAllMessages")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			
			List<Message> list = null;
			
			if(currentPage == null && pageSize == null) {
				list = this.messageService.findAllMessages(null, null);
			} else {
				list = this.messageService.findAllMessages(
						Integer.valueOf(currentPage), 
						Integer.valueOf(pageSize));
			}
			
			Integer total = this.messageService.findAllTotalNum();
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", total);
			map.put("data", list);
			out.print(JSON.toJSON(map));
		} else if(method.equals("findMessageByMessageId")) {
			String messageId = request.getParameter("messageId");
			
			Message message = 
					this.messageService.findMessageById(Integer.valueOf(messageId));
			
			out.print(JSON.toJSON(message));
		} else if(method.equals("deleteMessage")) {
			String messageId = request.getParameter("messageId");
			
			this.messageService.deleteMessage(Integer.valueOf(messageId));
		} else if(method.equals("findMessagesByUserId")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			String userId = request.getParameter("userId");
			
			List<Message> list = null;
			if(currentPage == null && pageSize == null) {
				list = this.messageService.findMessagesByUserId(
						Integer.valueOf(userId), null, null);
			} else {
				list = this.messageService.findMessagesByUserId(
						Integer.valueOf(userId), 
						Integer.valueOf(currentPage), 
						Integer.valueOf(pageSize));
			}
			
			out.print(JSON.toJSON(list));
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
