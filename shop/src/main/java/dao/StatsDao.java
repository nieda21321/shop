package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsDao {
	
	
		public List<Map<String, Object>> selectOrderCntByGender() {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
					
			String sql = """
							
				SELECT
					t.g AS gender,
					count(*) AS cnt
				FROM
					(
					SELECT
						c.gender g,
						o.ORDER_code oc
					FROM
						customer c
					INNER JOIN orders o ON
						c.CUSTOMER_CODE = o.CUSTOMER_CODE ) t
				GROUP BY
					t.g
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("gender",  rs.getString("gender"));
					map.put("cnt", rs.getInt("cnt"));
					list.add(map);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				
				
					try {
						
						if (conn != null) conn.close();
						if (rs != null) rs.close();
				        if (psmt != null) psmt.close();
					} catch (SQLException e) {
	
						e.printStackTrace();
					}
		        
			}
			
			return list;
		}
		
	
	
		// 11개 chart 메서드 구현
		public List<Map<String, Object>> selectOrderCntByYM(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
				SELECT
					to_char(createdate, 'yyyy-mm') AS ym,
					count(*) AS cnt
				FROM
					orders
				WHERE
					createdate BETWEEN 
					to_date ( '2025-01-01', 'YYYY-MM-DD') 
					AND to_date ( '2025-12-31', 'YYYY-MM-DD')
				GROUP BY to_char(createdate, 'yyyy-mm')
				ORDER BY ym asc
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, fromYM);
				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ym",  rs.getString("ym"));
					map.put("cnt", rs.getInt("cnt"));
					list.add(map);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				
				
					try {
						
						if (conn != null) conn.close();
						if (rs != null) rs.close();
				        if (psmt != null) psmt.close();
					} catch (SQLException e) {

						e.printStackTrace();
					}
		        
			}
			
			return list;
		}

	
	// 11개 chart 메서드 구현
	public List<Map<String, Object>> selectOrderTotalCntByYM(String fromYM, String toYM) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT 
					t.ym, sum(t.CNT) over(ORDER BY t.ym asc) AS totalOrder
				FROM (
					SELECT
						to_char(createdate, 'yyyy-mm') AS ym,
						count(*) AS cnt
					FROM
						orders
					WHERE
						createdate BETWEEN 
						to_date ( ? , 'YYYY-MM-DD') 
						AND to_date ( ? , 'YYYY-MM-DD')
					GROUP BY to_char(createdate, 'yyyy-mm') 
				) t
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, fromYM);
			psmt.setString(2, toYM);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ym",  rs.getString("ym"));
				map.put("totalOrder", rs.getString("totalOrder"));
				list.add(map);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			
				try {
					
					if (conn != null) conn.close();
					if (rs != null) rs.close();
			        if (psmt != null) psmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
	        
		}
		
		return list;
	}
	
	
	public List<Map<String, Object>> selectOrderTotalPriceByYM(String fromYM, String toYM) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				
				SELECT t.ym, sum(t.total) over(ORDER BY t.ym asc) AS totalPrice
				FROM (
				SELECT
					to_char(createdate, 'yyyy-mm') AS ym,
					sum(order_price) AS total
				FROM
					orders
				WHERE
					createdate BETWEEN 
					to_date ( ? , 'YYYY-MM-DD') 
					AND to_date ( ? , 'YYYY-MM-DD')
				GROUP BY to_char(createdate, 'yyyy-mm') ) t
				
				""";
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, fromYM);
			psmt.setString(2, toYM);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ym",  rs.getString("ym"));
				map.put("totalPrice", rs.getString("totalPrice"));
				list.add(map);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			
				try {
					
					if (conn != null) conn.close();
					if (rs != null) rs.close();
			        if (psmt != null) psmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
	        
		}
		return list;
	}
}
