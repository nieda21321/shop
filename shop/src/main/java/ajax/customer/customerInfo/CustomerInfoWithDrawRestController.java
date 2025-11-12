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
import dto.Outid;

/**
 * Servlet implementation class CustomerInfoRemoveRestController
 */
@WebServlet("/customer/manage/customerWithdraw")
public class CustomerInfoWithDrawRestController extends HttpServlet {
	
	private CustomerDao customerDao;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 
	 * 2025. 11. 10.
	 * Author - tester
	 * 고객로그인 - 메인페이지 - 개인정보관리 - 회원탈퇴
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int sessionCustomerCode = customer.getCustomerCode();
		String sessionCustomerPw = customer.getCustomerPw();
		
		String outId = request.getParameter("outId");
		String memo = request.getParameter("memo");
		
		// outId, id, memo
		Outid oi = new Outid();
		oi.setId(outId);
		oi.setMemo(memo);
		
		customerDao = new CustomerDao();
		int row = customerDao.removeCustomerByCustomer(sessionCustomerCode, sessionCustomerPw, oi);
		
		Map<String, String> result = new HashMap<String, String>();
		String withDrawChk = ( row > 0 ) ? "SUCCESS" : "FAIL"; 
		
		result.put("result", withDrawChk);
		
		Gson gson = new Gson();
		gson.toJson(result, response.getWriter());
		
		if ( row > 0 ) {
			
			session.invalidate();
		}
	}
}
