package ajax.emp.stats;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dao.StatsDao;

/**
 * Servlet implementation class TotalOrderAndPriceRestController
 */
@WebServlet("/emp/stats/orderPriceByYM")
public class SelectOrderPriceByYMRestController extends HttpServlet {

	
	private StatsDao statsDao;
	
	
	/**
	 * 
	 * 2025. 11. 12.
	 * Author - tester
	 * 관리자 - 통계관리
	 * 특정년도의 월별 주문 금액 : 막대 차트
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String fromYM = request.getParameter("fromYM");
		String toYM = request.getParameter("toYM");
		
		response.setContentType("application/json; charset = UTF-8");
		
		statsDao = new StatsDao();
		List<Map<String, Object>> orderList = new ArrayList<Map<String,Object>>();
		//List<Map<String, Object>> priceList = new ArrayList<Map<String,Object>>();
		
		orderList = statsDao.selectOrderPriceByYM(fromYM, toYM);
		//priceList = statsDao.selectOrderTotalPriceByYM(fromYM, toYM);
		
		Gson gson = new Gson();
		String jsonResult = gson.toJson(orderList);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
