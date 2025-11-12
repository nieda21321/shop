package ajax.customer.customerInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dao.CustomerDao;
import dto.Customer;

/**
 * Servlet implementation class CustomerInfo
 */
@WebServlet("/customer/manage/customerInfo")
public class CustomerInfoListRestController extends HttpServlet {
	
	private CustomerDao customerDao;

	/**
	 * 
	 * 2025. 11. 10.
	 * Author - tester
	 * 고객로그인 - 메인페이지 - 개인정보관리
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int sessionCustomerCode = customer.getCustomerCode();
		
		List<Map<String, Object>> customerInfoList = new ArrayList<Map<String,Object>>();
		
		customerDao = new CustomerDao();
		customerInfoList = customerDao.selectCustomerInfoList(sessionCustomerCode);
		
		Gson gson = new Gson();
        String json = gson.toJson(customerInfoList);
        response.getWriter().write(json);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
