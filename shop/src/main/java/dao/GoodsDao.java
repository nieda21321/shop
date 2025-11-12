package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Goods;
import dto.GoodsImg;

public class GoodsDao {
	
	
	public Map<String, Object> selectGoodsOne(int goodsCode) {
		
		Map<String,Object> goodsOneMap = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement psmt = null; 
		ResultSet rs = null;
		String sql = """
				
				SELECT
					    gi.filename   AS filename,
					    g.goods_code  AS goodscode,
					    g.goods_name  AS goodsname,
					    g.goods_price AS goodsprice,
					    NVL(g.soldout,' ') AS soldout,
					    g.point_rate AS pointRate
					FROM
					goods g inner join goods_img gi
					on g.goods_code = gi.goods_code
					where g.goods_code = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, goodsCode);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				goodsOneMap.put("filename", rs.getString("filename"));
				goodsOneMap.put("goodsCode", rs.getString("goodsCode"));
				goodsOneMap.put("goodsName", rs.getString("goodsName"));
				goodsOneMap.put("goodsPrice", rs.getInt("goodsPrice"));
				goodsOneMap.put("soldout", rs.getString("soldout"));
				goodsOneMap.put("pointRate", rs.getDouble("pointRate"));
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
		
		return goodsOneMap;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 2025. 11. 10.
	 * Author - tester
	 * 고객 로그인 - 메인 페이지 - 상단 상품베스트 5개 목록
	 * @return list
	 */
	public List<Map<String,Object>> selectCustomerBestGoodsList() {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null; 
		ResultSet rs = null;
		String sql = """
				
				SELECT
					    gi.filename   AS filename,
					    g.goods_code  AS goodscode,
					    g.goods_name  AS goodsname,
					    g.goods_price AS goodsprice
					FROM
					         goods g
					    INNER JOIN goods_img gi ON g.goods_code = gi.goods_code
					    INNER JOIN (
					        SELECT
					            goods_code,
					            COUNT(*)
					        FROM
					            orders
					        GROUP BY
					            goods_code
					        ORDER BY
					            COUNT(*) DESC
					        OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY
					    )         t ON g.goods_code = t.goods_code
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("filename", rs.getString("filename"));
				m.put("goodsCode", rs.getString("goodsCode"));
				m.put("goodsName", rs.getString("goodsName"));
				m.put("goodsPrice", rs.getInt("goodsPrice"));
				list.add(m);
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
		
		return list;
	}
	
	
	
		/**
		 * 
		 * 2025. 11. 10.
		 * Author - tester
		 * 고객 로그인 - 메인 페이지 - 상품목록 리스트
		 * @param beginRow
		 * @param rowPerPage
		 * @return list
		 */
		public List<Map<String,Object>> selectCustomerGoodsList(int beginRow, int rowPerPage) {
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null; 
			ResultSet rs = null;
			String sql = """
					
					select 
						gi.filename as filename,
						g.goods_code as goodsCode,
						g.goods_name as goodsName,
						g.goods_price as goodsPrice
					from goods g inner join goods_img gi
					on g.goods_code = gi.goods_code
					where g.soldout is null
					order by g.goods_code desc
					offset ? rows fetch next ? rows only
					
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, beginRow);
				psmt.setInt(2, rowPerPage);
				rs = psmt.executeQuery();
				while(rs.next()) {
					
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("filename", rs.getString("filename"));
					m.put("goodsCode", rs.getString("goodsCode"));
					m.put("goodsName", rs.getString("goodsName"));
					m.put("goodsPrice", rs.getInt("goodsPrice"));
					list.add(m);
				}
				
			} catch (Exception e) {
				
			} finally {
				
				try {
					
					if(rs != null) rs.close();
					if(psmt != null) psmt.close();
					if(conn != null) conn.close();
				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
			}
			
			return list;
		}
		
		/**
		 * 
		 * 2025. 11. 10.
		 * Author - tester
		 * 고객 로그인 - 메인 페이지 - 상품목록 리스트 마지막 페이징 값
		 * @param rowPerPage
		 * @return lastPage
		 * @throws Exception
		 */
		public int customerGoodsListLastPage(int rowPerPage) throws Exception{
			
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
											gi.filename AS filename,
											g.goods_code AS goodsCode,
											g.goods_name AS goodsName,
											g.goods_price AS goodsPrice
						FROM
							goods g
						INNER JOIN goods_img gi
										ON
							g.goods_code = gi.goods_code
						WHERE
							g.soldout IS NULL
					)
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
	
	
	
	
	
		// 상품등록 + 이미지 등록
		// 반환값은 실패시 false
		public boolean insertGoodsAndImg(Goods goods, GoodsImg img) {
			boolean result = false;
			Connection conn = null;
			PreparedStatement stmtSeq = null; // select
			PreparedStatement stmtGoods = null; // insert
			PreparedStatement stmtImg = null; // insert
			ResultSet rs = null;
			
			String sqlSeq = """
				select seq_goods.nextval from dual
			""";
			
			String sqlGoods = """
				insert into goods(goods_code, goods_name, goods_price, emp_code, point_rate, soldout, createdate)
				values(?, ?, ?, ?, ?, null, sysdate)	
			""";
			
			String sqlImg = """
				insert into goods_img(goods_code, filename, origin_name, content_type, filesize, createdate)
				values(?, ?, ?, ?, ?, sysdate)
			""";
			
			try {
				conn = DBConnection.getConn();
				conn.setAutoCommit(false); // 단일 트랜잭션안에서 시퀀스 생성 -> 상품입력 -> 이미지입력
				
				// 1) seq_goods.nextval값을 먼저 생성 후 사용
				stmtSeq = conn.prepareStatement(sqlSeq);
				rs = stmtSeq.executeQuery();
				rs.next();
				int goodsCode = rs.getInt(1);
				
				// 2) goods 입력
				stmtGoods = conn.prepareStatement(sqlGoods);
				stmtGoods.setInt(1, goodsCode);
				stmtGoods.setString(2, goods.getGoodsName());
				stmtGoods.setInt(3, goods.getGoodsPrice());
				stmtGoods.setInt(4, goods.getEmpCode());
				stmtGoods.setDouble(5, goods.getPointRate());
				int row1 = stmtGoods.executeUpdate();
				// 상품입력에 실패하면
				if(row1 != 1) {
					conn.rollback(); 
					return result;
				}
				// 3) // 상품입력에 성공하면 img 입력
				stmtImg = conn.prepareStatement(sqlImg);
				stmtImg.setInt(1, goodsCode);
				stmtImg.setString(2, img.getFileName());
				stmtImg.setString(3, img.getOriginName());
				stmtImg.setString(4, img.getContentType());
				stmtImg.setLong(5, img.getFileSize());
				int row2 = stmtImg.executeUpdate();
				if(row2 != 1) {
					conn.rollback(); 
					return result;
					// throw new SQLException();
				}
				result = true; // 상품 & 이미지 입력성공
				conn.commit();
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return result;
		}
	
	
	
	
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
