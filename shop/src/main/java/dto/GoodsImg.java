package dto;

public class GoodsImg {
	
	private int goodsCode;
	private String fileName;
	private String originName;
	private String contentType;
	private String createdate;
	private long fileSize;
	
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public int getGoodsCode() {
		return goodsCode;
	}
	
	public void setGoodsCode(int goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getOriginName() {
		return originName;
	}
	
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "GoodsImg [goodsCode=" + goodsCode + ", fileName=" + fileName + ", originName=" + originName
				+ ", contentType=" + contentType + ", createdate=" + createdate + "]";
	}
}
