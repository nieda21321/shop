package controller.emp.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.CustomerDao;
import dao.GoodsDao;

/**
 * Servlet implementation class GoodsListController
 */
@WebServlet("/emp/goods/goodsList")
public class GoodsListController extends HttpServlet {

	
	private GoodsDao goodsDao;
	
	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 관리자 - 상품관리 리스트 페이징
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		int lastPage = 0;
		
		goodsDao = new GoodsDao();
		List<Map<String, Object>> goodsList = null;
		
		try {
			
			goodsList = goodsDao.selectGoodsList(beginRow, rowPerPage); 
			lastPage = goodsDao.goodsListLastPage(rowPerPage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		// 속성에 모델 저장
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("goodsList", goodsList);
		
		request.getRequestDispatcher("/WEB-INF/view/emp/goods/goodsList.jsp").forward(request, response);
	}
}
