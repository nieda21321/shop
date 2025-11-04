package dto;


/**
 * 
 * 2025. 11. 03.
 * author : tester
 * table : customer
 * pk : customerCode
 */

public class Emp {
	
	private int empCode;
	private String empId;
	private String empPw;
	private String empName;
	private int active;
	private String createdate;
	
	public int getEmpCode() {
		return empCode;
	}
	
	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public String getEmpPw() {
		return empPw;
	}
	
	public void setEmpPw(String empPw) {
		this.empPw = empPw;
	}
	
	public String getEmpName() {
		return empName;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public int getActive() {
		return active;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	@Override
	public String toString() {
		return "Emp [empCode=" + empCode + ", empId=" + empId + ", empPw=" + empPw + ", empName=" + empName
				+ ", active=" + active + ", createdate=" + createdate + "]";
	}
}
