package repository;

import java.rmi.server.RemoteStub;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.PartialResultException;
import javax.naming.spi.DirStateFactory.Result;

import org.mariadb.jdbc.internal.com.read.dao.Results;

import vo.Goods;
import vo.GoodsImg;

public class GoodsDao {
	 /* 
    클래스가 아닌 인터페이스를 만들어주어야함
   	FM대로하면 vo를 제외한 service와 Dao는 인터페이스로 연결해야함
 	
 	List는 인터페이스 -- ArrayList 는 List를 구성하는 일종의  class
   */
	/*
	 beginLow는 service 페이지에서 넘어옴
	  
	 대부분의 매개변수에는 final을 붙이는게 정석 - 현재 페이지에서는 변경되선 안되기 때문
	 */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public int deleteGoods(Connection conn , Goods goods) throws SQLException, ClassNotFoundException {
		int goodsNo=0;
		/*
		 	DELETE FROM goods where goods_No=? 
		  
		 */
		PreparedStatement stmt = null;
		String sql = "DELETE FROM goods where goods_No=? ";
		try {
			conn = new DBUtil().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, goodsNo);
			goodsNo = stmt.executeUpdate();
			System.out.println("goodDaostmt : "+stmt);
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return goodsNo;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// goods에 저장된 내용 불러오기
	public List<Goods> selectGoodsListByPage(Connection conn, int rowPerPage, int beginRow) throws SQLException{
		List<Goods> list = new ArrayList<Goods>();
		/*
		 	SELECT goods_no goodsNo,
		 		   goods_name goodsName,
		 		   goods_price goodsPrice,
		 		   update_date updateDate,
		 		   create_date createDate,
		 		   sold_out soldOut
		 	FROM goods
		 	ORDER BY good_no DESC
		 	LIMIT ?,?
		 */
		/*
		 상품입력과 사진이 동시에 이루어져야함 -> 트렌젝션처리
		 
		 */
		String sql = "	SELECT goods_no goodsNo,\r\n"
				+ "		 		   goods_name goodsName,\r\n"
				+ "		 		   goods_price goodsPrice,\r\n"
				+ "		 		   update_date updateDate,\r\n"
				+ "		 		   create_date createDate,\r\n"
				+ "		 		   sold_out soldOut\r\n"
				+ "		 	FROM goods\r\n"
				+ "		 	ORDER BY goods_no DESC\r\n"
				+ "		 	LIMIT ?,?";
		ResultSet rs = null;
		PreparedStatement stmt=null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Goods g = new Goods();

				g.setGoodsNo(rs.getInt("goodsNo"));
				g.setGoodsName(rs.getString("goodsName"));
				g.setGoodsPrice(rs.getInt("goodsPrice"));
				g.setUpdateDate(rs.getString("updateDate"));
				g.setCreateDate(rs.getString("createDate"));
				g.setSoldOut(rs.getString("soldOut"));

				System.out.println("goodsDao.selectGoodsListByPage: " + g);
				list.add(g);
			}
		} finally {
			if(rs != null) {
				rs.close();
			}

			if(stmt != null) {
				stmt.close();
			}
		}

		System.out.println("list : " + list);

