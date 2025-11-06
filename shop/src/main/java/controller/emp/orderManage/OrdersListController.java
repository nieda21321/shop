package controller.emp.orderManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.OrdersDao;

/**
 * Servlet implementation class OrdersListController
 */
@WebServlet("/emp/ordersList")
public class OrdersListController extends HttpServlet {
       
	private OrdersDao ordersDao;
	
	
	/**
	 * 
	 * 2025. 11. 06.
	 * Author - tester
	 * 관리자 - 주문관리 페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		int lastPage = 0;
		
		ordersDao = new OrdersDao();
		List<Map<String, Object>> ordersList = null;
		
		try {
			
			ordersList = ordersDao.selectOrdersList(beginRow, rowPerPage);
			lastPage = ordersDao.ordersListLastPage(rowPerPage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("ordersList", ordersList);
		request.getRequestDispatcher("/WEB-INF/view/emp/ordersList.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
