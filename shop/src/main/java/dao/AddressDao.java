package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Address;

public class AddressDao {
	
	public void insertAddress(Address address) {
		
		// select
		Connection conn = null;
		PreparedStatement psmt1 = null;
		PreparedStatement psmt2 = null;
		PreparedStatement psmt3 = null;
		ResultSet rs1 = null;
		
		String sql1 = """
				
				SELECT COUNT(*) FROM ADDRESS WHERE CUSTOMER_CODE = ?
				
				""";
		
		String sql2 = """
				
				delete from address
				where customer_code = ?
				and address_code = ( select min(address_code) from address )
				
				""";
		
		String sql3 = """
				
				INSERT INTO ADDRESS ( address_code, customer_code, address, createdate )
				values ( seq_address.nextval, ?, ?, sysdate)
				
				""";
		
		
		
		try {
			
			conn = DBConnection.getConn();
			conn.setAutoCommit(false);
			psmt1 = conn.prepareStatement(sql1);
			psmt1.setInt(1, address.getCustomerCode());
			rs1 = psmt1.executeQuery();
			rs1.next();
			int cnt = rs1.getInt(1);
			
			// 5개면 가장 오래된 주소 삭제 후 입력
			if ( cnt > 4) {
				
				psmt2 = conn.prepareStatement(sql2);
				psmt2.setInt(1, address.getCustomerCode());
				psmt2.executeUpdate();
			}
			
			// 추가
			psmt3 = conn.prepareStatement(sql3);
			psmt1.setInt(1, address.getCustomerCode());
			psmt1.setString(1, address.getAddress());
			int row = psmt3.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			//finally 자원해지 ( null 체크 확인 후 )
			try {
				
				if ( psmt2 != null ) {
					
					psmt2.close();
				}
				if ( psmt1 != null ) {
					
					psmt1.close();
				}
				if ( conn != null ) {
					
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		
		
		
		// select
		
		// delete
		
		// insert
	}

}
