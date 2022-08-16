package vo;

public class Noitce {
	public int noticeNo;
	public String noticeTitle;
	public String noticeContemt;
	public String updateDate;
	public String createDate;
	
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContemt() {
		return noticeContemt;
	}
	public void setNoticeContemt(String noticeContemt) {
		this.noticeContemt = noticeContemt;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "Noitce [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContemt=" + noticeContemt
				+ ", updateDate=" + updateDate + ", createDate=" + createDate + "]";
	}
}
