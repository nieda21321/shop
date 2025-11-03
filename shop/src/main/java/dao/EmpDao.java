package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Emp;

public class EmpDao {
	
	/**
	 * 
	 * 2025. 11. 03.
	 * Author - tester
	 * 관리자 로그인
	 * 
	 * @param id
	 * @param pw
	 * @return
	 * @throws Exception
	 */
	public Emp selectEmpByLogin(String id, String pw) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					EMP_CODE as empCode,
					EMP_ID as empId,
					EMP_PW as empPw,
					EMP_NAME as empName,
					ACTIVE,
					CREATEDATE
				FROM
					emp
				WHERE EMP_ID = ? AND EMP_PW = ? AND ACTIVE > 0
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);

		psmt.setString(1, id);
		psmt.setString(2, pw);
		
		rs = psmt.executeQuery();
		
		Emp emp = null;
		
		if (rs.next()) {
			
			emp = new Emp();
			emp.setEmpCode(rs.getInt("empCode"));
			emp.setEmpId(rs.getString("empId"));
			emp.setEmpPw(rs.getString("empPw"));
			emp.setEmpName(rs.getString("empName"));
			emp.setActive(rs.getString("ACTIVE"));
			emp.setCreatedate(rs.getString("CREATEDATE"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return emp;
	}
}
