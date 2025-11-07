package dto;

public class Notice {
	
	private int noticeCode;
	private String noticeTitle;
	private String noticeContent;
	private int EmpCode;
	private String createdate;
	
	// 순번 추가
	private String noticeNum;
	
	public String getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(String noticeNum) {
		this.noticeNum = noticeNum;
	}
	
	public int getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(int noticeCode) {
		this.noticeCode = noticeCode;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	
	}
	
	public int getEmpCode() {
		return EmpCode;
	}
	
	public void setEmpCode(int empCode) {
		EmpCode = empCode;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "Notice [noticeCode=" + noticeCode + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", EmpCode=" + EmpCode + ", createdate=" + createdate + "]";
	}
}
