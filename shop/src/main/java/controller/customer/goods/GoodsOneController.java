package controller.customer.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dao.GoodsDao;

/**
 * Servlet implementation class GoodsOneController
 */
@WebServlet("/customer/goods/goodsOne")
public class GoodsOneController extends HttpServlet {

	private GoodsDao goodsDao;
	
	
	/**
	 *
	 * 2025. 11. 11.
	 * Author - tester
	 * 고객 로그인 - 메인페이지 - 제품클릭 - 제품상세정보페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramGoodsCode = request.getParameter("goodsCode");
		int goodsCode = Integer.parseInt(paramGoodsCode);
		
		// 필드 객체 의존성 주입 ( DI : Dependency Injection )
		goodsDao = new GoodsDao();
		Map<String, Object> goods = new HashMap<String, Object>();
		goods = goodsDao.selectGoodsOne(goodsCode);
		
		request.setAttribute("goods", goods);
		request.getRequestDispatcher("/WEB-INF/view/customer/goods/goodsOne.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
