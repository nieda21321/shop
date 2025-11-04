package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;

public class EmpDao {
	
	/**
	 * 2025. 11. 04.
	 * Author - tester
	 * 사원 계정 상태 변경 기능 
	 * 
	 * @param empCode
	 * @param newActive
	 * @return
	 * @throws Exception
	 */
	public int modifyEmpMemberActive(int empCode, int newActive) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int row = 0;
		
		String sql = """
				UPDATE EMP 
					SET ACTIVE = ?
				WHERE EMP_CODE = ?
				""";
	
			conn = DBConnection.getConn();
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, newActive);
			psmt.setInt(2, empCode);
			
			row = psmt.executeUpdate();
			
			if(row > 0) {
				
				System.out.println("MODIFY EMP MEMBER ACTIVE SUCCESS");
			} else {
				
				System.out.println("MODIFY EMP MEMBER ACTIVE FAILED");
			}

		return row;
	}
	
	
	/**
	 * 
	 * 2025. 11. 04.
	 * Author - tester
	 * 사원 추가 기능
	 * 
	 * @param empDto
	 * @return
	 * @throws Exception
	 */
	public int addEmpMember(Emp empDto) throws Exception{
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		Emp emp = null;
		int row = 0;
		
		String sql = """
				INSERT INTO emp (emp_code , emp_id, emp_pw, emp_name, active, createdate)
				VALUES (seq_emp.nextval, ?, ?, ?, 1, sysdate)
				""";
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		psmt.setString(1, empDto.getEmpId());
		psmt.setString(2, empDto.getEmpPw());
		psmt.setString(3, empDto.getEmpName());
		
		row = psmt.executeUpdate();
		
		if(row > 0) {
			
			System.out.println("ADD EMP MEMBER SUCCESS");
		} else {
			
			System.out.println("ADD EMP MEMBER FAILED");
		}
		
		return row;
	}
	
	/**
	 * 2025. 11. 04.
	 * Author - tester
	 * 사원 회원가입 아이디 중복 체크
	 * 
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public int addEmpMemberIdValid(String Id) throws Exception{
		
		int validChk = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT count(id)
				FROM
				( 
					SELECT customer_id AS id FROM customer
				UNION ALL
					SELECT emp_id AS id FROM emp 
				UNION ALL
					SELECT id FROM outid )
				WHERE id = ?
				""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		
		psmt.setString(1, Id);
		
		rs = psmt.executeQuery();
		
		while(rs.next()) {
			
			validChk = rs.getInt("count(id)");
		}
		
		return validChk;
	}
	
	/**
	 * 2025. 11. 04.
	 * Author - tester
	 * 사원관리 페이징
	 * 
	 * @param beginRow
	 * @param rowPerPage
	 * @return
	 * @throws Exception
	 */
	public List<Emp> selectEmpListByPage(int beginRow, int rowPerPage) throws Exception{
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = """
				SELECT
					emp_code as empCode, 
					emp_id as empId, 
					emp_name as empName, 
					active, 
					createdate 
				FROM
					emp
				ORDER BY
					emp_code
				offset ? ROWS FETCH NEXT ? ROWS ONLY
				""";
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);

		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);
		
		rs = psmt.executeQuery();
		
		List<Emp> list = new ArrayList<Emp>();
		while(rs.next()) {
			
			Emp e = new Emp();
			e.setEmpCode(rs.getInt("empCode"));
			e.setEmpId(rs.getString("empId"));
			e.setEmpName(rs.getString("empName"));
			e.setActive(rs.getInt("active"));
			e.setCreatedate(rs.getString("createdate"));
			list.add(e);
		}
		
		return list;
	}
	
	
	/**
	 * 
	 * 2025. 11. 04.
	 * 마지막 페이징 값
	 * 
	 * @param rowPerPage
	 * @return
	 * @throws Exception
	 */
	public int EmpListLastPage(int rowPerPage) throws Exception{
		
		int lastPage = 0;
		int allCnt = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT COUNT(*) FROM EMP
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
					active,
					createdate
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
			emp.setActive(rs.getInt("active"));
			emp.setCreatedate(rs.getString("createdate"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return emp;
	}
}
