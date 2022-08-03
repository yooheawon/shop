package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Customer;
import vo.Employee;

public class EmployeeDao {
	public Employee login(Employee employee) throws ClassNotFoundException, SQLException {
		
		Employee e = null;
		String sql="SELECT * FROM employee where employee_id=? AND employee_pass=PASSWORD(?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			DBUtil dbUtil = new DBUtil();
			conn = dbUtil.getConnection();
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
		}catch(Exception c){
			System.out.println( c );
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
		System.out.println("로그인 작동 정상");
		return e ;
	}
}
