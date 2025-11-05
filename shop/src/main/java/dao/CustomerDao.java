package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Customer;
import dto.Outid;

public class CustomerDao {
	
	
	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 고객 계정 탈퇴 처리 기능
	 * @param oi
	 */
	public int removeCustomerByEmp(Outid oi) {

		Connection conn = null;
		PreparedStatement psmtCustomer = null;
		PreparedStatement psmtOutid = null;
		int row = 0;
		int row2 = 0;
		
		String sqlCustomer = """
				
				delete from customer where customer_id = ?
				""";
		
		String sqlOutid = """
				
				insert into outid(id, memo, createdate)
				values (?, ?, sysdate)
				""";
		
		// JDBC Connection의 기본 Commit 설정 값 auto commit = true;
		// 해당 값을 false 로 변경 후 transaction 적용
		try {
			
			conn = DBConnection.getConn();
			
			// 개발자가 commit / rollback 직접 구현 필요
			conn.setAutoCommit(false);
			
			psmtCustomer = conn.prepareStatement(sqlCustomer);
			psmtCustomer.setString(1, oi.getId());
			row = psmtCustomer.executeUpdate();
			
			if (row == 1) {
				
				System.out.println("CUSTOMER DELETE SUCCESS");
				psmtOutid = conn.prepareStatement(sqlOutid);
				psmtOutid.setString(1, oi.getId());
				psmtOutid.setString(2, oi.getMemo());
				
				row2 = psmtOutid.executeUpdate();
				
				if ( row2 == 1 ) {
					
					System.out.println("OUTID INSERT SUCCESS");
				} else {
					
					System.out.println("OUTID INSERT FAILED");
				}
			} else {
				
				System.out.println("CUSTOMER DELETE NULL FAILED");
				throw new SQLException();
			}
			
			conn.commit();
		} catch (SQLException e) {
			
			try {
				
				System.out.println("removeCustomerByEmp SQL ROLLBACK");
				conn.rollback();
			} catch (SQLException e1) {
				
				System.out.println("removeCustomerByEmp SQL ERR");
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			
			try {
				
				psmtOutid.close();
				psmtCustomer.close();
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		return row2;
	}
	
	
	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 직원 로그인시 전체 고객 리스트 확인
	 * @param beginRow
	 * @param rowPerPage
	 * @return customerList
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectCustomerList(int beginRow, int rowPerPage) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					c.customer_code AS customerCode,
					c.customer_id AS customerId,
					c.customer_pw AS customerPw,
					c.customer_name AS customerName,
					a.ADDRESS AS address,
					c.customer_phone AS customerPhone,
					c.point,
					c.createdate
				FROM
					customer c
				INNER JOIN ADDRESS a
				ON c.CUSTOMER_CODE = a.CUSTOMER_CODE
				ORDER BY customerCode desc
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);
		
		rs = psmt.executeQuery();
		
		List<Map<String, Object>> customerList = new ArrayList<Map<String,Object>>();
		
		while (rs.next()) {
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("customerCode", rs.getInt("customerCode"));
			m.put("customerId",rs.getString("customerId"));
			m.put("customerPw",rs.getString("customerPw"));
			m.put("customerName",rs.getString("customerName"));
			m.put("address",rs.getString("address"));
			m.put("customerPhone",rs.getString("customerPhone"));
			m.put("point",rs.getString("point"));
			m.put("createdate",rs.getString("createdate"));
			customerList.add(m);
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return customerList;
	}
	
	
	/**
	 * 
	 * 2025. 11. 05.
	 * 마지막 페이징 값
	 * 
	 * @param rowPerPage
	 * @return
	 * @throws Exception
	 */
	public int customerListLastPage(int rowPerPage) throws Exception{
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					COUNT(*)
				FROM
					(
					SELECT
						c.customer_code AS customerCode,
						c.customer_id AS customerId,
						c.customer_pw AS customerPw,
						c.customer_name AS customerName,
						a.ADDRESS AS address,
						c.customer_phone AS customerPhone,
						c.point,
						c.createdate
					FROM
						customer c
					INNER JOIN ADDRESS a
				ON
						c.CUSTOMER_CODE = a.CUSTOMER_CODE )
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		rs = psmt.executeQuery();
		
		while(rs.next()) {
			
			allCnt = rs.getInt("COUNT(*)");
		}
		
		if ( allCnt % rowPerPage == 0 ) {
			
			lastPage = ( allCnt / rowPerPage );
		} else {
			
			lastPage = ( allCnt / rowPerPage ) + 1;
		}
		
		return lastPage;
	}
	
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
