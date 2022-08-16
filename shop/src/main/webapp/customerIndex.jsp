<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
	<table border="1">
	<tr>
		<td> 구분 </td>
		<td>
		<input type="text" name="" value="<%=session.getAttribute("user")%>"  readonly > <!-- customer  또는 employee 가 나와야함 -->
		</td>
	</tr>
	<tr>
		<td> 아이디 </td>
		<td>
		<br>
		<input type="text" name="" value="<%=session.getAttribute("Id") %>"  readonly > <!-- 로그인된 아이디 -->
		</td>
	</tr>
	<tr>
		<td> 이름 </td>
		<td>
		<br>
		<input type="text" name="" value="<%=session.getAttribute("Name") %>"  readonly > <!-- 로그인 이름 -->
		<!-- 세개 중 하나라도 안되면 리다이렉트 -->
		<br>
		</td>
	</tr>
	</table>
		<a href="<%=request.getContextPath()%>/logout.jsp">로그아웃</a>
		<a href="<%=request.getContextPath()%>/customerOutIdForm.jsp">회원탈퇴</a>
	</div>
</body>
<%@ include file="footer.jsp"%>
</html>
