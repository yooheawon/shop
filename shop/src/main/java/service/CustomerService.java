package service;
// service레이어드를 만든 이유 -- 트렌젝션 처리 뿐만 아니라 컨트롤러 및 DAO가 해서는 안되는일
/*
 *  DAO에게 넘겨주기 전에 가공하는일
 *  DAP에게 받은 값을 컨트롤러에 넘겨주기 전에 가공하는 일
 *  커넥션을 가져온다는 이야기와 커넥션에 연결된 쿼리를 넣는것은 다른 것
 *  이것을 도와주는게 service
 *  복수의 메서드 및 쿼리를 하기 위해서 service사용
 */
import java.sql.Connection;
import java.sql.SQLException;

import repository.CustomerDao;
import repository.DBUtil;
import repository.OutIdDao;
import vo.Customer;

public class CustomerService {
	///////////////////////////////////////////////////////////
	// 회원탈퇴
	public boolean removeCustomer(Customer paramCustomer) {
		Connection conn=null;
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false); //  executeUpdate()실행시 자동 COMMIT실행 안되게 막음 
					
					
			// 두개의 Dao 동일한 conn이 들어가도록
			// CustomerDao 호출 - 삭제
			CustomerDao customerDao = new CustomerDao();
			if(customerDao.deleteCustomer(conn, paramCustomer)!=1) { // 1)
				throw new Exception();
			};
			// outid 테이블에 삭제한 아이디 insert
			OutIdDao OutIdDao = new OutIdDao();
			if(OutIdDao.insertOutId(conn, paramCustomer.getCustomerId())!=1) {
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
	
	///////////////////////////////////////////////////////////
	// 회원가입
	public boolean insertCustomer(Customer paramCustomer) {
		Connection conn=null;
		
		try {
			conn = new DBUtil().getConnection();
			// 자동 커밋 방지
			conn.setAutoCommit(false);
			
			CustomerDao customerDao= new CustomerDao();
			int row = customerDao.insertCustomer(conn, paramCustomer);
			
		    if (row !=0) {
				System.out.println("cus page 실패");
			} else {
				System.out.println("cus page 성공");
			}	
		    // 이상 없으면 커밋
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace(); // console에 예외메세지 출력
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}
	///////////////////////////////////////////////////////////
	// 로그인
	public Customer getCustomer(Customer paramCustomer) {
		Connection conn=null;
		Customer selectCustomerByIdAndPw = null;
		try {
			conn=new DBUtil().getConnection();
			conn.setAutoCommit(false); // 자동 COMMIT방지
			
			// CustomerDao 호출 - 로그인
			CustomerDao customerDao = new CustomerDao();
			selectCustomerByIdAndPw = customerDao.selectCustomerByIdAndPw(conn, paramCustomer);
			if (selectCustomerByIdAndPw == null) {
				System.out.println("customerServicepage 로그인 실패");
			}else {
				System.out.println("customerServicepage 로그인 성공");
				throw new Exception();
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
			return selectCustomerByIdAndPw; // 탈퇴 실패
		}finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return selectCustomerByIdAndPw; // 탈퇴 실패
	}
}