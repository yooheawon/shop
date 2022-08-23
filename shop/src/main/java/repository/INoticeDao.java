package repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vo.Notice;

public interface INoticeDao {
	public int insertNotice(Connection conn,  Notice notice) throws Exception;
	// 공지사항 리스트 페이지
	public Map<String, Object> selectNoticeListByPage(Connection conn, int rowPerPage, int beginRow) throws Exception;
	//페이징
	public int NoticeLastPage(Connection conn, int rowPerPage) throws Exception;
	// 수정
	public int updateNotice(Connection conn, Notice notice) throws Exception;
	// 삭제
	public int deleteNotice(Connection conn, Notice notice);
	//상세보기
	public List<Notice> selectBoardOne(Connection conn, int noticeNo) throws Exception;


}
