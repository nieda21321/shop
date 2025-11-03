package dto;

public class PwHistory {
	
	private int customerCode;
	private String pw;
	private String createDate;
	public int getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(int customerCode) {
		this.customerCode = customerCode;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "PwHistory [customerCode=" + customerCode + ", pw=" + pw + ", createDate=" + createDate + "]";
	}
}
