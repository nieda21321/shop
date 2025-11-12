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
 * Servlet implementation class CustomerInfoUpdateRestController
 */
@WebServlet("/customer/manage/customerInfoUpdate")
public class CustomerInfoUpdateRestController extends HttpServlet {

	
	private CustomerDao customerDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * 
	 * 2025. 11. 11.
	 * Author - tester
	 * 고객로그인 - 메인페이지 - 개인정보관리 - 수정하기 기능 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("application/json; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int sessionCustomerCode = customer.getCustomerCode();
		String sessionCustomerPw = customer.getCustomerPw();
		
		
		// 업데이트 받아 오는 것들
		String customerPw = request.getParameter("customerPw");
		String customerPhone = request.getParameter("customerPhone");
		String address = request.getParameter("address");
		
		// customerPwChg , customerPhoneChg, customerAddressChg
		Map<String, String> modifyCustomerInfo = new HashMap<String, String>();
		
		modifyCustomerInfo.put("customerPwChg", customerPw);
		modifyCustomerInfo.put("customerPhoneChg", customerPhone);
		modifyCustomerInfo.put("customerAddressChg", address);

		// 비밀번호가 바뀌었을 경우 세션 값 업데이트 처리
		if (!(sessionCustomerPw.equals(customerPw))) {
			
			System.out.println("PW CHG CHK");
			customer.setCustomerPw(customerPw);
			session.setAttribute("loginCustomer", customer);
		}
		
		customerDao = new CustomerDao();
		int row = customerDao.updateCustomerInfo(sessionCustomerCode, sessionCustomerPw, modifyCustomerInfo);
		
		Map<String, String> result = new HashMap<String, String>();
		
		String transChk = (row > 0) ? "SUCCESS" : "FAIL";
		
		result.put("result", transChk);
		
		Gson gson = new Gson();
		gson.toJson(result, response.getWriter());
	}

}
