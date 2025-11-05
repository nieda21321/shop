package ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.CustomerDao;
import dto.Outid;

/**
 * 
 */
@WebServlet("/emp/removeCustomerByEmpAjax")
public class RemoveCustomerByEmpRestController extends HttpServlet {

	private CustomerDao customerDao;
	
	/**
	 * 2025. 11. 05.
	 * Author - tester
	 * 고객 계정 탈퇴 처리 기능
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("customerId");
		String memo = request.getParameter("memo");
		int row = 0;
		
		Outid outid;
		customerDao = new CustomerDao();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
		
		if ( id != null && !id.trim().isEmpty() ) {
			
			outid = new Outid();
			outid.setId(id);
			outid.setMemo(memo);
			row = customerDao.removeCustomerByEmp(outid);
			
			if ( row == 1) {
				
				System.out.println("removeCustomerByEmpController SUCCESS");
				jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "탈퇴 완료");
			} else {
				
				System.out.println("removeCustomerByEmpController FAILED");
				jsonResponse.addProperty("status", "fail");
                jsonResponse.addProperty("message", "DB 처리 실패");
			}
		} else {
			
			System.out.println("PARAM ERR");
			jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "잘못된 요청");
		}
		
	    //  Gson으로 JSON 문자열 변환 후 응답 전송
        String json = gson.toJson(jsonResponse);
        response.getWriter().write(json);
	}
}
