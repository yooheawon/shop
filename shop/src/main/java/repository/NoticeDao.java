package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mariadb.jdbc.internal.com.read.dao.Results;

import vo.Notice;

public class NoticeDao implements INoticeDao {
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 추가
	@Override
	public int insertNotice(Connection conn, Notice notice) throws Exception {
		// 리턴값 초기화
		int insertNotice = 0;
		String sql = "INSERT INTO notice (notice_no, notice_title, notice_content, update_date, create_date) VALUES (?,?,?,NOW(),NOW())";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, notice.getNoticeNo());
		stmt.setString(2, notice.getNoticeTitle());
		stmt.setString(3, notice.getNoticeContent());
		
		insertNotice = stmt.executeUpdate();
		// 디버깅
		System.out.println("insertNotice : "  + insertNotice);
		if (stmt != null) {
			stmt.close();
		}
		return insertNotice;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 리스트, 페이지
	@Override
	public Map<String, Object> selectNoticeListByPage(Connection conn, int rowPerPage, int beginRow) throws Exception {
		// 리턴할 리스트 생성
		Map<String, Object> map = new HashMap<String, Object>();
		List<Notice> list = new ArrayList<Notice>();
		
		String sql = "SELECT * FROM notice LIMIT ?,?"; 
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, beginRow);
		stmt.setInt(2, rowPerPage);
		
		rs = stmt.executeQuery();
		
		// 디버깅
		System.out.println(" selectNoticeList rs: "+rs);
		
		while(rs.next()) {
			// 객체생성
			Notice notice = new Notice();
			notice.setNoticeNo(rs.getInt("notice_no"));
			notice.setNoticeTitle(rs.getString("notice_title"));
			notice.setNoticeContent(rs.getNString("notice_content"));
			notice.setUpdateDate(rs.getString("update_date"));
			notice.setCreateDate(rs.getString("create_date"));
			//리스트에 담기
			list.add(notice);
			//디버깅
			System.out.println("list : "+list);
		}
		map.put("list",list);
		if (rs != null) { rs.close(); } 
		if (stmt != null) { stmt.close(); } 
		
		return map;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 페이징
	@Override
	public int NoticeLastPage(Connection conn, int rowPerPage) throws Exception {
		
		// 총 게시물 갯수
		String sql = "SELECT COUNT(*) FROM notice";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int lastPage = 0;
		int totalRow = 0;
		
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			totalRow  = rs.getInt("COUNT(*)");
		}
		
		lastPage = totalRow / rowPerPage;
		if (totalRow % rowPerPage != 0) {
			lastPage += 1;
		}
		
		if ( rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		return lastPage;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 수정
	@Override
	public int updateNotice(Connection conn, Notice notice) throws Exception {
		// 리턴값 초기화
		int updateNotice = 0;
		String sql ="UPDATE FROM notice SET notice_title=?, notice_content=?, update_date=NOW() WHERE notice_no =?";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, notice.getNoticeTitle());
		stmt.setString(2, notice.getNoticeContent());
		stmt.setInt(3, notice.getNoticeNo());
		updateNotice = stmt.executeUpdate();
		
		// 디버깅
		System.out.println("updateNoticeDao : " + updateNotice);
		if (stmt!=null) {
			stmt.close();
		}
		return updateNotice;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 삭제
	@Override
	public int deleteNotice(Connection conn, Notice notice) {
		// TODO Auto-generated method stub
		return 0;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 수정
	@Override
	public List<Notice> selectBoardOne(Connection conn, int noticeNo) throws Exception{
		// 리턴값 초기화 - 번호에 등록된 모든 정보 가져와야하니 리스트
		List<Notice> list = null;
		Notice notice = null;
		String sql = "SELECT notice_title, notice_content, update_date, create_date FROM notice WHERE notice_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, noticeNo);
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			notice.setNoticeNo(rs.getInt("notice_no"));
			notice.setNoticeTitle(rs.getString("notice_title"));
			notice.setNoticeContent(rs.getString("notice_content"));
			notice.setUpdateDate(rs.getString("update_date"));
			
			list.add(notice);
			// 디버깅
			System.out.println("list : "+ list.toString());
		}
		if (rs != null) { rs.close(); } 
		if (stmt != null) { stmt.close(); }
		
		return null;
	}
}
