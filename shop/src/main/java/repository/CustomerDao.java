package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import org.mariadb.jdbc.internal.com.read.dao.Results;

import repository.DBUtil;
import service.CustomerService;
import vo.Customer;

public class CustomerDao {
////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입
	public int insertCustomer(Connection conn, Customer paramCustomer) throws SQLException, ClassNotFoundException {
		int c = 0;
		/*
		 INSERT INTO customer
		 (customer_id, customer_pass, customer_name, customer_address, update_date, create_date)
		 VALUES
		 (?,PASSWORD(?),?,?,NOW(),NOW())
		 */
		String sql=" INSERT INTO customer\r\n"
				+ "		 (customer_id, customer_pass, customer_name, customer_address, customer_telephone, update_date, create_date)\r\n"
				+ "		 VALUES\r\n"
				+ "		 (?,PASSWORD(?),?,?,?,NOW(),NOW())";
		PreparedStatement stmt =null;
		try {
			conn = new DBUtil().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramCustomer.getCustomerId() );
			stmt.setString(2, paramCustomer.getCustomerPw());
			stmt.setString(3, paramCustomer.getCustomerName());
			stmt.setString(4, paramCustomer.getCustomerAddress());
			stmt.setString(5, paramCustomer.getCustomerTelephone());
			c = stmt.executeUpdate();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}		
		return c;		
}

////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 탈퇴
	// 아이디와 패스워드가 맞아야 탈퇴
	// CustomerService.removeCustomer(Customer param(Customer))가 호출
	public int deleteCustomer(Connection conn, Customer paramCustomer) throws ClassNotFoundException, SQLException {
		// OutIdDao 동일한 conn 사용하도록 만들어줘야함 - removeCustomer에서 제공
		// conn.close() 못함
		// 양쪽다 성공해야 commit 아니면 rollback
		int row=0;
		String sql = "DELETE FROM customer where customer_id=? AND customer_pass=PASSWORD(?)";
		PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramCustomer.getCustomerId());
			stmt.setString(2, paramCustomer.getCustomerPw());
			row = stmt.executeUpdate();
			stmt.close();
	
		return row;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
 	// 아이디와 패스워드를 입력하면 고객 하나가 나옴 - 로그인
	// CustomerService가 호출함
	public Customer selectCustomerByIdAndPw(Connection conn, Customer customer) throws ClassNotFoundException, SQLException {
		
		Customer c = null;
		String sql="SELECT * FROM customer WHERE customer_id = ? AND customer_pass = password(?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		//run
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
			if(rs!=null) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			//디버깅
			System.out.println("로그인 작동 정상");
			return c;		
	}		
}
