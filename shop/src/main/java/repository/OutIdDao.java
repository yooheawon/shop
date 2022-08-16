package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class OutIdDao {
	// 탈퇴 회원의 아이디를 입력
	// CustomerService.removeCustomer(Customer param(Customer))가 호출
	public int insertOutId(Connection conn, String customerId) throws ClassNotFoundException, SQLException {
		// CustomerDao 동일한 conn을 사용하도록 만들어줘야함 - removeCustomer에서 제공
		// conn.close() 못함
		// 양쪽다 성공해야 commit 아니면 rollback
		int row=0;
		String sql = "INSERT INTO outid(out_id, out_date) VALUES (?, NOW())";
		PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, customerId);
			row = stmt.executeUpdate();	
			stmt.close();			
		return row;
	}
}