package controller.emp.customerManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.CustomerDao;
import dto.Customer;

/**
 * Servlet implementation class CustomerListController
 */
@WebServlet("/emp/customerList")
public class CustomerListController extends HttpServlet {
       
	private CustomerDao customerDao;
	

	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 관리자 - 고객관리 리스트 페이징 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request
		int currentPage = 1;
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		int lastPage = 0;
		
		customerDao = new CustomerDao();
		List<Map<String, Object>> customerList = null;
		
		try {
			
			customerList = customerDao.selectCustomerList(beginRow, rowPerPage);
			lastPage = customerDao.customerListLastPage(rowPerPage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		// 속성에 모델 저장
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("customerList", customerList);
		
		request.getRequestDispatcher("/WEB-INF/view/emp/customerList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
