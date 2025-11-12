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
	
		/**
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 성별 총 주문 수량 : 파이 차트 
		 * @return
		 */
		public List<Map<String, Object>> selectOrderCntByGender(String fromYM, String toYM) {
			
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
		
		
		/**
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 성별 총 주문 금액 : 파이 차트 
		 * @return
		 */
		public List<Map<String, Object>> selectOrderPriceSumByGender(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
					
			String sql = """
							
				SELECT
					t.g AS gender,
					sum(t.op) AS sum
				FROM
					(
					SELECT
						c.gender g,
						o.ORDER_price op
					FROM
						customer c
					INNER JOIN orders o ON
						c.CUSTOMER_CODE = o.CUSTOMER_CODE
					WHERE
						to_char(o.createdate, 'yyyymmdd') >= '20250101'
						AND o.ORDER_STATE = '배달완료' ) t
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
					map.put("totalPrice", rs.getInt("sum"));
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
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 상품별 평균 리뷰 평점 1위 ~ 10위 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderReviewAvgGoodsRank(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						t.GOODSNAME AS goodsname,
						t.avg AS avg,
						t.RANK AS rank
					FROM
						(
						SELECT
							goodscode,
							goodsname,
							avg,
							ROW_NUMBER() OVER(ORDER BY avg DESC) AS RANK
						FROM
							(
							SELECT
								g.GOODS_CODE AS goodscode,
								g.GOODS_NAME AS goodsname,
								avg(r.score) AS avg
							FROM
								orders o
							INNER JOIN review r
					ON
								o.ORDER_CODE = r.ORDER_CODE
							INNER JOIN goods g
						ON
								o.GOODS_CODE = g.GOODS_CODE
							WHERE
								to_char(o.createdate, 'yyyymmdd') >= '20250101'
								AND o.ORDER_STATE = '배달완료'
					--			더미데이터 부족으로 확인불가
					--			AND o.order_code not in (
					--			
					--				select order_code from (
					--				SELECT
					--					order_code,
					--					count(*)
					--				FROM
					--					review
					--				GROUP BY
					--					order_code
					--				HAVING
					--					count(*) > 50
					--			)
							GROUP BY
								g.GOODS_CODE,
								g.GOODS_NAME ) ) t
					WHERE
						RANK < 11
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("goodsname",  rs.getString("goodsname"));
					map.put("avg", rs.getInt("avg"));
					map.put("rank", rs.getInt("rank"));
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
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 상품별 주문금액 1위 ~ 10위 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderPriceGoodsRank(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						t.GOODSNAME AS goodsname,
						t.SUM AS sum,
						t."RANK" AS rank
					FROM
						(
						SELECT
							goodscode,
							goodsname,
							sum,
							ROW_NUMBER() OVER(ORDER BY sum DESC) AS RANK
						FROM
							(
							SELECT
								o.GOODS_CODE AS goodscode,
								g.GOODS_NAME AS goodsname,
								sum(o.ORDER_PRICE) AS sum
							FROM
								orders o
							INNER JOIN goods g ON
								o.GOODS_CODE = g.GOODS_CODE
							WHERE
								to_char(o.createdate, 'yyyymmdd') >= '20250101'
								AND o.ORDER_STATE = '배달완료'
							GROUP BY
								o.GOODS_CODE,
								g.GOODS_NAME )) t
					WHERE
						RANK < 11
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("goodsName",  rs.getString("goodsname"));
					map.put("totalPrice", rs.getInt("sum"));
					map.put("rank", rs.getInt("rank"));
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
		
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 상품별 주문횟수 1위 ~ 10위 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderCntGoodsRank(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						t.GOODSNAME AS goodsname,
						t.CNT AS cnt,
						t.RANK AS rank
					FROM
						(
						SELECT
							goodscode,
							goodsname,
							cnt,
							ROW_NUMBER() OVER(ORDER BY cnt DESC) AS RANK
						FROM
							(
							SELECT
								o.GOODS_CODE AS goodscode,
								g.GOODS_NAME AS goodsname,
								count(o.GOODS_CODE) AS cnt
							FROM
								orders o
							INNER JOIN goods g ON
								o.GOODS_CODE = g.GOODS_CODE
							WHERE
								to_char(o.createdate, 'yyyymmdd') >= '20250101'
								AND o.ORDER_STATE = '배달완료'
							GROUP BY
								o.GOODS_CODE,
								g.GOODS_NAME )) t
					WHERE
						RANK < 11
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("goodsName",  rs.getString("goodsname"));
					map.put("cnt", rs.getInt("cnt"));
					map.put("rank", rs.getInt("rank"));
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
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 고객별 총금액 1위 ~ 10위 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderPriceCustomerRank(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						t.name AS name,
						t.sum AS sum,
						t.RANK AS rank
					FROM
						(
						SELECT
							code,
							name,
							sum,
							ROW_NUMBER() OVER(ORDER BY sum DESC) AS RANK
						FROM
							(
							SELECT
								c.CUSTOMER_CODE AS code,
								c.CUSTOMER_NAME AS name,
								sum(o.ORDER_PRICE) AS sum
							FROM
								customer c
							INNER JOIN orders o ON
								c.CUSTOMER_CODE = o.ORDER_CODE
							WHERE
								to_char(o.createdate, 'yyyymmdd') >= '20250101'
								AND o.ORDER_STATE = '배달완료'
							GROUP BY
								c.CUSTOMER_CODE,
								c.CUSTOMER_NAME
					)) t
					WHERE
						RANK < 11
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("customerName",  rs.getString("name"));
					map.put("totalPrice", rs.getInt("sum"));
					map.put("rank", rs.getInt("rank"));
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
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 고객별 주문횟수 1위 ~ 10위 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderCntCustomerRank(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						t.name as name,
						t.cnt as cnt,
						t.rank as rank
					FROM
						(
						SELECT
							code,
							name,
							cnt,
							ROW_NUMBER() OVER(ORDER BY cnt DESC) AS RANK
						FROM
							(
							SELECT
								c.CUSTOMER_CODE AS code,
								c.CUSTOMER_NAME AS name,
								count(*) AS cnt
							FROM
								customer c
							INNER JOIN orders o ON
								c.CUSTOMER_CODE = o.ORDER_CODE
							WHERE
								to_char(o.createdate, 'yyyymmdd') >= '20250101'
								AND o.ORDER_STATE = '배달완료'
							GROUP BY
								c.CUSTOMER_CODE,
								c.CUSTOMER_NAME
					)) t
					WHERE
						RANK < 11
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("customerName",  rs.getString("name"));
					map.put("cnt", rs.getInt("cnt"));
					map.put("rank", rs.getInt("rank"));
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
	
		
		
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 특정년도의 월별 주문 금액 : 막대 차트
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
		public List<Map<String, Object>> selectOrderPriceByYM(String fromYM, String toYM) {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			String sql = """
					
					SELECT
						to_char(createdate, 'yyyy-mm') AS ym,
						sum(order_price) AS totalPrice
					FROM
						orders
					WHERE
						createdate BETWEEN 
						to_date ( '2025-01-01', 'YYYY-MM-DD') 
						AND to_date ( '2025-12-31', 'YYYY-MM-DD')
						AND ORDER_STATE = '배달완료'
					GROUP BY to_char(createdate, 'yyyy-mm')
					ORDER BY ym asc
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ym",  rs.getString("ym"));
					map.put("totalPrice", rs.getInt("totalPrice"));
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
		
		
	
		/**
		 * 
		 * 2025. 11. 12.
		 * Author - tester
		 * 관리자 - 통계관리
		 * 특정년도의 월별 주문 수량 : 막대 차트 
		 * @param fromYM
		 * @param toYM
		 * @return
		 */
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
					AND ORDER_STATE = '배달완료'
				GROUP BY to_char(createdate, 'yyyy-mm')
				ORDER BY ym asc
				
					""";
			
			try {
				
				conn = DBConnection.getConn();
				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, fromYM);
//				psmt.setString(2, toYM);
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

	
	/**
	 * 2025. 11. 11.
	 * Author - tester
	 * 관리자 - 통계관리
	 * 특정년도의 월별 누적 주문수량 : 선 차트
	 * @param fromYM
	 * @param toYM
	 * @return
	 */
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
					WHERE createdate BETWEEN 
						to_date ( '2025-01-01', 'YYYY-MM-DD') 
						AND to_date ( '2025-12-31', 'YYYY-MM-DD')
						AND ORDER_STATE = '배달완료'
					GROUP BY to_char(createdate, 'yyyy-mm')
				) t
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, fromYM);
//			psmt.setString(2, toYM);
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
	
	/**
	 * 
	 * 2025. 11. 11.
	 * Author - tester
	 * 관리자 - 통계관리
	 * 특정년도의 월별 누적 주문금액 : 선 차트
	 * @param fromYM
	 * @param toYM
	 * @return
	 */
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
				WHERE createdate BETWEEN 
						to_date ( '2025-01-01', 'YYYY-MM-DD') 
						AND to_date ( '2025-12-31', 'YYYY-MM-DD')
						AND ORDER_STATE = '배달완료'
					GROUP BY to_char(createdate, 'yyyy-mm') ) t
				
				""";
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, fromYM);
//			psmt.setString(2, toYM);
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
