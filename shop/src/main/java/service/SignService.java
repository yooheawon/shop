package service;

import java.sql.Connection;
import java.sql.SQLException;

import repository.DBUtil;
import repository.SignDao;

public class SignService {
   private SignDao signDao;
   // return 
   // true : 사용가능한 아이디
   // false : 
   public boolean idCheck(String id) { 
      boolean result = false;
      this.signDao = new SignDao();
      
      Connection conn = null;

      try {
    	  conn = new DBUtil().getConnection();
    	  conn.setAutoCommit(false);
		 
    	 SignDao signDao = new SignDao();
         if(signDao.idCheck(conn, id) == null) {
            result = true;
         }
         
         conn.commit();
         
      } catch (Exception e) {
         e.printStackTrace();
         try {
            conn.rollback();
         } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      } finally {
    	  if(conn != null) {
	         try {
	        	 conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
    	  }
      }
      return true;
   }
}