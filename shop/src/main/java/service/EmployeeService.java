package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
			if(EmployeeDao.deleteEmployee(conn, paramEmployee) !=1 ) { // 1)
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
	// 로그인
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
// employeeList.jsp 페이징 처리
	public ArrayList<Employee> getEmployeeList(int rowPerPage, int currentPage){
		
		ArrayList<Employee> list = new ArrayList<Employee>();
		
		Connection conn = null;
		int beginRow = (currentPage-1)*rowPerPage;
		
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);			// 자동 커밋 막아줌
		
			EmployeeDao employeeDao = new EmployeeDao();
			list = employeeDao.selectEmployeeList(conn, rowPerPage, beginRow);
			
			// 디버깅
			System.out.println("list : " + list);
			
			if(list==null) {	
				throw new Exception();
			}
			
			conn.commit();
			
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return list;
	}	// end getEmployeeList
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	public int getlastPage(int rowPerPage) {
		
		Connection conn = null;
		
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
			
			EmployeeDao employeeDao = new EmployeeDao();
			rowPerPage = employeeDao.lastPage(conn);
	
			// 디버깅
			System.out.println("rowPerPage : " + rowPerPage);
			
	     if(rowPerPage == 0) {	// 실패시 예외처리로..
	    	 throw new Exception();
	     }
				conn.commit();
				
			} catch(Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
	      return rowPerPage;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	// active 변경
	public int modifyEmployeeActive(Employee employeeActive) {
		
		Connection conn = null;
		int active = 0;
		
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
		
			EmployeeDao employeeDao = new EmployeeDao();
			active = employeeDao.updateEmployeeActive(conn, employeeActive);
			
			// 디버깅
			System.out.println("active : " + active);
			
			if(active == 0) {	// 실패시 예외처리로..
				throw new Exception();
			}
			conn.commit();
			
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return active;
		
	}
	
}