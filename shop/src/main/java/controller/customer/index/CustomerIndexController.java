package controller.customer.index;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * 고객로그인 - 메인페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if(request.getParameter("currentPage") != null) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 20;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		
		
		goodsDao = new GoodsDao();
		List<Map<String,Object>> customerGoodsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> customerBestGoodsList = new ArrayList<Map<String,Object>>();
		int lastPage = 0;
		
		try {
			
		    customerGoodsList = goodsDao.selectCustomerGoodsList(beginRow, rowPerPage);
		    customerBestGoodsList = goodsDao.selectCustomerBestGoodsList();
			lastPage = goodsDao.customerGoodsListLastPage(rowPerPage);
		} catch (Exception e) {

			e.printStackTrace();
		}

		// 하단 페이징
		int pagePerpage = 10;
		
		int startPage = (( currentPage -1 ) / pagePerpage * pagePerpage ) + 1;
		int endPage = startPage + 9;
		
		if ( lastPage < endPage ) {
			
			endPage = lastPage;
		}
		
		request.setAttribute("customerGoodsList", customerGoodsList);
		request.setAttribute("customerBestGoodsList", customerBestGoodsList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		request.getRequestDispatcher("/WEB-INF/view/customer/customerIndex.jsp").forward(request, response);
	}

}
