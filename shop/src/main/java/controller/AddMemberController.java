package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.CustomerDao;

/**
 * 
 */
@WebServlet("/out/addMember")
public class AddMemberController extends HttpServlet {

	/**
	 * 2025. 11. 03.
	 * 
	 * addMemberform
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//addMember.jsp
		request.getRequestDispatcher("/WEB-INF/view/out/addMember.jsp").forward(request, response);
	}

	/**
	 * 2025. 11. 03.
	 * 
	 * addMemberAction
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		 String customerId = request.getParameter("customerId");
		 String customerPw = request.getParameter("customerPw");
		 String customerName = request.getParameter("customerName");
		 String customerPhone = request.getParameter("customerPhone");
		 String address = request.getParameter("address");
		
		List<Map<String, Object>> addMemberList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> member = new HashMap<String, Object>();
		member.put("customerId", customerId);
		member.put("customerPw", customerPw);
		member.put("customerName", customerName);
		member.put("customerPhone", customerPhone);
		member.put("point", 0);
		member.put("address", address);
		addMemberList.add(member);
		
		CustomerDao customerDao = new CustomerDao(); 
		
		try {
			
			int row = customerDao.insertCustomer(addMemberList);
			
			if ( row > 1) {
				
				System.out.println("addMember SUCCESS");
				response.sendRedirect(request.getContextPath() + "/out/login");	
			} else {
				
				System.out.println("addMember SQL init FAILED");
				response.sendRedirect(request.getContextPath() + "/out/login");
			}
		} catch (Exception e) {
			
			System.out.println("addMember FAILED");
			e.printStackTrace();
		}
	}
}
