package controller.emp.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 */
@WebServlet("/customer/customerLogout")
public class CustomerLogoutController extends HttpServlet {
    
	 /**
	  * 2025. 11. 04.
	  * author - tester
	  * 고객 로그아웃 세션처리
	  */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/home");
	}
}
