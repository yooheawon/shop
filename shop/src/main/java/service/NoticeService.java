package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import repository.DBUtil;
import repository.NoticeDao;
import vo.Notice;

public class NoticeService implements INoticeService {
	private Notice notice;
	private NoticeDao noticeDao;
//////////////////////////////////////////////////////////////////////////	
	// 공지사항 추가
	@Override
	public int addNotice(Notice notice) throws Exception {
		// 리턴값 초기화
		int addNotice= 0;
		Connection conn = null;
		
		try {
			conn = new DBUtil().getConnection();
			// 커밋방지
			conn.setAutoCommit(false);
			// 객체 생성
			this.noticeDao = new NoticeDao();
			
			addNotice = noticeDao.insertNotice(conn, notice);
			
			if (addNotice != 0 ) {
				System.out.println("공지사항 등록 완료");
				conn.commit();
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 등록안되면 롤백
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return addNotice;
	}
//////////////////////////////////////////////////////////////////////////
	// 리스트, 페이지
	@Override
	public Map<String, Object> getNoticeList(int rowPerPage, int currentPage) {
		Map<String, Object> map = null;
		
		int beginRow = (currentPage-1)*rowPerPage;
		int lastPage = 0;
		
		// 메소드 생성
		NoticeDao noticeDao = new NoticeDao();
		
		Connection conn = null;
		
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
			
			 // list를 가진 map 객체 
			map = noticeDao.selectNoticeListByPage(conn, rowPerPage, beginRow);
			
			// 마지막페이지
			lastPage = noticeDao.NoticeLastPage(conn, rowPerPage);
			
			lastPage = lastPage/rowPerPage;
			
			if ((lastPage % rowPerPage) != 0) {
				lastPage += 1;
			}
			map.put("lastPage", lastPage);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
		return map;
	}
//////////////////////////////////////////////////////////////////////////
	// 수정
	@Override
	public List<Notice> selectBoardOne(int boardNo) throws Exception {
		List<Notice> list = null;
		Connection conn = null;
		
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
			this.noticeDao = new NoticeDao();
			list = noticeDao.selectBoardOne(conn, boardNo);
			
			if (list != null) {
				System.out.println("수정성공");
				conn.commit();
			}else {
				System.out.println("수정실패");
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return list;
	}
}
