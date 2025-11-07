package controller.emp.notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dao.NoticeDao;
import dto.Emp;
import dto.Notice;

/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/emp/notice/noticeList")
public class NoticeListController extends HttpServlet {

	
	private NoticeDao noticeDao;
	
	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 리스트 페이지
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		
		noticeDao = new NoticeDao();
		List<Notice> list = noticeDao.selectNoticeList(beginRow, rowPerPage);
		int lastPage = noticeDao.noticeListLastPage(rowPerPage);

		// 하단 페이징
		int pagePerpage = 10;
		int startPage = (( currentPage -1 ) / pagePerpage * pagePerpage ) + 1;
		int endPage = startPage + 9;
		
		if ( lastPage < endPage ) {
			
			endPage = lastPage;
		}
		
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		request.getRequestDispatcher("/WEB-INF/view/emp/notice/noticeList.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
