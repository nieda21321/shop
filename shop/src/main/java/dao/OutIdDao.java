package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Outid;

public class OutIdDao {
	
	/**
	 * 
	 * 2025. 11. 05.
	 * Author - tester
	 * 탈퇴회원관리 리스트 
	 * @param beginRow
	 * @param rowPerPage
	 * @return
	 * @throws Exception
	 */
	public List<Outid> selectOutIdList(int beginRow, int rowPerPage) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					id,
					memo,
					createdate
				FROM
					outid
				ORDER BY createdate desc
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);
		
		rs = psmt.executeQuery();
		
		List<Outid> outIdList = new ArrayList<Outid>();
		
		while (rs.next()) {
			
			Outid o = new Outid();
			o.setId(rs.getString("id"));
			o.setMemo(rs.getString("memo"));
			o.setCreatedate(rs.getString("createdate"));
			outIdList.add(o);
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		return outIdList;
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
	public int outIdListLastPage(int rowPerPage) throws Exception{
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT COUNT(*) FROM outid
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
