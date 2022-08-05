package service;

import java.sql.Connection;

import repository.DBUtil;
import repository.EmployeeDao;
import repository.OutIdDao;
import vo.Employee;

public class EmployeeService {
///////////////////////////////////////////////////////////
	// 회원탈퇴
		// 
	public boolean removeEmployee(Employee paramEmployee) {
		Connection conn=null;
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false); //  executeUpdate()실행시 자동 COMMIT실행 안되게 막음 
					
					
			// 두개의 Dao 동일한 conn이 들어가도록
			// CustomerDao 호출 - 삭제
			EmployeeDao EmployeeDao = new EmployeeDao();
			if(EmployeeDao.deleteEmployee(conn, paramEmployee)!=1) { // 1)
				throw new Exception();
			};
			// outid 테이블에 삭제한 아이디 insert
			OutIdDao OutIdDao = new OutIdDao();
			if(OutIdDao.insertOutId(conn, paramEmployee.getEmployeeId())!=1) {
			//롤백을 해도 되지만 예외를 만들어 처리  conn.rollback();
				throw new Exception();

			}; 
			
			// 위의 두개가 정확히 확인 되면 커밋
			conn.commit();
			
		}catch(Exception e){
			// 콘솔창에 예외가 생기면 띄워주는 코드
			e.printStackTrace();
			// try 에서 문제가 생기면 무조건 롤백하겠다.
			
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false; // 탈퇴 실패
		}finally {
			
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return true; // 탈퇴 성공
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입
	public void  addEmployee(Employee paramEmployee) {
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			conn.getAutoCommit();
			
			EmployeeDao employDao = new EmployeeDao();
			
			try {
				employDao.insertEmployee(conn, paramEmployee);
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn.commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}finally {
			try {
				conn.close();
			}catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Employee getEmployee(Employee paramEmployee) {
		Connection conn=null;
		Employee selectEmployeeByIdAndPw = null;
		try {
			conn=new DBUtil().getConnection();
			conn.setAutoCommit(false); // 자동 COMMIT방지
			
			// CustomerDao 호출 - 로그인
			EmployeeDao employeeDao = new EmployeeDao();
			selectEmployeeByIdAndPw = employeeDao.selectEmployeeByIdAndPw(conn, paramEmployee);
			if (selectEmployeeByIdAndPw == null) {
				System.out.println("EmployeeDaoServicepage 로그인 실패");
				throw new Exception();
			}else {
				System.out.println("EmployeeDaoServicepage 로그인 성공");
				
			}
			conn.commit(); // 문제 없으면 commit
		}catch(Exception e){
			// 콘솔창에 예외가 생기면 띄워주는 코드
			e.printStackTrace();
			// try 에서 문제가 생기면 무조건 롤백하겠다.
			
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return selectEmployeeByIdAndPw; // 탈퇴 실패
		}finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return selectEmployeeByIdAndPw; // 탈퇴 실패
	}
}