package dto;

public class CustomerAddress {

	private int addressCode;
	private int customerCode;
	private String address;
	private String createDate;
	
	public int getAddressCode() {
		
		return addressCode;
	}
	
	public void setAddressCode(int addressCode) {
		this.addressCode = addressCode;
	}
	
	public int getCustomerCode() {
	
		return customerCode;
	}
	
	public void setCustomerCode(int customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "CustomerAddress [addressCode=" + addressCode + ", customerCode=" + customerCode + ", address=" + address
				+ ", createDate=" + createDate + "]";
	}
}
