package service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import repository.DBUtil;
import repository.OrdersDao;

public class OrdersService {
	public List<Map<String, Object>> selectOrdersListByPage(int goodsNo){
		System.out.println("selectOrdersListByPage goodNo : "+goodsNo);
		Map<String, Object> map = null;
		Connection conn = null;
		try {
			conn = new DBUtil().getConnection();
			//디버깅
			System.out.println("연동 성공");
			conn.setAutoCommit(false); // 자동 커밋 방지
			map = new HashMap<>();
			OrdersDao ordersDao = new OrdersDao();	
			map = ordersDao.selectOrdersOne(conn, goodsNo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	
	}
}
