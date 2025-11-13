package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Notice;
import dto.QuestionComment;

public class QuestionDao {
	
	
	/**
	 * 
	 * 2025. 11. 12.
	 * Author - tester
	 * 관리자 - 질문관리 리스트 
	 * @param beginRow
	 * @param rowPerPage
	 * @return list
	 */
	public List<Map<String, Object>> selectQuestionList(int beginRow, int rowPerPage) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT
					q.QUESTION_CODE AS questionCode,
					c.CUSTOMER_NAME AS customerName,
					c.CUSTOMER_ID AS customerId,
					q.ORDER_CODE AS orderCode,
					g.GOODS_NAME AS goodsName,
					q.CATEGORY AS category,
					q.QUESTION_MEMO AS questionMemo,
					q.CREATEDATE AS createdate,
					qc.COMMENT_CODE AS commentCode,
					qc.COMMENT_MEMO AS commentMemo
				FROM
					question q
				LEFT OUTER JOIN question_comment qc 
				ON q.QUESTION_CODE = qc.QUESTION_CODE
				INNER JOIN ORDERS o 
				ON q.ORDER_CODE = o.ORDER_CODE
				INNER JOIN goods g
				ON o.GOODS_CODE = g.GOODS_CODE 
				INNER JOIN CUSTOMER c 
				ON o.CUSTOMER_CODE = c.CUSTOMER_CODE
				ORDER BY q.createdate desc;
				OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
				
				""";
		
		try {
		
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, beginRow);
			psmt.setInt(2, rowPerPage);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("questionCode", rs.getInt("questionCode"));    
				m.put("customerName", rs.getString("customerName")); 
				m.put("customerId", rs.getString("customerId"));     
				m.put("orderCode", rs.getInt("orderCode"));          
				m.put("goodsName", rs.getString("goodsName"));       
				m.put("category", rs.getString("category"));         
				m.put("questionMemo", rs.getString("questionMemo")); 
				m.put("createdate", rs.getString("createdate"));     
				m.put("commentCode", rs.getInt("commentCode"));      
				m.put("commentMemo", rs.getString("commentMemo"));   
				list.add(m);
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (psmt != null) psmt.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
			
		}
		
		return list;
	}

	/**
	 * 
	 * 2025. 11. 12.
	 * 마지막 페이징 값
	 * 
	 * @param rowPerPage
	 * @return lastPage
	 * @throws Exception
	 */
	public int questionListLastPage(int rowPerPage){
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT COUNT(*) FROM (
				SELECT
					q.QUESTION_CODE AS questionCode,
					c.CUSTOMER_NAME AS customerName,
					c.CUSTOMER_ID AS customerId,
					q.ORDER_CODE AS orderCode,
					g.GOODS_NAME AS goodsName,
					q.CATEGORY AS category,
					q.QUESTION_MEMO AS questionMemo,
					q.CREATEDATE AS createdate,
					qc.COMMENT_CODE AS commentCode,
					qc.COMMENT_MEMO AS commentMemo
				FROM
					question q
				LEFT OUTER JOIN question_comment qc 
				ON q.QUESTION_CODE = qc.QUESTION_CODE
				INNER JOIN ORDERS o 
				ON q.ORDER_CODE = o.ORDER_CODE
				INNER JOIN goods g
				ON o.GOODS_CODE = g.GOODS_CODE 
				INNER JOIN CUSTOMER c 
				ON o.CUSTOMER_CODE = c.CUSTOMER_CODE
				)
				
				""";
		
		try {
			
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
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (psmt != null) psmt.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
		}
		
		return lastPage;
	}
	
	/**
	 * 
	 * 2025. 11. 12.
	 * Author - tester
	 * 관리자 - 질문관리 - 답변등록
	 * @param n
	 * @return row
	 */
	public int insertQuestionComment(QuestionComment qc) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				INSERT INTO question_comment 
				(comment_code,
					question_code,
					comment_memo,
					CREATEDATE)
				VALUES (seq_comment.nextval,
				?,
				?,
				sysdate)
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, qc.getQuestionCode());
			psmt.setString(2, qc.getCommentMemo());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("INSERT QUESTION COMMENT FAILED");
			} else {
				
				System.out.println("INSERT QUESTION COMMENT SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("INSERT QUESTION COMMENT ROLLBACK");
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			
			try {
				
				if (psmt != null) psmt.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
			
		}
		
		return row;
	}
	
	/**
	 * 
	 * 2025. 11. 12.
	 * Author - tester
	 * 관리자 - 질문관리 - 답변수정
	 * @param n
	 * @return row
	 */
	public int updateQuestionComment(QuestionComment qc) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				UPDATE
					question_comment
				SET
					COMMENT_MEMO = ?,
					createdate = sysdate
				WHERE
					comment_code = ?
					AND question_code = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, qc.getCommentMemo());
			psmt.setInt(2, qc.getCommentCode());
			psmt.setInt(3, qc.getQuestionCode());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("UPDATE QUESTION COMMENT FAILED");
			} else {
				
				System.out.println("UPDATE QUESTION COMMENT SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("UPDATE QUESTION COMMENT ROLLBACK");
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			
			try {
				
				if (psmt != null) psmt.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
		}
		
		return row;
	}
	
	/**
	 * 
	 * 2025. 11. 12.
	 * Author - tester
	 * 관리자 - 질문관리 - 답변삭제
	 * @param n
	 * @return row
	 */
	public int removeQuestionComment(QuestionComment qc) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				DELETE FROM question_comment
				WHERE
					comment_code = ?
					AND question_code = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, qc.getCommentCode());
			psmt.setInt(2, qc.getQuestionCode());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("DELETE QUESTION COMMENT FAILED");
			} else {
				
				System.out.println("DELETE QUESTION COMMENT SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("DELETE QUESTION COMMENT ROLLBACK");
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			
			try {
				
				if (psmt != null) psmt.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
		}
		
		return row;
	}

}
