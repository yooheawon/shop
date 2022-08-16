<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="adminHeader.jsp"%>
<%
	// 세션 유효성 검사
%>
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
	<ul>
		<li><a href="<%=request.getContextPath()%>/logout.jsp">로그아웃</a></li>
		<li><a href="<%=request.getContextPath()%>/employeeOutIdForm.jsp">회원탈퇴</a></li>
		<li><a href="<%=request.getContextPath()%>/admin/adminCustomerList.jsp">고객관리</a></li>
			<!-- 고객목록/강제탈퇴/비밀번호수정(문자, 메일등의 방법이 있지만 구현x API가 필요한 부분) -->
		<li><a href="<%=request.getContextPath()%>/admin/adminList.jsp">사원관리</a></li>
		<li><a href="<%=request.getContextPath()%>/admin/adminGoodList.jsp">상품관리</a></li>
			<!-- 상품목록/등록/수정(품절)/삭제(장바구니, 주문이 없는 경우) -->
		<li><a href="<%=request.getContextPath()%>/admin/adminOrder.jsp">주문관리</a></li>
			<!-- 주문목록/수정 -->
		<li><a href="<%=request.getContextPath()%>/admin/adminNoticeList.jsp">공지관리</a></li>
			<!-- 공지 CRUD 작성 -->
	</ul>
	</div>
</body>
<%@ include file="/footer.jsp"%>
</html>
