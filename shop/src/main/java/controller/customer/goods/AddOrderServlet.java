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

import dao.AddressDao;
import dao.CartDao;
import dao.GoodsDao;
import dao.OrdersDao;
import dto.Address;
import dto.Customer;
import dto.Goods;
import dto.Orders;

/**
 * Servlet implementation class AddOrderServlet
 */
@WebServlet("/customer/goods/addOrders")
public class AddOrderServlet extends HttpServlet {
	
	
	private GoodsDao goodsDao;
	private CartDao cartDao;
	private AddressDao addressDao;
	private OrdersDao ordersDao;

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int customerCode = customer.getCustomerCode();
		
		
		// goodsOne action
		String goodsCode = request.getParameter("goodsCode");
		String cartQuantity = request.getParameter("cartQuantity");
		
		// cartList action
		String[] cartCodeList = request.getParameterValues("cartCodeList");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		// Map : 상품정보 + 이미지정보 + 수량
		if(goodsCode != null) {
			// goods
			// goodsCode 사용 조인
			// list.size() -> 하나의 상품
			
			goodsDao = new GoodsDao();
			Map<String, Object> m = goodsDao.selectGoodsOne(Integer.parseInt(goodsCode));
			m.put("cartQuantity", cartQuantity);
			list.add(m);
			
		} else {
			
			//cart
			// list.size() -> 하나이상의 상품
			// cartCodeList -> goods 정보 - > 조인
			
			cartDao = new CartDao();
			for(String cc : cartCodeList) {
				
				int cartCode = Integer.parseInt(cc);
				Map<String, Object> m = cartDao.selectCartListByKey(cartCode);
				list.add(m);
				
				// cartDao.deleteCart(cc); 주문목록으로 이동 후 cart에서 삭제
			}
		}
		
		int totalPrice = 0;
		for ( Map m : list) {
			
			totalPrice += (Integer) (m.get("goodsPrice"));
		}
		
		addressDao = new AddressDao();
		List<Address> addressList = new ArrayList<Address>();
		
		addressList = addressDao.selectAddressList(customerCode);
		
		
		request.setAttribute("list", list);
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("addressList", addressList);
		
		request.getRequestDispatcher("/WEB-INF/view/customer/goods/addOrders.jsp").forward(request, response);
	}

	/**
	 * 
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		int customerCode = customer.getCustomerCode();
		
		String addressCode = request.getParameter("addressCode");
		// 결제 모듈 추가 후 진행
		String orderPrice = request.getParameter("orderPrice");

		String[] goodsCodeList = request.getParameterValues("goodsCode");
		String[] goodsPriceList = request.getParameterValues("goodsPrice");
		String[] orderQuantityList = request.getParameterValues("orderQuantity");
		
		ordersDao = new OrdersDao();
		for ( int i = 0; i < goodsCodeList.length; i = i+1) {
			
			Orders o = new Orders();
			o.setCustomerCode(customerCode);
			o.setAddressCode(Integer.parseInt(addressCode));
			o.setGoodsCode(Integer.parseInt(goodsCodeList[i]));
			o.setOrderPrice(Integer.parseInt(goodsPriceList[i]) * Integer.parseInt(orderQuantityList[i]));
			o.setOrderQuantity(Integer.parseInt(orderQuantityList[i]));
			ordersDao.insertOrders(o);
		}
		
		
		
		
		
		
		// insert payment 테이블에 결제 내역 추가
		// insert orders 각 상품별로 주문이력 추가
		// orders_payment 테이블에 payment pk 와 orders pk를 연결 ( insert)
		
	}
}
