package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignDao {
   public String idCheck(Connection conn, String id) throws SQLException {
      String ckId = null;
      /*
       SELECT t.id
       FROM (SELECT customer_id id FROM customer
             UNION
             SELECT employee_id id FROM employee
             UNION
             SELECT out_id id FROM out_id) t
       WHERE t.id = ?   
       --> null일때 사용가능한 아이디
       */
      // if(rs != null) {
      // close....
      String sql="  SELECT t.id\r\n"
      		+ "       FROM (SELECT customer_id id FROM customer\r\n"
      		+ "             UNION\r\n"
      		+ "             SELECT employee_id id FROM employee\r\n"
      		+ "             UNION\r\n"
      		+ "             SELECT out_id id FROM outid) t\r\n"
      		+ "       WHERE t.id = ?   ";
      PreparedStatement stmt=null;
      ResultSet rs = null;
      
      try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				System.out.print("있음");
				ckId = rs.getString("id");
			}
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		}
		
		return ckId;
	}
}