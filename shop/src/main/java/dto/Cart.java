package dto;

public class Cart {
	
	private int goodsCode;
	private int customerCode;
	private int cartQuantity;
	private String createDate;
	
	public int getGoodsCode() {
		return goodsCode;
	}
	
	public void setGoodsCode(int goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	public int getCustomerCode() {
		return customerCode;
	}
	
	public void setCustomerCode(int customerCode) {
		this.customerCode = customerCode;
	}
	
	public int getCartQuantity() {
		return cartQuantity;
	}
	
	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Cart [goodsCode=" + goodsCode + ", customerCode=" + customerCode + ", cartQuantity=" + cartQuantity
				+ ", createDate=" + createDate + "]";
	}
}
