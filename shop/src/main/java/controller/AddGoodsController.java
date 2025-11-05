package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;

import dto.Emp;
import dto.Goods;
import dto.GoodsImg;

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
		
		request.getRequestDispatcher("/WEB-INF/view/emp/addGoods.jsp").forward(request, response);
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
		Emp loginEmp = (Emp)(request.getSession().getAttribute("loginEmp"));
		
		String originName = part.getSubmittedFileName();
		String fileName = UUID.randomUUID().toString().replace("-", "");
		
		fileName += originName.substring(originName.lastIndexOf(".")); 
		String contentType = part.getContentType();
		
		long fileSize = part.getSize();
		
		if ( ! (contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif") ) ) {
			
			System.out.println("png, jpg, gif 파일만 허용");
			response.sendRedirect(request.getContextPath() + "/emp/addGoods");
			return;
		}
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(Integer.parseInt(goodsPrice));
		goods.setPointRate(Double.parseDouble(pointRate));
		goods.setEmpCode(loginEmp.getEmpCode());
		
		int goodsCode = 0;
		
		GoodsImg goodsImg = new GoodsImg();
		goodsImg.setGoodsCode(goodsCode);
		goodsImg.setFileName(fileName);
		goodsImg.setOriginName(originName);
		goodsImg.setFileSize(fileSize);
		
		//이미지 저장
		String realPath = request.getServletContext().getRealPath("upload");
		
		// 빈(스트림) 파일
		File saveFile = new File(realPath, fileName);
		InputStream is = part.getInputStream();
		OutputStream os = Files.newOutputStream(saveFile.toPath());
		
		// 스트림을 is -> os 전송
		is.transferTo(os);
	}
}
