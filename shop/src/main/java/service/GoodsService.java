package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import repository.DBUtil;
import repository.GoodsDao;
import repository.GoodsImgDao;
import vo.Goods;
import vo.GoodsImg;

public class GoodsService {
/*
   1. 특정 맴버를 인터페이스로 연결 
   2. 코드 자체에 유연성이 생김	
   	-- 직접 명령하는 것이 아니라 타이틀, 별명등을 이용하여 명령
   	-- 의존성이 떨어지기 때문에 유지관리에 용이 but 만들기 어려움 -- 더 많은 비용을 지불해야하는 개발자가 필요
   
 	goodsDao 호출
 */
	private GoodsDao goodsDao; 
	private DBUtil dbUtil;
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 삭제
	public int remove(Goods goods, GoodsImg goodsImg) {
		int row=0;
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false); // 자동 커밋 방지
			GoodsDao goodsDao = new GoodsDao();
			GoodsImgDao goodsImgDao = new GoodsImgDao();
			row = goodsDao.deleteGoods(conn, goods);
			// 디버깅
			System.out.println("goodsservice 삭제 row"+ row);
			if (row != 0) {
				goodsImg.setImggoodsNo(row);
				this.goodsImgDao = new GoodsImgDao();
			}
			conn.commit();
		} catch (Exception e) {
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
	      return row;
	   }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public GoodsService() {
		super();
		this.goodsDao = new GoodsDao();
		this.dbUtil = new DBUtil();
	}
	
	public List<Goods> selectGoodsListByPage(int rowPerPage, int currentPage) throws SQLException {
		List<Goods> list = new ArrayList<Goods>();
		Connection conn=null;
		int beginRow=(currentPage-1)*rowPerPage;
		
		try {
			conn = dbUtil.getConnection();
			list = goodsDao.selectGoodsListByPage(conn, rowPerPage, beginRow);
			
			// 디버깅
			System.out.println("list : "+list);
			
			if(list == null) {
				throw new Exception();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();	
			}
		}
		// 디버깅
		System.out.println("서비스 : O");
		
		return list;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public int getLastPage(int rowPerPage) {
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			conn.setAutoCommit(false);
			
			GoodsDao goodsDao = new GoodsDao();
			rowPerPage = goodsDao.getLastPage(conn);
					
			System.out.println(rowPerPage+"-roperpage");
			
			if (rowPerPage == 0) {
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
		return rowPerPage;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 
// 상품추가
	
	// 트랜잭션 + action이나 dao가 해서는 안되는 일
	// 서비스가 하는 일: 트랜잭션 처리 ★★★★★★★ + action, Dao가 해서는 안되는 일 처리
	// 굿즈디에이오를 호출 *원래 서비스는 그런거양
	// 특정멤버를 참조할 때도 이렇게 인티페이스를 참조하면 디커플링(Decoupling) >>> 유연해지므로 유지보수 편해
	// 인터페이스는 어디에 쓰이고 왜 쓰이는지가 중요하댕 또한 편한데 만드는게 힘들대	
	// 디자인패턴을(=설계패턴) 따라하는데 이게 어려워가지고 프레임워크가 나타났대. 스프링프레임워크 쓰면 쟤가 자동화로 알아서 만들어주는듯
	// 스프링프레임워크는 MVC프레임워크고 얘들이 필수요소들은 자동으로 만들어쥐만 service는 안만들어준대 이건 ㄹㅇ 인간개발자의 영역. 자동화로 해결 ㄴㄴ래
	
	// 내 손은 내가 갖고있음. 밥먹겠다고 갑자기 세포분열? 이딴거 없ㅇ음 >>> field변수
	// 글쓰겠다고 손에 늘 필기도구 장착하고다님? 그럴리가? 그냥 해당장소가서 필요할때 잡으면 그만 >>> local변수
	
	// DAO안에 5개의 메서드가 있음. 이 5개 메서드들은 다같은 DAO를 가져오겠지 필요할때마다 객체를 마만들겠징

	
	// 두개의 인서트를 하나의 트랜젝션으로해야한대
	   private GoodsImgDao goodsImgDao;
	   
	   public int addGoods(Goods goods, GoodsImg goodsImg) {
		  int goodsNo=0;
	      Connection conn = null;
	      try {
	         conn = new DBUtil().getConnection();
	         conn.setAutoCommit(false);
	         goodsDao = new GoodsDao();
	         goodsImgDao = new GoodsImgDao();
	         goodsNo = goodsDao.insertGoods(conn, goods); // goodsNo가 AI로 자동생성되어 DB입력
	         if(goodsNo != 0) { // goods insert에 성공한 경우
	            goodsImg.setImggoodsNo(goodsNo);
	            this.goodsImgDao = new GoodsImgDao(); //  모든 메소드에서 쓰는 dao가 아니므로
	            
	            if (goodsImgDao.insertGoodsImg(conn, goodsImg)==0) {
					goodsNo=0; 
					System.out.println("goodsImgDao.insertGoods: " + goodsNo);
					
					throw new Exception();
					// 이미지 입력 실패 시 catch 절로 이동하기 위해 강제 예외처리하여  rollBack
				}
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
	      return goodsNo;
	   }
	   
	   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	   // 상세페이지
	public Map<String, Object> getGoodsAndImgOne(int goodsNo){
		// 전송받은 값 디버깅
		System.out.println(goodsNo + "<--goodsNo - getGoodsAndImgOne");
		// 리턴할 변수 선언
		Map<String, Object> map = null;	
		Connection conn = null;
		
		try {
			conn = this.dbUtil.getConnection();
			//디버깅
			System.out.println(" db연동성공 : "+ conn);
			// goods의 넘버와 goodImg 넘버 같이 사용할 수 있도록
			map = new GoodsDao().selectGoodsAndImgOne(conn, goodsNo);
			if(map == null) {
				System.out.println("goodsOne오류 이미지 불가");
				// 강제 오류발생으로 이미지 없이 등록되는것 방지
				throw new Exception();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("MAP : "+ map);
		return map;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public List<Map<String,Object>> getCustomerGoodsListByPage(final int rowPerPage, final int currentPage){
		List<Map<String, Object>> list = null;
		// 파라미터 디버깅
		System.out.println("getCustomerGoodsListByPage 파라미터 디버깅 : " + "rowPerPage > " + rowPerPage + ",currentPage >"
				+ currentPage );
		
		//beginRow
		int beginRow = (currentPage - 1)*rowPerPage;
		// db자원 만들기
		Connection conn = null;
		//List객체 생성 - 상품리스트 받아오기
		
		try {
			// db driver연동
			conn = new DBUtil().getConnection();
			// 디버깅
			System.out.println("getCustomerGoodsListByPage드라이버 연동 성공");
			// 상품리스트 받아오기
			GoodsDao goodsDao = new GoodsDao();
			list = goodsDao.selectCustomerGoodsListByPage(conn, rowPerPage, beginRow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// db자원 해제
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
}