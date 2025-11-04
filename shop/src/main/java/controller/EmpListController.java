package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.EmpDao;
import dto.Emp;

/**
 * Servlet implementation class EmpListController
 */
@WebServlet("/emp/empList")
public class EmpListController extends HttpServlet {

	/**
	 * 
	 * 2025. 11. 04.
	 * author - tester
	 * 사원관리 - 사원 리스트 페이징
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int currentPage = 1; 
				
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		
		int lastPage = 0;
		
		EmpDao empDao = new EmpDao();
		List<Emp> empList = null;
		
		try {
			
			empList = empDao.selectEmpListByPage(beginRow, rowPerPage);
			lastPage = empDao.EmpListLastPage(rowPerPage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("empList", empList);
		request.getRequestDispatcher("/WEB-INF/view/emp/empList.jsp").forward(request, response);
	}
}
