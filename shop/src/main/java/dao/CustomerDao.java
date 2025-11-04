package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import dto.Customer;

public class CustomerDao {
	
	/**
	 * 
	 * 2025. 11. 03.
	 * Author - tester
	 * 고객 로그인 기능
	 * 
	 * @param id
	 * @param pw
	 * @return customerDto
	 * @throws Exception
	 */
	public Customer selectCustomerByLogin(String id, String pw) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					CUSTOMER_CODE AS customerCode,
					CUSTOMER_ID AS customerId,
					CUSTOMER_PW AS customerPw,
					CUSTOMER_NAME AS customerName,
					customer_phone AS customerPhone,
					POINT,
					CREATEDATE
				FROM
					customer
				WHERE CUSTOMER_ID = ? AND CUSTOMER_PW = ?
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);

		psmt.setString(1, id);
		psmt.setString(2, pw);
		
		rs = psmt.executeQuery();
		
		Customer customer = null;
		
		if (rs.next()) {
			
			customer = new Customer();
			customer.setCustomerCode(rs.getInt("customerCode"));
			customer.setCustomerId(rs.getString("customerId"));
			customer.setCustomerPw(rs.getString("customerPw"));
			customer.setCustomerName(rs.getString("customerName"));
			customer.setCustomerPhone(rs.getString("customerPhone"));
			customer.setPoint(rs.getInt("POINT"));
			customer.setCreatedate(rs.getString("CREATEDATE"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return customer;
	}
	
	/**
	 * 
	 * 2025. 11. 03.
	 * Author - tester
	 * 사용자 회원가입
	 * 
	 * @param addMemberList
	 * @return
	 * @throws Exception
	 */
	public int insertCustomer(List<Map<String, Object>> addMemberList) throws Exception {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				INSERT ALL
					  INTO customer (customer_code, customer_id, customer_pw, customer_name, customer_phone, point, createdate)
					    VALUES (SEQ_CUSTOMER.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)
					  INTO address (address_code, customer_code, address, createdate)
					    VALUES (SEQ_ADDRESS.NEXTVAL, SEQ_CUSTOMER.CURRVAL, ?, SYSDATE)
					  INTO pw_history (customer_code, pw, createdate)
					    VALUES (SEQ_CUSTOMER.CURRVAL, ?, SYSDATE)
					SELECT * FROM dual
				""";
		
		try {
	        conn = DBConnection.getConn();
	        psmt = conn.prepareStatement(sql);

	        for (Map<String, Object> member : addMemberList) {
	            psmt.setString(1, (String)member.get("customerId"));     // customer_id
	            psmt.setString(2, (String)member.get("customerPw"));     // customer_pw
	            psmt.setString(3, (String)member.get("customerName"));   // customer_name
	            psmt.setString(4, (String)member.get("customerPhone"));  // customer_phone
	            psmt.setInt(5, (Integer)member.getOrDefault("point", 0)); // point

	            psmt.setString(6, (String)member.get("address"));        // address.address

	            psmt.setString(7, (String)member.get("customerPw"));     // pw_history.pw

	            row = psmt.executeUpdate();

	            if (row < 1) {
	               
	            	// insert 실패시 처리 (예외 던지거나 로깅 등)
	                throw new Exception("Insert failed for customerId");
	            }
	        }
	    } finally {
	    	
	        if (psmt != null) {
	        	
	        	psmt.close();
	        }
	        
	        if (conn != null) {
	        	
	        	conn.close();
	        }
	    }
		
		return row;
	}
	
	
	/**
	 * 
	 * 2025. 11. 03.
	 * Author - tester
	 * 사용자 아이디 중복 체크
	 * 
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public boolean addMemberIdChk(String customerId) throws Exception {
	    
		String sql = """
				SELECT count(id)
				FROM
				( 
					SELECT customer_id AS id FROM customer
				UNION ALL
					SELECT emp_id AS id FROM emp 
				UNION ALL
					SELECT id FROM outid )
				WHERE id = ?
				""";

	    try (Connection conn = DBConnection.getConn();
	         PreparedStatement psmt = conn.prepareStatement(sql)) {

	        psmt.setString(1, customerId);

	        try (ResultSet rs = psmt.executeQuery()) {
	           
	        	if (rs.next()) {
	                
	        		return rs.getInt(1) > 0; // 존재하면 true, 없으면 false
	            }
	        }
	    } catch (Exception e) {
	       
	    	e.printStackTrace();
	    	// 필요시 상위로 예외 전달
	    	throw e; 
	    }

	    return false;
	}
}
