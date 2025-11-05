package controller.emp.outid;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.CustomerDao;
import dao.OutIdDao;
import dto.Outid;

/**
 * Servlet implementation class outIdListController
 */
@WebServlet("/emp/outIdList")
public class outIdListController extends HttpServlet {
       
	
	private OutIdDao outIdDao;
	
	/**
	 * 
	 * 2025. 11. 05.
	 * author - tester
	 * 관리자 - 탈퇴회원관리 페이징
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				int currentPage = 1;
				
				if ( request.getParameter("currentPage") != null ) {
					
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
				
				int rowPerPage = 10;
				int beginRow = ( currentPage - 1 ) * rowPerPage;
				int lastPage = 0;
				
				outIdDao = new OutIdDao();
				List<Outid> outIdList = null;
				
				try {
					
					outIdList = outIdDao.selectOutIdList(beginRow, rowPerPage); 
					lastPage = outIdDao.outIdListLastPage(rowPerPage);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
				// 속성에 모델 저장
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("lastPage", lastPage);
				request.setAttribute("outIdList", outIdList);
				
				request.getRequestDispatcher("/WEB-INF/view/emp/outIdList.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
