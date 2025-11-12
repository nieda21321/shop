package dto;

/**
 * 
 * 2025. 11. 03.
 * author : tester
 * table : customer
 * pk : customerCode
 */

public class Customer {
	
	private int customerCode;
	private String customerId;
	private String customerPw; 
	private String customerName;
	private String customerPhone;
	private int point;
	private String createdate;
	private String gender;
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getCustomerCode() {
		return customerCode;
	}
	
	public void setCustomerCode(int customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerPw() {
		return customerPw;
	}
	
	public void setCustomerPw(String customerPw) {
		this.customerPw = customerPw;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "Customer [customerCode=" + customerCode + ", customerId=" + customerId + ", customerPw=" + customerPw
				+ ", customerName=" + customerName + ", customerPhone=" + customerPhone + ", point=" + point
				+ ", createdate=" + createdate + ", gender=" + gender + "]";
	}
}
