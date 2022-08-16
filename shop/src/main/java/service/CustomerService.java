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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import repository.CustomerDao;
import repository.DBUtil;
import repository.GoodsDao;
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
		/*
		 	// Customer에 담을 필요가 없다
		insertCustomer만 실행하면 끝인거야
		
		
		 INSERT DELETE UPDATE 등은 그냥 ??? 에 값 넣고 쿼리 돌리면
		 그 이후 그냥 DB에 들어가면 끝. 나에게 뭐 보여줄게 필요없어
		 근데 SELECT는 WHERE을 이후 뒤에 기준이 되는 값들 (ID나 PASSWORD나 ~NO같은)
		 이 일치하면 앞에 나머지 컬럼이 데이터값들을 '보여줌'
		 
		 그니까 값이 돌아옴. 리턴값이 있음. 그래서 그런 SELECT와 연결되는 DAO의 경우
		 리턴값이 있음. 필요하니까
		 
		 근데 나머지는 끽해야 int= 1,0 / void면 됨. 그냥 sql실행시키면 끝이라니까?
		 
		 
		 */
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public int getLastPage(int rowPerPage) {
		int lastPage = 0;
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
			
			CustomerDao customerDao = new CustomerDao();
			int totalCount = customerDao.getLastPage(conn);
			
			// 마지막페이지 구하기
			lastPage =(int)Math.ceil(totalCount/(double)rowPerPage);
			
			System.out.println(lastPage+"-lastPage");
			
			
			if (lastPage == 0) {
			throw new Exception();
			}
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return lastPage;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public List<Customer> getCustomerList(final int rowPerPage, final int currentPage){
		List<Customer> list = new ArrayList<Customer>();
		Connection conn = null;
		
		int beginRow = (currentPage-1)*rowPerPage;
		// 커넥션 부를 객체 생성
		DBUtil dbutil = new DBUtil();
		try {
			// getConnection 메서드 실행
			conn = dbutil.getConnection();
			// 오토커밋 제한
			conn.setAutoCommit(false);
			
			// customerDao 객체 생성
			CustomerDao customerDao = new CustomerDao();
			list = customerDao.selectCustomerList(conn, rowPerPage, beginRow);
			// 완료되면 커밋
			conn.commit();
			System.out.println("서비스");
		} catch (Exception e) {
			e.printStackTrace();
			// 실패 시 롤백
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}finally {
			//db 자원 해제
			if (conn != null) {
				try {
					conn.close();
				}catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
		return list;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	// 비밀번호 변경
	public int modifyCustomerPw(Customer customer) {
		int Pw = 0;
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			// 자동 커밋 방지
			conn.setAutoCommit(false);
			
			CustomerDao customerDao = new CustomerDao();
			Pw= customerDao.updateCustomerPw(conn, customer);
			
			//디버깅
			System.out.println("updateCustomerPw : " + Pw);
			// 실패시 예외 발생
			if (Pw == 0) {
				 throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return Pw;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public boolean removeCustomerByAdmin(String customerId)  {
		//conn 초기화
		Connection conn = null;
		CustomerDao customerDao = new CustomerDao();
		try {
			conn = new DBUtil().getConnection();
			int row = customerDao.deleteCustomerByAdmin(customerId, conn);
			//디버깅
			System.out.println("removeCustomerByAdmin : "+row);
			
			if (row == 1) {
				System.out.println("removeCustomerByAdmin 삭제 성공");
			}else {
				System.out.println("removeCustomerByAdmin 삭제 실패");
			}
		} catch (Exception e) {
			return false;
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return true;
	}
}