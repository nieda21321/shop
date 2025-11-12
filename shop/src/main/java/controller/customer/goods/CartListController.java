package controller.customer.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.CartDao;
import dto.Customer;

/**
 * Servlet implementation class CartListController
 */
@WebServlet("/customer/goods/cartList")
public class CartListController extends HttpServlet {

	
	private CartDao cartDao;
	
	
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer loginCustomer = (Customer) session.getAttribute("loginCustomer");
		int customerCode = loginCustomer.getCustomerCode();
		
		cartDao = new CartDao();
		List<Map<String, Object>> cartList = new ArrayList<Map<String,Object>>();
		cartList = cartDao.selectCartList(customerCode);
		
		request.setAttribute("cartList", cartList);
		
		request.getRequestDispatcher("/WEB-INF/view/customer/goods/cartList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
