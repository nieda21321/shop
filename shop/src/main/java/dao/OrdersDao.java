package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDao {
	
	public List<Map<String,Object>> selectOrdersList(int beginRow, int rowPerPage) throws Exception{
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				SELECT
				    o.order_code as orderCode,
				    o.goods_code as goodsCode,
				    o.customer_code as customerCode,
				    o.address_code as addressCode,
				    o.order_quantity as orderQuantity,
				    o.order_state as orderState,
				    o.order_price as orderPrice,
				    o.createdate,
				    g.goods_name as goodsName,
				    g.goods_price as goodsPrice,
				    c.customer_name as customerName,
				    c.customer_phone as customerPhone,
				    a.address,
				    gi.filename
				FROM
				         orders o
				    INNER JOIN goods    g ON o.goods_code = g.goods_code
				    INNER JOIN customer c ON o.customer_code = c.customer_code
				    INNER JOIN address  a ON o.address_code = a.address_code
				    INNER JOIN GOODS_IMG gi ON o.GOODS_CODE = gi.GOODS_CODE 
				ORDER BY
				    o.order_code DESC
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);
		
		rs = psmt.executeQuery();
		
		while(rs.next()) {
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("orderCode",     rs.getInt("orderCode"));
		    m.put("goodsCode",     rs.getInt("goodsCode"));
		    m.put("customerCode",  rs.getInt("customerCode"));
		    m.put("addressCode",   rs.getInt("addressCode"));
		    m.put("orderQuantity", rs.getInt("orderQuantity"));
		    m.put("orderState",    rs.getString("orderState"));
		    m.put("orderPrice",    rs.getInt("orderPrice"));
		    m.put("createdate",    rs.getString("createdate"));
		    m.put("goodsName",     rs.getString("goodsName"));
		    m.put("goodsPrice",    rs.getInt("goodsPrice"));
		    m.put("customerName",  rs.getString("customerName"));
		    m.put("customerPhone", rs.getString("customerPhone"));
		    m.put("address",       rs.getString("address"));
		    m.put("fileName",       rs.getString("fileName"));
		    list.add(m);
		}
		
		return list;
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
	public int ordersListLastPage(int rowPerPage) throws Exception{
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				SELECT COUNT(*) FROM (
				SELECT
				    o.order_code as orderCode,
				    o.goods_code as goodsCode,
				    o.customer_code as customerCode,
				    o.address_code as addressCode,
				    o.order_quantity as orderQuantity,
				    o.order_state as orderState,
				    o.order_price as orderPrice,
				    o.createdate,
				    g.goods_name as goodsName,
				    g.goods_price as goodsPrice,
				    c.customer_name as customerName,
				    c.customer_phone as customerPhone,
				    a.address,
				    gi.FILENAME
				FROM
				         orders o
				    INNER JOIN goods    g ON o.goods_code = g.goods_code
				    INNER JOIN customer c ON o.customer_code = c.customer_code
				    INNER JOIN address  a ON o.address_code = a.address_code
				    INNER JOIN GOODS_IMG gi ON o.GOODS_CODE = gi.GOODS_CODE 
				)
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		rs = psmt.executeQuery();
		
		while(rs.next()) {
			
			allCnt = rs.getInt("COUNT(*)");
		}
		
		if ( allCnt == 0 ) {
			
			lastPage = 1;
		} else if (allCnt % rowPerPage == 0 ) {
			 
			lastPage = ( allCnt / rowPerPage );
		} else {
			
			lastPage = ( allCnt / rowPerPage ) + 1;
		}
		
		return lastPage;
	}
}
