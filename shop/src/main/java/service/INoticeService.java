package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import repository.DBUtil;
import vo.Notice;

public interface INoticeService {
	// 추가
	public int addNotice(Notice notice) throws Exception;
	// 리스트 페이지
	public Map<String, Object> getNoticeList(int rowPerPage, int currentPage);
	//상세보기
	public List<Notice> selectBoardOne(int boardNo) throws Exception;
}
