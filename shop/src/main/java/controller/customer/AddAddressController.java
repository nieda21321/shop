package controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.AddressDao;
import dto.Address;

/**
 * 
 */
@WebServlet("/customer/addAddress")
public class AddAddressController extends HttpServlet {
       

	private AddressDao addressDao;
	
	
	/**
	 * 
	 * 2025. 11. 06.
	 * Author 
	 * 고객 - 주소관리 - 주소등록 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/customer/addAddress.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Address a = new Address();
		addressDao = new AddressDao();
		addressDao.insertAddress(a);
		
		response.sendRedirect(request.getContextPath() + "/customer/addressList");
	}
}