		return list;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 페이징을 하기위한 총 개시물 수
	public int getLastPage(Connection conn) throws SQLException {

		String sql = "SELECT COUNT(*) FROM goods";
		PreparedStatement stmt=null;
		ResultSet rs= null;
		int totalCount=0;

		stmt = conn.prepareStatement(sql);
		rs=stmt.executeQuery();

		if (rs.next()) {
			totalCount = rs.getInt("COUNT(*)");
		}
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		return totalCount;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 고객 상품 리스트 페이지에서 사용
	public List<Map<String, Object>> selectCustomerGoodsListByPage(Connection conn, int rowPerPage, int beginRow) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// 어느 테이블에서 무엇을 조인할지
		/*
		 * String sql =
		 * "g.goods_no goodNo, g.goods_name goodsName, g.goods_price goodsPrice g.sold_out "
		 * + "FROM goods g INNER JOIN goods_img gi ON goods_no = gi.goods_no" +
		 * "	ORDER BY cteate_date";
		 */
		//판매량수 많은 것
		/*
		 	 SELECT g.goods_no goodsNo
             , g.goods_name goodsName
             , g.goods_price goodsPrice
             , gi.filename filename
       FROM
       goods g LEFT JOIN (SELECT goods_no, SUM(order_quantity) sumNum
                      FROM orders
                      GROUP BY goods_no) t
                      ON g.goods_no = t.goods_no
                         INNER JOIN goods_img gi
                         ON g.goods_no = gi.goods_no
       ORDER BY IFNULL(t.sumNUm, 0) DESC
		 */
		 String sql = "SELECT g.goods_no goodsNo, g.goods_name goodsName, g.goods_price goodsPrice, g.sold_out soldOut, gi.filename fileName\r\n"
		 		+ "FROM goods g INNER JOIN goods_img gi\r\n"
		 		+ "ON g.goods_no = gi.goods_no\r\n"
		 		+ "ORDER BY g.goods_no LIMIT  ?, ?";
		 		
		/* 	
	     String orderSql = " SELECT g.goods_no goodsNo\r\n"
	     		+ "             , g.goods_name goodsName\r\n"
	     		+ "             , g.goods_price goodsPrice\r\n"
	     		+ "             , gi.filename filename\r\n"
	     		+ "       FROM\r\n"
	     		+ "       goods g LEFT JOIN (SELECT goods_no, SUM(order_quantity) sumNum\r\n"
	     		+ "                      FROM orders\r\n"
	     		+ "                      GROUP BY goods_no) t\r\n"
	     		+ "                      ON g.goods_no = t.goods_no\r\n"
	     		+ "                         INNER JOIN goods_img gi\r\n"
	     		+ "                         ON g.goods_no = gi.goods_no\r\n"
	     		+ "       ORDER BY IFNULL(t.sumNUm, 0) DESC";

	     String lowPriceSql = "SELECT g.good_no, g.goods_name goodsName, g.goods_price goodsPrice, gi.filename fileName "
	     		+ "FROM goods g INNER JOIN goods_img gi"
	     		+ "ON g.goods_no = gi.goods_no"
	     		+ "ORDER BY g.goods_price LIMIT?,?";

	     String highPriceSql = "SELECT g.good_no, g.goods_name goodsName, g.goods_price goodsPrice, gi.filename fileName"
	     		+ "FROM goods g INNER JOIN goods_img gi"
	     		+ "ON g.goods_no = gi.goods_no"
	     		+ "ORDER BY g.goods_price LIMIT ? , ?";

	     
			고객이 주문수의 desc 
			SELECT g.goods_no, g.goods_name goodsName, g.goods_price goodsPrice, gi.filename FROM goods g LEFT JOIN (SELECT goods_no, SUM(order_quantity) sumNum FROM orders GROUP BY goods_no) t ON g.goods_no = t.goods_no INNER JOIN goods_img gi ON g.goods_no = gi.goods_no ORDER BY IFNULL(t.sumNum, 0) DESC LIMIT 0,10
		
			인기순
			아직 클릭수가 구현되지 않았음
		
			낮은가격순
			SELECT g.goods_no, g.goods_name goodsName, g.goods_price goodsPrice, gi.filename FROM goods g INNER JOIN goods_img gi ON g.goods_no = gi.goods_no ORDER BY g.goods_price LIMIT 0,10
			
		 */
		 	// 디버깅
		    System.out.println(beginRow + " < beginRow");
			System.out.println(rowPerPage + " < rowPerPage");
	     try {
	    	 stmt = conn.prepareStatement(sql);
	    	 stmt.setInt(1, beginRow);
	    	 stmt.setInt(2, rowPerPage);
	    	 rs = stmt.executeQuery();
	    	System.out.println("rs : " + rs);
	    	 while(rs.next()) {
	    		 // 표시할내용 
	    		 Map<String,Object> map = new HashMap<String, Object>();
	    		 map.put("goodsNo", rs.getInt("goodsNo"));
	    		 map.put("goodsName", rs.getString("goodsName"));
	    		 map.put("goodsPrice", rs.getInt("goodsPrice"));
	    		 map.put("soldOut", rs.getString("soldOut"));
	    		 map.put("fileName", rs.getString("fileName"));

	    		 // list에 담기
	    		 list.add(map);
	    	 }

		} finally {
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
		}
	      return list;
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 상품목록 추가
	  // 반환값 : key값 (jdbc api)
	   public int insertGoods(Connection conn, Goods goods) throws SQLException {
	      int goodsNo = 0;
	      /*
	       	INSERT INTO goods
	       	(goods_name, goods_price, sold_out, update_date, create_date)
	       	VALUES (? , ? , ? ,NOW() , NOW())
	       */
	      String sql="	INSERT INTO goods\r\n"
	      		+ "	       	(goods_name, goods_price, sold_out, update_date, create_date)\r\n"
	      		+ "	       	VALUES (? , ? , ? ,NOW() , NOW())";
	      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	      // RETURN_GENERATED_KEYS == 1 --> 두 번의 쿼리 실행
		  // 1) insert 
		  // 2) select last_ai_key from table

	      stmt.setString(1, goods.getGoodsName());
	      stmt.setInt(2, goods.getGoodsPrice());
	      stmt.setString(3, goods.getSoldOut());

	      stmt.executeUpdate(); // insert 성공한 row 수
	      ResultSet rs = stmt.getGeneratedKeys(); // select last_key 리턴값
	      if(rs.next()) {
	    	  goodsNo = rs.getInt(1);
	    	  System.out.println("GoodsDao.insertDao : "+goodsNo);
	    	// getGeneratedKeys가 반환하는 컬럼명을 알 순 없지만
				// 첫번째라는 것은 알 수 있으므로 rs.getInt(1)
	      }

	      if(stmt!=null) {
	         stmt.close();
	      }
	      if(rs!=null) {
	         stmt.close();
	      }
	      return goodsNo;
	   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// admin 상품 상세보기
	public Map<String, Object> selectGoodsAndImgOne(Connection conn, int goodsNo) throws SQLException {
		/*
		  SELECT g.*, gi.*
		  FROM goods g INNER JOIN goods_img gi
		  ON g.goods_no = gi.goods_no
		  WHERE g.gods_no=?
		 
		 
		 SELECT g.goods_no goodsNo, g.goods_name goodsName, g.goods_price goodsPrice, g.update_date updateDate, g.create_date createDate,
		  		g.sold_out soldOut, gi.filename filename, gi.origin_filename originFilename, gi.content_type contentType 
		 FROM goods g INNER JOIN goods_img gi 
		 ON g.goods_no = gi.goods_no 
		 WHERE g.goods_no = ?
		 
		 
		String sql ="";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		while(rs.next()) {
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("goodsNo", rs.getInt("goodsNo"));
		}
		 쿼리에서 where 조건이 없다면 ... 반환 타임 List<Map<String,object>> list
		 */
		  // 리턴값 초기화
		  Map<String , Object> map =  new HashMap<String, Object>();
		  //db자원 선언
		  PreparedStatement stmt = null;
		  ResultSet rs = null;
		 String sql = " SELECT g.* , gi.* FROM goods g "
			  		+ "INNER JOIN goods_img gi "
			  		+ "ON g.goods_no = gi.goods_no "
			  		+ "WHERE g.goods_no = ?";

		try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, goodsNo);
			System.out.println("stmt에 저장된 내용 : "+stmt);

			// stmt쿼리에 담긴 내용 저장
			rs = stmt.executeQuery();
			if (rs.next()) {
				// map에 전달
				map.put("goodsNo", rs.getInt("g.goods_no"));
				map.put("goodsName", rs.getString("g.goods_name"));
				map.put("goodPrice", rs.getInt("g.goods_price"));
				map.put("updateDate", rs.getString("g.update_date"));
				map.put("createDate", rs.getString("g.create_date"));
				map.put("soldOut", rs.getString("g.sold_out"));
				map.put("imgFileName", rs.getString("gi.filename"));
				map.put("imgOriginFilename", rs.getString("gi.origin_filename"));				
				map.put("imgContentType", rs.getString("gi.content_type"));				
				map.put("imgCreateDate", rs.getString("gi.create_date"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//db 해제
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return map;
	}
/////////////////////////////////////////////////////////////////////////////
	// admin에서 상품 삭제
	public int deleteGoodsByAdmin(int goodsNo, Connection conn) throws SQLException {
		int row = 0;
		String sql = "DELETE FROM goods where goods_no = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, goodsNo);
			// 디버깅
			System.out.println("deleteGoodsByAdmin의 goodsNo : "+goodsNo);
			row = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return row;
	}
//////////////////////////////////////////////////////////////////////////////////////////////
	// 상품 수정 (adminGoodsUpdateForm)
	public int updateGoods(Connection conn, Goods goods) throws Exception {
		// 리턴값 초기화
		int updateGoods = 0;
		
		// 쿼리
		String sql = "UPDATE goods SET gooods_name=?, goods_price=?, sold_out=? WHERE goods_no";
		
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, goods.getGoodsName());
		stmt.setInt(2, goods.getGoodsPrice());
		stmt.setString(3, goods.getSoldOut());
		stmt.setInt(4, goods.getGoodsNo());
		
		// 리턴값에 담기
		updateGoods = stmt.executeUpdate();
		// 디버깅
		System.out.println("상품 업데이트 DAO updateGoods : " + updateGoods);
		if (stmt != null) {
			stmt.close();
		}
		return updateGoods;
		
	}
}