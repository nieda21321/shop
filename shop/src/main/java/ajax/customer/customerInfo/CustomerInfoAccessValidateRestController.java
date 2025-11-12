package ajax.customer.customerInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.CustomerDao;
import dto.Customer;

/**
 * Servlet implementation class customerInfoAccessValidateRestController
 */
@WebServlet("/customer/manage/customerInfoAccessValidate")
public class CustomerInfoAccessValidateRestController extends HttpServlet {
       

	private CustomerDao customerDao;
	
	/**
	 * 2025. 11. 10.
	 * Author - tester
	 * 고객 로그인 - 메인페이지 - 개인정보관리 - 사용확인창
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int sessionCustomerCode = customer.getCustomerCode();
		
		String customerPw = request.getParameter("customerPw");
		
		customerDao = new CustomerDao();
		String userChk = customerDao.customerInfoAccessValidate(sessionCustomerCode, customerPw);
		
		Map<String, String> result = new HashMap<>();
		
		if ( userChk.equals(customerPw) ) {
			
			System.out.println("customerInfoAccessValidate SUCCESS");
	        result.put("result", "SUCCESS");
		} else {
			
			System.out.println("customerInfoAccessValidate FAILED");
			result.put("result", "FAIL");
		}
		
		Gson gson = new Gson();
        String json = gson.toJson(result);
        response.getWriter().write(json);
	}
}
