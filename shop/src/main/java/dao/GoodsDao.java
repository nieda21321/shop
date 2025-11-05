package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsDao {
	
	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 관리자 - 상품관리 페이지
	 * @param beginRow
	 * @param rowPerPage
	 * @return goodsList
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectGoodsList(int beginRow, int rowPerPage) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					g.goods_code as goodsCode,
					g.goods_name as goodsName,
					g.goods_price as goodsPrice,
					g.soldout,
					g.emp_code as empCode,
					g.point_Rate as pointRate,
					g.createdate,
					e.emp_id as empId,
					e.emp_name as empName,
					gi.filename AS filename,
					gi.origin_name AS originName,
					gi.content_type AS contentType
				FROM
					goods g
				INNER JOIN emp e
				ON
					g.EMP_CODE = e.EMP_CODE
				INNER JOIN GOODS_IMG gi 
				ON
					g.GOODS_CODE = gi.GOODS_CODE 
				ORDER BY
					g.CREATEDATE DESC
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);
		
		rs = psmt.executeQuery();
		
		List<Map<String, Object>> goodsList = new ArrayList<Map<String,Object>>();
		
		while (rs.next()) {
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("goodsCode", rs.getInt("goodsCode"));
			m.put("goodsName",rs.getString("goodsName"));
			m.put("goodsPrice",rs.getString("goodsPrice"));
			m.put("soldout",rs.getString("soldout"));
			m.put("address",rs.getString("address"));
			m.put("empCode",rs.getString("empCode"));
			m.put("pointRate",rs.getString("pointRate"));
			m.put("createdate",rs.getString("createdate"));
			m.put("empId",rs.getString("empId"));
			m.put("empName",rs.getString("empName"));
			m.put("filename",rs.getString("filename"));
			m.put("originName",rs.getString("originName"));
			m.put("contentType",rs.getString("contentType"));
			goodsList.add(m);
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return goodsList;
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
	public int goodsListLastPage(int rowPerPage) throws Exception{
		
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
						g.goods_code as goodsCode,
						g.goods_name as goodsName,
						g.goods_price as goodsPrice,
						g.soldout,
						g.emp_code as empCode,
						g.point_Rate as pointRate,
						g.createdate,
						e.emp_id as empId,
						e.emp_name as empName,
						gi.filename AS filename,
						gi.origin_name AS originName,
						gi.content_type AS contentType
					FROM
						goods g
					INNER JOIN emp e
					ON
						g.EMP_CODE = e.EMP_CODE
					INNER JOIN GOODS_IMG gi 
					ON
						g.GOODS_CODE = gi.GOODS_CODE )
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
