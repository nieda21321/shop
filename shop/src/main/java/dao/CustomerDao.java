package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Customer;

public class CustomerDao {
	
	/**
	 * 2025. 11. 03.
	 * author : tester
	 * 고객 로그인
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
}
