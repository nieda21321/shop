package controller.customer.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.CartDao;
import dto.Cart;
import dto.Customer;

/**
 * Servlet implementation class AddCartController
 */
@WebServlet("/customer/goods/addCart")
public class AddCartController extends HttpServlet {

	private CartDao cartDao;
	
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer loginCustomer = (Customer) session.getAttribute("loginCustomer");
		
		String goodsCode = request.getParameter("goodsCode");
		String cartQuantity = request.getParameter("cartQuantity");
		int customerCode = loginCustomer.getCustomerCode();
		
		Cart cart = new Cart();
		cart.setGoodsCode(Integer.parseInt(goodsCode));
		cart.setCustomerCode(customerCode);
		cart.setCartQuantity(Integer.parseInt(cartQuantity));
		cartDao.insertCart(cart);
		
		response.sendRedirect(request.getContextPath() + "/customer/goods/cartList");
	}

}
