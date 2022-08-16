package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import vo.GoodsImg;

public class GoodsImgDao {
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
}