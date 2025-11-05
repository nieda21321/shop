package controller.emp.customerManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.CustomerDao;
import dto.Outid;

/**
 * 
 */
@WebServlet("/emp/removeCustomerByEmp")
public class RemoveCustomerByEmpController extends HttpServlet {

	private CustomerDao customerDao;
	
	
	/**
	 * 2025. 11. 05.
	 * Author - tester
	 * 고객 계정 탈퇴 처리 화면
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/emp/removeCustomerByEmp.jsp").forward(request, response);
	}

}
