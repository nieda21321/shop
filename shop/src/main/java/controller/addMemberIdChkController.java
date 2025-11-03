package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.CustomerDao;

/**
 * Servlet implementation class addMemberIdChkController
 */
@WebServlet("/out/checkId")
public class addMemberIdChkController extends HttpServlet {
	
	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       
			String customerId = request.getParameter("customerId");
	        CustomerDao customerDao = new CustomerDao();
	        boolean exists = false;

	        try {
	            
	        	exists = customerDao.addMemberIdChk(customerId);
	        } catch (Exception e) {
	            
	        	System.out.println("회원가입 아이디 체크 예외 오류 발생");
	        	e.printStackTrace();
	        }

	        response.setContentType("text/plain;charset=UTF-8");
	        if (exists) {
	        	
	            response.getWriter().write("exists"); // 이미 존재
	        } else {
	        	
	            response.getWriter().write("available"); // 사용 가능
	        }
	    }
}
