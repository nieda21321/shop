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
 * Servlet implementation class RemoveNoticeController
 */
@WebServlet("/emp/notice/removeNotice")
public class RemoveNoticeController extends HttpServlet {

	private NoticeDao noticeDao;
	
	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항 삭제 기능
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사용자 체크
				HttpSession session = request.getSession();
				Emp emp = (Emp) session.getAttribute("loginEmp");
				int loginEmpCode = emp.getEmpCode(); 
				
				String paramEmpCode = request.getParameter("empCode");
				int empCode = Integer.parseInt(paramEmpCode);
				String paramNoticeCode = request.getParameter("noticeCode");
				int noticeCode = Integer.parseInt(paramNoticeCode);
				
				// 삭제권한 체크
				if ( loginEmpCode != empCode ) {
					
					System.out.println("공지사항 삭제권한 X");
					response.sendRedirect(request.getContextPath() + "/emp/notice/noticeList");
					return;
				} 
				
				int row = 0;
				noticeDao = new NoticeDao();
				Notice notice = new Notice();

				notice.setEmpCode(empCode);
				notice.setNoticeCode(noticeCode);
				
				row = noticeDao.removeNotice(notice);
				
				if ( row > 0 ) {
					
					System.out.println("공지사항 삭제 성공");
					response.sendRedirect(request.getContextPath() + "/emp/notice/noticeList");
				}
	}

	/**
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}
}
