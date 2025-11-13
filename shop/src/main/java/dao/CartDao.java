package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Cart;

public class CartDao {
	
	
	
	/**
	 * 
	 * 고객 - 주문 - 주문완료 페이지 상품정보
	 * @param cartCode
	 * @return
	 */
	public Map<String, Object> selectCartListByKey(int cartCode) {
		
		Connection conn = null;
		PreparedStatement psmt = null; 
		ResultSet rs = null;
		String sql = """
				
				SELECT
					gi.FILENAME AS filename,
					g.GOODS_CODE AS goodsCode,
					g.GOODS_NAME AS goodsName,
					g.GOODS_PRICE AS goodsPrice,
					g.POINT_RATE AS potinRate,
					c.CART_QUANTITY AS cartQuantity
				FROM
					cart c
				INNER JOIN goods g
				ON
					c.GOODS_CODE = g.GOODS_CODE
				INNER JOIN goods_img gi
				ON 
					c.GOODS_CODE = gi.GOODS_CODE 
				WHERE
					c.CART_CODE = ?
				
				""";
			
		Map<String, Object> m = new HashMap<String, Object>();
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cartCode);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				m.put("filename", rs.getString("filename"));
				m.put("goodsCode", rs.getString("goodsCode"));
				m.put("goodsName", rs.getString("goodsName"));
				m.put("goodsPrice", rs.getInt("goodsPrice"));
				m.put("pointRate", rs.getDouble("pointRate"));
				m.put("cartQuantity", rs.getDouble("cartQuantity"));
			}
			
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
			try {
				
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
		}
		
		return m;
		
	}
	
	
	public List<Map<String, Object>> selectCartList(int customerCode) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT
					c.cart_code as cardCode,
					g.goods_name as goodsName,
					nvl(g.SOLDOUT, ' ') as soldout,
					g.goods_price as goodsPrice,
					c.CART_QUANTITY as cartQuantity,
					( g.goods_price * c.CART_QUANTITY ) AS totalPrice
				FROM
					CART c
				INNER JOIN goods g ON
					c.GOODS_CODE = g.GOODS_CODE
				WHERE c.CUSTOMER_CODE = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, customerCode);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("cardCode", rs.getInt("cardCode"));
				m.put("goodsName", rs.getString("goodsName"));
				m.put("soldout", rs.getString("soldout"));
				m.put("goodsPrice", rs.getInt("goodsPrice"));
				m.put("cartQuantity", rs.getInt("cartQuantity"));
				m.put("totalPrice", rs.getInt("totalPrice"));
				list.add(m);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			//finally 자원해지 ( null 체크 확인 후 )
			try {

				if ( rs != null ) {

					rs.close();
				}
				if ( psmt != null ) {

					psmt.close();
				}
				if ( conn != null ) {
					
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
	
	
	
	
	
	
	public int insertCart(Cart c) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			
			conn.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			//finally 자원해지 ( null 체크 확인 후 )
			try {
				
				if ( psmt != null ) {

					psmt.close();
				}
				if ( conn != null ) {
					
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		return row;
	}

}
