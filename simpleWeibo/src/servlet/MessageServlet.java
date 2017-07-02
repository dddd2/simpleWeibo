package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import entity.Message;
import net.sf.json.JSONArray;
import service.IMessageService;
import service.Impl.MessageServiceImpl;
import sun.misc.BASE64Decoder;

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
		
		if(method.equals("findAllMessages")) {
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			
			List<Message> list = this.messageService.findAllMessages(
					Integer.valueOf(currentPage),
					Integer.valueOf(pageSize));
	
			for (Message message : list) {
				System.out.println(message);
			}
			JSONArray resultList = JSONArray.fromObject(list);
			
			out.print(resultList);
		} else if(method.equals("createMessage")) {
			String newMessage = request.getParameter("newMessage");
			String userId = request.getParameter("userId");
			String imgs = request.getParameter("imgs");
			String keywords = request.getParameter("keywords");
			System.out.println(imgs);
//			JSONObject imgsObj = JSONObject.parseObject(imgs);
			JSONObject imgsObj = JSON.parseObject(imgs);
			System.out.println(imgsObj);
			System.out.println(keywords);
			String[] keys = keywords.split(",");
			for(String key : keys) {
				String text = (String)((JSONObject)imgsObj.get(key)).get("imgSrc");
				System.out.println(text);
				
				GenerateImage(text);
			}
			Integer messageId = this.messageService.createMessage(newMessage, userId);
			
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

	  /** 
     * @Title: GenerateImage 
     * @Description: TODO(base64字符串转化成图片) 
     * @param imgStr 
     * @return 
     */  
    public static boolean GenerateImage(String imgStr) {  
    	System.out.println(imgStr);
        if (imgStr == null) // 图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
            // Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for (int i = 0; i < b.length; ++i) {  
                if (b[i] < 0) {// 调整异常数据  
                    b[i] += 256;  
                }  
            }  
            // 生成jpeg图片  
            String imgFilePath = "d://" + new Date().getTime() + ".jpg";  
            OutputStream out = new FileOutputStream(imgFilePath);  
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }  
}
