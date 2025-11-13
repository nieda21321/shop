package dto;

public class Question {
	
	private int questionCode;
	private int ordersCode;
	private String category;
	private String qeustionMemo;
	private String createdate;
	
	public int getQuestionCode() {
		return questionCode;
	}
	
	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}
	
	public int getOrdersCode() {
		return ordersCode;
	}
	
	public void setOrdersCode(int ordersCode) {
		this.ordersCode = ordersCode;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getQeustionMemo() {
		return qeustionMemo;
	}
	
	public void setQeustionMemo(String qeustionMemo) {
		this.qeustionMemo = qeustionMemo;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "Question [questionCode=" + questionCode + ", ordersCode=" + ordersCode + ", category=" + category
				+ ", qeustionMemo=" + qeustionMemo + ", createdate=" + createdate + "]";
	}
}
