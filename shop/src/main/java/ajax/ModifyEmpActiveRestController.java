package ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.EmpDao;

/**
 * Servlet implementation class modifyEmpActive
 */
@WebServlet("/emp/modifyEmpActive")
public class ModifyEmpActiveRestController extends HttpServlet {
       

	/**
	 * 
	 * 2025. 11. 04.
	 * author - tester
	 * 사원 사용 상태 처리 REST 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
		
		String empCodeParam = request.getParameter("empCode");
		String activeParam = request.getParameter("active");
		
		// 기본 검증
        if (empCodeParam == null || activeParam == null) {
            
        	response.getWriter().write("{\"result\":\"error\",\"message\":\"필수값 누락\"}");
            return;
        }
        
		int parsEmpCode = Integer.parseInt(empCodeParam);
		int parsActive = Integer.parseInt(activeParam);
		int newActive = (parsActive == 1) ? 0 : 1;
		
		int row = 0;
		EmpDao empDao = new EmpDao();
		
		try {
			
			row = empDao.modifyEmpMemberActive(parsEmpCode, newActive);
			if ( row > 0 ) {
				
				System.out.println("MODIFY EMP ACTIVE SUCCESS");
				response.getWriter().write("{\"result\":\"success\",\"newActive\":" + newActive + "}");
			} else {
				
				System.out.println("MODIFY EMP ACTIVE FAILED");
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("MODIFY EMP ACTIVE ERR");
			response.getWriter().write("{\"result\":\"error\",\"message\":\"서버 오류 발생\"}");
		}
	}
}
