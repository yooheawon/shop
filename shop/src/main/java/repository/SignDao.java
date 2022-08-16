package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignDao {
	// id check
	// 커넥션을 주입하는 계층은 컨트롤러계층 다음 서비스계층에서 dao를 호출. 서비스계층에서 conn 주입
   public String ckId(Connection conn, String idck) throws SQLException {
      String id = null;
      /* t는 임시 알리언스; ()안의 결과물을 t라고 임시 이름을 붙인거
       SELECT t.id
       FROM (SELECT customer_id id FROM customer
             UNION
             SELECT employee_id id FROM employee
             UNION
             SELECT out_id id FROM out_id) t
       WHERE t.id = ?   
       // 위의 세 테이블중 등록되어 있는 아이디가 있으면 사용 불가 조건
       --> null일때 사용가능한 아이디
       */
      // if(rs != null) {
      String sql="  SELECT t.id\r\n"
      		+ "       FROM (SELECT customer_id id FROM customer\r\n"
      		+ "             UNION\r\n"
      		+ "             SELECT employee_id id FROM employee\r\n"
      		+ "             UNION\r\n"
      		+ "             SELECT out_id id FROM outid) t\r\n"
      		+ "       WHERE t.id = ?   ";
      PreparedStatement stmt=null;
      ResultSet rs = null;
      
   
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				System.out.print("signDao의 rs 있음");
				id = rs.getString("t.id");
			}
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
			return id;
	}
}