package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 */
@WebServlet("/emp/empIndex")
public class EmpIndexController extends HttpServlet {

	/**
	 * 2025. 11. 04.
	 * author - tester
	 * 사원 상단 인덱스 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/emp/empIndex.jsp").forward(request, response);
	}
}
