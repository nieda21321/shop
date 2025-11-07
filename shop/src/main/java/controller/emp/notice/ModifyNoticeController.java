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
import dto.Notice;

/**
 * Servlet implementation class ModifyNoticeController
 */
@WebServlet("/emp/notice/modifyNotice")
public class ModifyNoticeController extends HttpServlet {

	private NoticeDao noticeDao;
	
	
	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항 수정 페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자 체크
		HttpSession session = request.getSession();
		Emp emp = (Emp) session.getAttribute("loginEmp");
		int loginEmpCode = emp.getEmpCode(); 
		
		// 수정페이지
		String paramNoticeCode = request.getParameter("noticeCode");
		String paramEmpCode = request.getParameter("empCode");
		int empCode = Integer.parseInt(paramEmpCode);
		
		// 수정권한 체크
		if ( loginEmpCode != empCode ) {
			
			System.out.println("공지사항 수정권한 X");
			response.sendRedirect(request.getContextPath() + "/emp/notice/noticeList");
			return;
		} 
		
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
		request.getRequestDispatcher("/WEB-INF/view/emp/notice/modifyNotice.jsp").forward(request, response);
	}

	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항 수정 기능 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		String paramEmpCode = request.getParameter("empCode");
		int empCode = Integer.parseInt(paramEmpCode);
		String paramNoticeCode = request.getParameter("noticeCode");
		int noticeCode = Integer.parseInt(paramNoticeCode);
		
		int row = 0;
		noticeDao = new NoticeDao();
		Notice notice = new Notice();

		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setEmpCode(empCode);
		notice.setNoticeCode(noticeCode);
		
		row = noticeDao.updateNotice(notice);
		
		if ( row > 0 ) {
			
			System.out.println("공지사항 수정 성공");
			response.sendRedirect(request.getContextPath() + "/emp/notice/noticeOne?noticeCode=" + noticeCode);
		} else {
			
			System.out.println("공지사항 수정 실패");
			response.sendRedirect(request.getContextPath() + "/emp/notice/noticeOne?noticeCode=" + noticeCode);
		}
	}
}
