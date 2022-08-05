package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Customer;
import vo.Employee;

public class EmployeeDao {
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 탈퇴
	public int deleteEmployee(Connection conn, Employee paramEmployee) throws ClassNotFoundException, SQLException {
		// OutIdDao 동일한 conn 사용하도록 만들어줘야함 - removeCustomer에서 제공
		// conn.close() 못함
		// 양쪽다 성공해야 commit 아니면 rollback
		int row=0;
		String sql = "DELETE FROM employee where employee_id=? AND employee_pass=PASSWORD(?)";
		PreparedStatement stmt = null;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramEmployee.getEmployeeId());
			stmt.setString(2, paramEmployee.getEmployeePw());
			row = stmt.executeUpdate();
			stmt.close();
	
		return row;
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입
	public int insertEmployee(Connection conn, Employee paramEmployee) throws SQLException, ClassNotFoundException {
		int row = 0;
		/*
		 INSERT INTO(employee_id, employee_pass, employee_name, update_date, create_date, active)
		 VALUES
		 (?,PASSWORD(?),?,NOW(),NOW(),?)
		 */
		String sql="INSERT INTO employee (employee_id, employee_pass, employee_name, update_date, create_date)\r\n"
				+ "		 VALUES\r\n"
				+ "		 (?,PASSWORD(?),?,NOW(),NOW())";
		PreparedStatement stmt=null;
		try {
			conn = new DBUtil().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramEmployee.getEmployeeId());
			stmt.setString(2, paramEmployee.getEmployeePw());
			stmt.setString(3, paramEmployee.getEmployeeName());
			row = stmt.executeUpdate();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return row;
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	public Employee selectEmployeeByIdAndPw(Connection conn, Employee employee) throws ClassNotFoundException, SQLException {
		// 로그인
		Employee e = null;
		String sql="SELECT * FROM employee where employee_id=? AND employee_pass=PASSWORD(?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, employee.getEmployeeId());
		stmt.setString(2, employee.getEmployeePw());
		System.out.println(stmt);
		
		rs = stmt.executeQuery();
		System.out.println(rs + "는 rs ");
		
		if(rs.next()) {
			// c 인스턴스 채우기
			e = new Employee();
			e.setEmployeeId(rs.getString("employee_id"));
			e.setEmployeeName(rs.getString("employee_name"));
			System.out.println("e에 데이터 셋팅 성공");
		}
		if(rs!=null) {
			rs.close();
		}
		if(stmt!=null) {
			stmt.close();
		}
		System.out.println("로그인 작동 정상");
		return e ;
	}
}
