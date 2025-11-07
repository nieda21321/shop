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

public class NoticeDao {
	
	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 리스트 
	 * @param beginRow
	 * @param rowPerPage
	 * @return list
	 */
	public List<Notice> selectNoticeList(int beginRow, int rowPerPage) {
		
		List<Notice> list = new ArrayList<Notice>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT 
				    ROW_NUMBER() OVER (ORDER BY createdate ASC) AS noticeNum,
				    notice_code AS noticecode,
				    notice_title AS noticetitle,
				    notice_content AS noticecontent,
				    emp_code AS empcode,
				    createdate
				FROM notice
				ORDER BY noticeNum DESC
				OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		try {
		
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, beginRow);
			psmt.setInt(2, rowPerPage);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				Notice n = new Notice();
				n.setNoticeNum(rs.getString("noticeNum"));
				n.setNoticeCode(rs.getInt("noticecode"));
				n.setNoticeTitle(rs.getString("noticetitle"));
				n.setNoticeContent(rs.getString("noticecontent"));
				n.setEmpCode(rs.getInt("empcode"));
				n.setCreatedate(rs.getString("createdate"));
				list.add(n);
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
	 * 2025. 11. 07.
	 * 마지막 페이징 값
	 * 
	 * @param rowPerPage
	 * @return lastPage
	 * @throws Exception
	 */
	public int noticeListLastPage(int rowPerPage){
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT COUNT(*) FROM notice
				
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
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 세부사항
	 * @param noticeCode
	 * @return noticeOneList
	 */
	public List<Map<String, Object>> selectNoticeOne(int noticeCode) {
		
		List<Map<String, Object>> noticeOneList = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				
				SELECT
					n.notice_code AS noticecode,
					n.notice_title AS noticetitle,
					n.notice_content AS noticecontent,
					n.emp_code AS empcode,
					n.createdate,
					e.EMP_ID AS empId,
					e.EMP_NAME AS empName
				FROM
					notice n
				INNER JOIN emp e
								ON
					n.EMP_CODE = e.EMP_CODE
				WHERE
					n.notice_code = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, noticeCode);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("noticeCode", rs.getInt("noticeCode"));
				m.put("noticeTitle", rs.getString("noticetitle"));
				m.put("noticeContent", rs.getString("noticecontent"));
				m.put("empCode", rs.getInt("empcode"));
				m.put("createdate", rs.getString("createdate"));
				m.put("empId", rs.getString("empId"));
				m.put("empName", rs.getString("empName"));
				noticeOneList.add(m);
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
		
		return noticeOneList;
	}


	/**
	 * 
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항등록
	 * @param n
	 * @return row
	 */
	public int insertNotice(Notice n) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				INSERT INTO notice 
				(notice_code,
					notice_title,
					NOTICE_CONTENT,
					emp_code,
					CREATEDATE)
				VALUES (seq_notice.nextval,
				?,
				?,
				?,
				sysdate)
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, n.getNoticeTitle());
			psmt.setString(2, n.getNoticeContent());
			psmt.setInt(3, n.getEmpCode());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("INSERT NOTICE FAILED NO DATA");
			} else {
				
				System.out.println("INSERT NOTICE SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("INSERT NOTICE ROLLBACK");
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
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항수정
	 * @param n
	 * @return row
	 */
	public int updateNotice(Notice n) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				UPDATE
					NOTICE
				SET
					NOTICE_TITLE = ?,
					notice_content = ?,
					CREATEDATE = sysdate
				WHERE
					NOTICE_CODE = ?
					AND EMP_CODE = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, n.getNoticeTitle());
			psmt.setString(2, n.getNoticeContent());
			psmt.setInt(3, n.getNoticeCode());
			psmt.setInt(4, n.getEmpCode());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("UPDATE NOTICE FAILED NO DATA");
			} else {
				
				System.out.println("UPDATE NOTICE SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("UPDATE NOTICE ROLLBACK");
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
	 * 2025. 11. 07.
	 * Author - tester
	 * 관리자 - 공지사항관리 - 공지사항삭제
	 * @param n
	 * @return row
	 */
	public int removeNotice(Notice n) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = """
				
				DELETE FROM notice
				WHERE notice_code = ? AND emp_code = ?
				
				""";
		
		try {
			
			conn = DBConnection.getConn();
			// 오토커밋해제처리
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, n.getNoticeCode());
			psmt.setInt(2, n.getEmpCode());
			
			row = psmt.executeUpdate();
			
			if ( row < 1 ) {
				
				System.out.println("DELETE NOTICE FAILED NO DATA");
			} else {
				
				System.out.println("DELETE NOTICE SUCCESS");
				conn.commit();
			}
		} catch (Exception e) {

			try {
				
				System.out.println("DELETE NOTICE ROLLBACK");
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
