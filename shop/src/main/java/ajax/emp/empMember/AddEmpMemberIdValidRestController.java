package ajax.emp.empMember;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import dao.EmpDao;

/**
 * Servlet implementation class AddEmpMemberIdValidateController
 */
@WebServlet("/emp/checkId")
public class AddEmpMemberIdValidRestController extends HttpServlet {
       

	/**
	 * 
	 * 2025. 11. 04.
	 * author - tester
	 * 사원 가입 아이디 중복 검증 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String empId = request.getParameter("empId");
        boolean isDuplicate = false;

        if (empId != null && !(empId.trim().isEmpty())) {
        	
            try {
            	
                EmpDao empDao = new EmpDao();
                int idValidChk = empDao.addEmpMemberIdValid(empId); 
                if (idValidChk > 0) {
                	
                	System.out.println("EMP MEMBER ID VALID FAILED");
                	isDuplicate = true;
                } else {
                	
                	System.out.println("EMP MEMBER ID VALID SUCCESS");
                	isDuplicate = false;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("EMP MEMBER ID VALID ERR");
            }
        }

        // JSON 형식으로 결과 반환
        PrintWriter out = null;
        
        try {
        	
            out = response.getWriter();
            out.print("{\"duplicate\": " + isDuplicate + "}");
        } catch (IOException e) {
        	
            e.printStackTrace();
        } finally {
        	
            if (out != null) {
                out.close();
            }
        }

	}
}
