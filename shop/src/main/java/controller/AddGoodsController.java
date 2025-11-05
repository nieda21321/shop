package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

/**
 * Servlet implementation class AddGoodsController
 */
@WebServlet("/emp/addGoods")
public class AddGoodsController extends HttpServlet {
       

	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 
	 * 상품등록 페이징
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/emp/addGoods.jsp");
	}

	/**
	 * 2025. 11. 05.
	 * Author - tester
	 * 
	 * 상품 등록 기능 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goodsName = request.getParameter("goodsName");
		String goodsPrice = request.getParameter("goodsPrice");
		String pointRate = request.getParameter("pointRate");
		// 파일업로드는 Part 라이브러리 사용
		Part part = request.getPart("goodsImg");
		
		String fileName = "";
		String originName = part.getSubmittedFileName();
		String contentType = part.getContentType();
		
		long fileSize = part.getSize(); 
	}
}
