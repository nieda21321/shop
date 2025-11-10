package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.GoodsDao;

/**
 * Servlet implementation class CustomerIndexController
 */
@WebServlet("/customer/customerIndex")
public class CustomerIndexController extends HttpServlet {

	
	private GoodsDao goodsDao;
	
	/**
	 * 2025. 11. 04.
	 * author - tester
	 * 고객 상단 인덱스
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if(request.getParameter("currentPage") != null) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 20;
		int beginRow = ( 1 - currentPage ) * rowPerPage;
		int lastPage = 0;
		int startPage = 0;
		int endPage = 0;
		
		goodsDao = new GoodsDao();
		request.setAttribute("customerGoodsList", goodsDao.selectCustomerGoodsList(beginRow, rowPerPage));
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		
		int totalTd = 20;
		// 페이지 ( 첫번째 or 마지막) 에 출력할 상품이 7개다 -> totalTd = 10
		
		request.setAttribute("totalTd", totalTd);
		
		request.getRequestDispatcher("/WEB-INF/view/customer/customerIndex.jsp").forward(request, response);
	}

}
