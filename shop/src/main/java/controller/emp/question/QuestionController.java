package controller.emp.question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.NoticeDao;
import dao.QuestionDao;
import dto.Notice;
import dto.Question;

/**
 * Servlet implementation class QuestionController
 */
@WebServlet("/emp/question/questionList")
public class QuestionController extends HttpServlet {

	
	private QuestionDao questionDao;
	
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		
		if ( request.getParameter("currentPage") != null ) {
			
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		int beginRow = ( currentPage - 1 ) * rowPerPage;
		
		questionDao = new QuestionDao();
		List<Map<String, Object>> questionList = new ArrayList<Map<String,Object>>();
		
		questionList = questionDao.selectQuestionList(beginRow, rowPerPage);
		int lastPage = questionDao.questionListLastPage(rowPerPage);
		
		// 하단 페이징
		int pagePerpage = 10;
		int startPage = (( currentPage -1 ) / pagePerpage * pagePerpage ) + 1;
		int endPage = startPage + 9;
		
		if ( lastPage < endPage ) {
			
			endPage = lastPage;
		}
		
		request.setAttribute("questionList", questionList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		request.getRequestDispatcher("/WEB-INF/view/emp/notice/noticeList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
