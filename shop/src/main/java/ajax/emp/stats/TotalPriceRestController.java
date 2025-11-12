package ajax.emp.stats;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dao.StatsDao;

/**
 * Servlet implementation class TotalPriceRestController
 */
@WebServlet("/emp/status/totalPrice")
public class TotalPriceRestController extends HttpServlet {
       
	private StatsDao statsDao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String fromYM = request.getParameter("fromYM");
			String toYM = request.getParameter("toYM");
			
			response.setContentType("application/json; charset = UTF-8");
			
			statsDao = new StatsDao();
			List<Map<String, Object>> priceList = new ArrayList<Map<String,Object>>();
			
			priceList = statsDao.selectOrderTotalPriceByYM(fromYM, toYM);
			
			Gson gson = new Gson();
			String jsonResult = gson.toJson(priceList);
			PrintWriter out = response.getWriter();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
