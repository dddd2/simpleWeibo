package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ICommentService;
import service.IMessageService;
import service.Impl.CommentServiceImpl;
import service.Impl.MessageServiceImpl;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ICommentService commentService = new CommentServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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
		
		if(method.equals("submitComment")) {
			String puserName = request.getParameter("puserName");
			String userId = request.getParameter("userId");
			String comment = request.getParameter("comment");
			String messageId = request.getParameter("messageId");
			
			Integer result = this.commentService.createCommentForMessage(
					Integer.valueOf(messageId),
					Integer.valueOf(userId), puserName, comment);
			
			out.print(result != 0);
		}
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
