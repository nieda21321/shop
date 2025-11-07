package controller.emp.notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.NoticeDao;
import dto.Emp;

/**
 * Servlet implementation class NoticeOneController
 */
@WebServlet("/emp/notice/noticeOne")
public class NoticeOneController extends HttpServlet {
       

	private NoticeDao noticeDao;
	
	
	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 세부정보 페이지
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자 체크
		HttpSession session = request.getSession();
		Emp emp = (Emp) session.getAttribute("loginEmp");
		int loginEmpCode = emp.getEmpCode();
		
		
		String paramNoticeCode = request.getParameter("noticeCode");
		
		int noticeCode = 0;
		
		List<Map<String, Object>> noticeOnelist = new ArrayList<Map<String,Object>>();
		Map<String, Object> noticeOne = null;
		
		if ( paramNoticeCode != null ) {
			
			noticeCode = Integer.parseInt(paramNoticeCode);
			noticeDao = new NoticeDao();
			
			noticeOnelist = noticeDao.selectNoticeOne(noticeCode);
			if (!noticeOnelist.isEmpty()) {
		        noticeOne = noticeOnelist.get(0);
		    }
		}

		request.setAttribute("noticeOne", noticeOne);
		request.setAttribute("sessionUserCode", loginEmpCode);
		request.getRequestDispatcher("/WEB-INF/view/emp/notice/noticeOne.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
