package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	
	// ┌ 퍼블릭이 아니라면 캡슐화 (private(게터,세터) / protected(같은패키지내상속받은자식) )
	public Connection getConnection() throws ClassNotFoundException, SQLException {
	// 이 디비로딩 부분을 따로 메서드로 뺀거야.	디비 url, dbuser, dbpw, sql등 수정 요잉하게 하려고
		
		Class.forName("org.mariadb.jdbc.Driver");
		String url = "jdbc:mariadb://localhost:3306/shop";
		String dbuser = "root";
		String dbpw = "1234";
		
		Connection conn = DriverManager.getConnection(url, dbuser, dbpw);
		
		return conn;
		// getConnection은 dao보다 상위레벨의 메서드라서 캡슐화 안하고 BoardDao에 안넣고 따로 DBUtil 클래스 만들어서 메서드 분리함				
	}	
	// 디비 유틸은 늘 메서드 하나라 스테틱으로 만들어도 괜찮대. BoardDao.java 필기 참고		
}
