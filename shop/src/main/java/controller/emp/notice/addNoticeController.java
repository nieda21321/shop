package controller.emp.notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.NoticeDao;
import dto.Emp;
import dto.Notice;

/**
 * Servlet implementation class addNoticeController
 */
@WebServlet("/emp/notice/addNotice")
public class addNoticeController extends HttpServlet {
       
	
	private NoticeDao noticeDao;

	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항등록 페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자 체크
		HttpSession session = request.getSession();
		Emp emp = (Emp) session.getAttribute("loginEmp");
		int loginEmpCode = emp.getEmpCode();
		String empId = emp.getEmpId();
		String empName = emp.getEmpName();
		
		request.setAttribute("loginEmpCode", loginEmpCode);
		request.setAttribute("empId", empId);
		request.setAttribute("empName", empName);
		request.getRequestDispatcher("/WEB-INF/view/emp/notice/addNotice.jsp").forward(request, response);
	}

	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항등록 기능
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		String empCode = request.getParameter("empCode");
		int parseEmpCode = Integer.parseInt(empCode);
		
		int row = 0;
		noticeDao = new NoticeDao();
		Notice notice = new Notice();

		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setEmpCode(parseEmpCode);
		
		row = noticeDao.insertNotice(notice);
		
		if ( row > 0 ) {
			
			System.out.println("INSERT NOTICE SUCCESS");
			response.sendRedirect(request.getContextPath() + "/emp/notice/noticeList");
		}
	}
}
