package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import vo.GoodsImg;

public class GoodsImgDao {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이미지 삭제
	public int deleteGoodImg(Connection conn, GoodsImg goodImg) throws SQLException {
		int row = 0;
		/* 
		 	DELETE FROM goodImg 
		 	WHERE goodImg_no?
		 */
		String sql = "DELETE FROM goodImg \r\n"
				+ "		 	WHERE goodImg_no?";
		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, goodImg.getImggoodsNo());
		row = stmt.executeUpdate();
		
		System.out.println("deleteImg 값 " + row);
		if (stmt != null) {
			stmt.close();
		}
		return row;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이미지 추가
	public int insertGoodsImg(Connection conn, GoodsImg goodsImg) throws SQLException {
		int row=0;
		/*
		 	INSERT INTO goods_img(goods_no , filename, origin_filename, content_type, create_date)
		 	VALUES(?,?,?,?,NOW())
		 */
		String sql="INSERT INTO goods_img(goods_no , filename, origin_filename, content_type, create_date)\r\n"
				+ "		 	VALUES(?,?,?,?,NOW())";
		PreparedStatement stmt = null;
		stmt=conn.prepareStatement(sql);
		stmt.setInt(1, goodsImg.getImggoodsNo());
		stmt.setString(2, goodsImg.getImgFileName());
		stmt.setString(3, goodsImg.getImgOriginFileName());
		stmt.setString(4, goodsImg.getImgContentType());
		
		row = stmt.executeUpdate();
		System.out.println("imageDao의 row 값 : "+row);
		if (stmt != null) {
			stmt.close();
		}
		return row;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 상품 이미지 수정
	public int updateGoodsImg(Connection conn , GoodsImg goodsImg) throws Exception {
		// 리턴값 초기화
		int updateGoodsImg = 0;
		String sql = "UPDATE goods_img SET filename=? origin_filename=?, content_type=?, create date= NOW() WHERE goods_no;";
		PreparedStatement stmt = null;
		
		// 입력값 불러오기
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, goodsImg.getImgFileName());
		stmt.setString(2, goodsImg.getImgOriginFileName());
		stmt.setString(3, goodsImg.getImgContentType());
		stmt.setString(4, goodsImg.getImgCreateDate());
		stmt.setInt(5, goodsImg.getImggoodsNo());
		
		updateGoodsImg = stmt.executeUpdate();
		// 디버깅
		System.out.println("상품이미지 dao : "+ updateGoodsImg);
		
		if ( stmt != null) {
			stmt.close();
		}
		return updateGoodsImg;
	}
}