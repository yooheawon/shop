package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 커스터머 리스트를 출력하기 위해
	public List<Customer> selectCustomerList(Connection conn, final int rowPerPage, final int beginRow) throws SQLException{
		List<Customer> list = new ArrayList<Customer>();
		/*
		 	SELECT * FROM customer limit ?,?
		 */
		String sql="SELECT customer_id customerId, customer_pass customerPw,customer_name customerName, customer_Address customerAddress"
				+ ",customer_telephone CustomerTelephone, update_date updateDate, create_date createDate"
				+ " FROM customer limit ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			
			// 디버깅
			System.out.println("beginRow : " + beginRow);
			System.out.println("rowPerPage : " + rowPerPage);
			// 쿼리
			rs = stmt.executeQuery();
			System.out.println(rs+"ff");
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerId(rs.getString("CustomerId"));
				customer.setCustomerPw(rs.getString("CustomerPw"));
				customer.setCustomerName(rs.getString("CustomerName"));
				customer.setCustomerAddress(rs.getString("CustomerAddress"));
				customer.setCustomerTelephone(rs.getString("CustomerTelephone"));
				customer.setUpdateDate(rs.getString("updateDate"));
				customer.setCreateDate(rs.getString("createDate"));
				
				// 디버깅
				System.out.println(customer.toString());
				
				list.add(customer);
			}
		} finally{
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
		}
		return list;
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int updateCustomerPw(Connection conn , Customer customer) throws SQLException {
		int row = 0;
		/*
		 * UPDATE customer Set customer_pass=? where customer_id=?
		 */
		String sql = "UPDATE customer Set customer_pass=? where customer_id=?";
		PreparedStatement stmt = null;
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, customer.getCustomerPw());
			stmt.setString(2, customer.getCustomerId());
			row = stmt.executeUpdate();
			//디버깅
			System.out.println("row : " + row);
		} finally {
			if(stmt != null) {stmt.close();}
		}
		return row;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// admin에서 고객 삭제
	public int deleteCustomerByAdmin(String customerId, Connection conn) throws SQLException {
		int row=0;
		/*
		 * DELETE FROM customer WHERE customer_id
		 */
		String sql  = "DELETE FROM customer WHERE customer_id=?";
		PreparedStatement stmt  = null;
		try {
			// 쿼리 담기
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, customerId);
			// 디버깅
			System.out.println("deleteCustomerByAdmin : " + stmt);
			// 쿼리 실행
			row = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return row;
	}
}
