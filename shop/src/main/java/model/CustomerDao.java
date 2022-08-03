package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Customer;

public class CustomerDao {
	public Customer login(Customer customer) throws ClassNotFoundException, SQLException {
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerPw());
		Customer c = null;
		String sql="SELECT * FROM customer WHERE customer_id = ? AND customer_pass = password(?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		//run
		try {
			System.out.println("try...");
			DBUtil dbUtil = new DBUtil();
			
			conn = dbUtil.getConnection();
			System.out.println(conn+" <--conn");
			
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, customer.getCustomerId());
			stmt.setString(2, customer.getCustomerPw());
			System.out.println(stmt);
			
			rs = stmt.executeQuery();
			System.out.println(rs + " rs 는");
			
			if(rs.next()) {
				// c 인스턴스 채우기
				c = new Customer();
				c.setCustomerId(rs.getString("customer_id"));
				c.setCustomerName(rs.getString("customer_name"));
				System.out.println("c에 데이터 셋팅 성공");
			}
		}catch(Exception e){
			System.out.println( e );
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}		
		//디버깅
		System.out.println("로그인 작동 정상");
		return c;		
	}
}
