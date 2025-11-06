package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Address;

public class AddressDao {
	
	/**
	 * 2025. 11. 06.
	 * Author - tester
	 * 고객 - 배송지 주소 추가 기능
	 * @param address
	 */
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
			psmt3.setInt(1, address.getCustomerCode());
			psmt3.setString(2, address.getAddress());
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
	}
	
	
	/**
	 * 
	 * 2025. 11. 06.
	 * Author - tester
	 * 고객 - 배송지 주소 관리 리스트 출력
	 * @param beginRow
	 * @param rowPerPage
	 * @return addressList
	 * @throws Exception
	 */
	public List<Address> selectAddressList(int customerCode, int beginRow, int rowPerPage) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
				    address_code as addressCode,
				    customer_code as customerCode,
				    address,
				    createdate
				FROM
				    address
				WHERE customer_code = ?
				ORDER BY
				    address_code DESC
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		psmt.setInt(1, customerCode);
		psmt.setInt(2, beginRow);
		psmt.setInt(3, rowPerPage);
		
		rs = psmt.executeQuery();
		
		List<Address> addressList = new ArrayList<Address>();
		
		while (rs.next()) {
			
			Address address = new Address();
			address.setAddressCode(rs.getInt("addressCode"));
			address.setCustomerCode(rs.getInt("customerCode"));
			address.setAddress(rs.getString("address"));
			address.setCreatedate(rs.getString("createdate"));
			addressList.add(address);
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return addressList;
	}
	
	
	/**
	 * 
	 * 2025. 11. 06.
	 * 마지막 페이징 값
	 * 
	 * @param rowPerPage
	 * @return lastPage
	 * @throws Exception
	 */
	public int AddressListLastPage(int rowPerPage) throws Exception{
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT COUNT(*) FROM ADDRESS 
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
	
}
