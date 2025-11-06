package controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.AddressDao;
import dto.Address;
import dto.Customer;

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

		//request.getRequestDispatcher("/WEB-INF/view/customer/addressList.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int paramCusCode = customer.getCustomerCode();
		
		String postcode = request.getParameter("postcode");
		String roadAddress = request.getParameter("roadAddress");
		String jibunAddress = request.getParameter("jibunAddress");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		
		String address = postcode + " " + roadAddress + " " + jibunAddress + " " +  detailAddress + " " + extraAddress;
		System.out.println("address : " + address);
		
		Address a = new Address();
		addressDao = new AddressDao();

		a.setCustomerCode(paramCusCode);
		a.setAddress(address);
		
		addressDao.insertAddress(a);
		
		response.sendRedirect(request.getContextPath() + "/customer/addressList");
	}
}
