package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import dao.EmpDao;

/**
 * Servlet implementation class AddEmpMemberIdVaildateController
 */
@WebServlet("/emp/checkId")
public class AddEmpMemberIdVaildController extends HttpServlet {
       

	/**
	 * 
	 * 2025. 11. 04.
	 * ADD EMP MEMBER ID VAILD CONTROLLER  
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String empId = request.getParameter("empId");
        boolean isDuplicate = false;

        if (empId != null && !empId.trim().isEmpty()) {
        	
            try {
            	
                EmpDao empDao = new EmpDao();
                int idVaildChk = empDao.addEmpMemberIdVaild(empId); 
                System.out.println("aaaaaaaaaaaa" + idVaildChk );
                if (idVaildChk > 0) {
                	
                	System.out.println("EMP MEMBER ID VAILD FAILED");
                	isDuplicate = true;
                } else {
                	
                	System.out.println("EMP MEMBER ID VAILD SUCCESS");
                	isDuplicate = false;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("EMP MEMBER ID VAILD ERR");
            }
        }

        System.out.println("bbbbbbbbbb" + isDuplicate);
        // ✅ JSON 형식으로 결과 반환
        try (PrintWriter out = response.getWriter()) {
        	
            out.print("{\"duplicate\": " + isDuplicate + "}");
        }

	}
}
