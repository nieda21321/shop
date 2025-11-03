package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.apache.catalina.Session;

import dao.CustomerDao;
import dao.EmpDao;
import dto.Customer;
import dto.Emp;

/**
 * 
 */
@WebServlet("/out/login")
public class LoginController extends HttpServlet {
       

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/out/login.jsp").forward(request, response);
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String customerOrEmpSel = request.getParameter("customerOrEmpSel");
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		
		if ( customerOrEmpSel.equals("customer")) {
			
			CustomerDao customerDao = new CustomerDao();
			
			try {
				
				Customer loginCustomer = customerDao.selectCustomerByLogin(id, pw);
				
				if (loginCustomer == null) {
					
					System.out.println("Customer Login NULL FAILED");
					response.sendRedirect(request.getContextPath()+"/home");
					return;
				}
				
				session.setAttribute("loginCustomer", loginCustomer);
				System.out.println("Customer Login SUCCESS");
				response.sendRedirect(request.getContextPath()+"/customer/customerIndex");
				
			} catch (Exception e) {
				
				System.out.println("Customer Login FAILED");
				e.printStackTrace();
			}
			
		} else if (customerOrEmpSel.equals("emp")) {
			
			EmpDao empDao = new EmpDao();
			
			try {
				
				Emp loginEmp = empDao.selectEmpByLogin(id, pw);
				
				if (loginEmp == null) {
					
					System.out.println("Emp Login NULL FAILED");
					response.sendRedirect(request.getContextPath()+"/home");
					return;
				}
				
				session.setAttribute("loginEmp", loginEmp);
				response.sendRedirect(request.getContextPath()+"/emp/empIndex");
			} catch (Exception e) {
				
				System.out.println("Emp Login FAILED");
				e.printStackTrace();
			}
		}
	}
}
