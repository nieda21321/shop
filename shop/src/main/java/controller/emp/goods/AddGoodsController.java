package controller.emp.goods;

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

import dao.GoodsDao;
import dto.Emp;
import dto.Goods;
import dto.GoodsImg;

/**
 * Servlet implementation class AddGoodsController
 */
@WebServlet("/emp/addGoods")
public class AddGoodsController extends HttpServlet {
       
	
	private GoodsDao goodsDao;

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
		String originName = part.getSubmittedFileName();
		String filename = UUID.randomUUID().toString().replace("-", "");
		// filename = filename + originName의 확장자
		filename += originName.substring(originName.lastIndexOf("."));                 
		String contentType = part.getContentType();
		long filesize = part.getSize();
		
		System.out.println("originName: "+originName);
		System.out.println("filename: "+filename);
		System.out.println("contentType: "+contentType);
		System.out.println("filesize: "+filesize);
		
		if(!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
			System.out.println("png, jpg, gif 파일만 허용");
			response.sendRedirect(request.getContextPath() + "/emp/addGoods");
			return;
		}
		
		
		Emp loginEmp = (Emp)(request.getSession().getAttribute("loginEmp"));
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(Integer.parseInt(goodsPrice));
		goods.setPointRate(Double.parseDouble(pointRate));
		goods.setEmpCode(loginEmp.getEmpCode());

		
		GoodsImg goodsImg = new GoodsImg();
		goodsImg.setFileName(filename);
		goodsImg.setOriginName(originName);
		goodsImg.setContentType(contentType);
		goodsImg.setFileSize(filesize);
		// insertGoodsImg()
		
		// 이미지를 저장
		String realPath = request.getServletContext().getRealPath("upload");
		File saveFile = new File(realPath, filename); // 빈(스트림) 파일
		InputStream is = part.getInputStream();
		OutputStream os = Files.newOutputStream(saveFile.toPath());
		// 스트림을 is -> os 전송
		is.transferTo(os);
		
		// db입력 
		goodsDao = new GoodsDao(); // DI : 객체의존성 주입
		// 1) 상품등록 후 키값 반환
		// https://cafe.naver.com/jjdev/18456
		// 2) 1번의 키값으로 이미지를 등록
		boolean result = goodsDao.insertGoodsAndImg(goods, goodsImg);
		// db입력 실패시 업로드된 이미지 삭제
		if(result == false) { // if(!result)
			// 저장된 이미지(File saveFile = new File(realPath, filename)) 삭제
			if(saveFile.exists()) {
				saveFile.delete();
			}
			response.sendRedirect(request.getContextPath()+"/emp/addGoods");
			return;
		}
		
		response.sendRedirect(request.getContextPath()+"/emp/goodsList");
	}
}
