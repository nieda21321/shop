package controller.customer.address;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dao.AddressDao;
import dao.EmpDao;
import dto.Address;
import dto.Customer;
import dto.Emp;

/**
 * Servlet implementation class AddressListController
 */
@WebServlet("/customer/addressList")
public class AddressListController extends HttpServlet {
       
	
	private AddressDao addressDao;

	/**
	 * 
	 * 2025. 11. 06.
	 * Author - tester
	 * 고객 - 배송지 주소 관리 리스트 페이징
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int paramCusCode = customer.getCustomerCode();
		
		int currentPage = 1; 
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		
		int lastPage = 0;
		
		addressDao = new AddressDao();
		List<Address> addressList = null;
		
		try {
			
			addressList = addressDao.selectAddressList(paramCusCode, beginRow, rowPerPage); 
			lastPage = addressDao.AddressListLastPage(paramCusCode, rowPerPage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("addressList", addressList);
		request.getRequestDispatcher("/WEB-INF/view/customer/addressList.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
