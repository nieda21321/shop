package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.EmpDao;
import dto.Emp;

/**
 * Servlet implementation class addEmpMemberController
 */
@WebServlet("/emp/addEmp")
public class AddEmpMemberController extends HttpServlet {

	/**
	 * 
	 * 2025. 11. 04.
	 * author - tester
	 * 사원 회원가입 페이징 
	 * addEmpform 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/emp/addEmp.jsp").forward(request, response);
	}

	/**
	 * 
	 * 2025. 11. 04.
	 * author - tester
	 * 사원 회원가입 기능 처리 
	 * addEmpform
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String empId = request.getParameter("empId");
		String empPw = request.getParameter("empPw");
		String empName = request.getParameter("empName");
		
		int row = 0;
		
		Emp empDto = null;
		EmpDao empDao = new EmpDao();
		
		if ( ( empId != null || empId != "" ) && 
				( empPw != null || empId != "" ) && 
				( empName != null || empId != "" ) ) {
			
			try {
				
				empDto = new Emp();
				empDto.setEmpId(empId);
				empDto.setEmpPw(empPw);
				empDto.setEmpName(empName);
				
				row = empDao.addEmpMember(empDto);
				
				if ( row > 0 ) {
					
					System.out.println("ADD EMP MEMBER SUCCESS");
					response.sendRedirect(request.getContextPath() + "/emp/empList");
				}
			} catch (Exception e) {
				
				System.out.println("ADD EMP MEMBER ERR");
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/emp/empList");
			}
		}
	}
}
