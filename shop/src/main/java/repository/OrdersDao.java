package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mariadb.jdbc.internal.com.read.dao.Results;

import vo.Orders;

public class OrdersDao {
	// 5-1 전체 주문 목록(관리자)
		// orders 대신 Map사용
	public List<Map<String, Object>> selectOrdersList(Connection conn, int rowPerPage, int beginRow) throws SQLException{
		// list만 사용하면 join문을 제대로 출력 할수 없으므로 list안에 map를 넣어 값을 다 받아와야함 ?
		List<Map<String, Object>> list = new ArrayList<>(); // 0을 직접적 리턴이 아니라 0을 가지고 있는 리스트
		// 
		// 다형성 -- 연결성을 떨어트리기 위해 ArrayList / 제일 연결성을 안두는 것은 언급 안하는것?
		/*
		 	무슨상품을 사는지 알기 위해선 join을 사용해야함 
		 	SELECT o.order_no , o.goods_no , o.customer_id 
		 			, o.order_quantity , o.order_price , o.order_addr 
		 			, o.order_state , o.update_date , o.create_date , g.goods_name  
		 			FROM orders o INNER JOIN goods g 
		 			ON o.goods_no = g.goods_no 
		 			ORDER BY o.create_date DESC LIMIT ?,?
		 */
		String sql ="SELECT o.order_no , o.goods_no , o.customer_id \r\n"
				+ "		 			, o.order_quantity , o.order_price , o.order_addr \r\n"
				+ "		 			, o.order_state , o.update_date , o.create_date , g.goods_name  \r\n"
				+ "		 			FROM orders o INNER JOIN goods g \r\n"
				+ "		 			ON o.goods_no = g.goods_no \r\n"
				+ "		 			ORDER BY o.create_date DESC LIMIT ?,?";		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			
			rs=stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("orderNo", rs.getInt("orderNo"));
				map.put("goodsNo", rs.getString("goodsNo"));
				map.put("customerId", rs.getString("customerId"));
				map.put("orderQuantity", rs.getInt("orderNo"));
				map.put("ordersPricec", rs.getInt("orderNo"));
				map.put("orderAddr",  rs.getString("orderAddr"));
				map.put("orderState",  rs.getString("orderState"));
				map.put("updateDate",  rs.getString("updateDate"));
				map.put("createDate",  rs.getString("createDate"));
				map.put("goodsName",  rs.getString("goodsName"));
				
				list.add(map);
				// 디버깅
				System.out.println("map.toString : "+map.toString());
			}
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		}
		return list;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // 5-2) 주문상세보기
   public Map<String, Object> selectOrdersOne(Connection conn, int ordersNo) {
      Map<String, Object> map = null;
      /*
       SELECT o.orders_no , g.goods_name , c.customer_name
		FROM orders o INNERE JOIN goods g
		ON o.goods_no = g.goods_no
		INNER JOIN customer c
		ON o.customer_id = c.customer_id
		WHERE customer_id = sunsu6911;
       
       SELECT
          o. ,
          g. ,
          c. ,
       FROM orders o INNERE JOIN goods g
       ON o.goods_no = g.goods_no
                            INNER JOIN customer c
                            ON o.customer_id = c.customer_id
       WHERE o.orders_no = ?
       */
      return map;
   }

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2-1 고객 한명의 주문 목록(관리자, 고객)
	public List<Map<String, Object>> selectOrdersListByCustomer(Connection conn, String customerId, int rowPerPage, int beginRow){
		List<Map<String, Object>> list = new ArrayList<>(); // 0을 직접적 리턴이 아니라 0을 가지고 있는 리스트
		// 다형성 -- 연결성을 떨어트리기 위해 ArrayList / 제일 연결성을 안두는 것은 언급 안하는것?
		/*
		 	무슨상품을 사는지 알기 위해선 join을 사용해야함 
		 	SELECT
		 	 	o. , .....
		 		g. , .....
		 	FROM orders o INNER JOIN goods g
		 	ON o.goods_no = g.goods_no
		 	WHERE cuseomer_id?
		 	ORDER BY create_date DESC
		 	LIMIT ?,?
		 */
		return list;
	}
}
