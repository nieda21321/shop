package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 2025. 11. 05.
 * 
 * 로그인전에 만 허용
 */
//@WebFilter("/out/*")
public class OutFilter extends HttpFilter implements Filter {
       
	/**
	 * 2025. 11. 05.
	 * 
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// 요청 전
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if (session.getAttribute("loginEmp") != null ) {
			
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/emp/empIndex");
			return;
		} else if ( session.getAttribute("loginCustomer") != null ) {
			
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/customer/customerIndex");
		}
		
		chain.doFilter(request, response);
		
		// 요청 후
	}
}
