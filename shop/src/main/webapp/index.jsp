<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%=session.getAttribute("user")%> <!-- customer  또는 employee 가 나와야함 -->
	<br>
	<%=session.getAttribute("Id") %> <!-- 로그인된 아이디 -->
	<br>
	<%=session.getAttribute("Name") %> <!-- 로그인 이름 -->
	<!-- 세개 중 하나라도 안되면 리다이렉트 -->
	<br>
	<a href="<%=request.getContextPath()%>/logout.jsp">로그아웃</a>
</body>
</html>
