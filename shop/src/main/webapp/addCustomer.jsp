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
	<!-- id check -->
	<form action="<%= request.getContextPath() %>/idCheckAction.jsp" mehtod="post">
	<h3>고객회원가입</h3>
		<div>
			ID 체크
			<input type="text" name="ckId">
			<button type="submit" value="중복확인" >아이디 중복 검사</button>
		</div>
	</form>
	<%
		String ckId="";
		if(request.getParameter("ckId") != null){
			ckId=request.getParameter("ckId");
		}
	%>
	<!-- 고객 가입 form -->
	<!-- 
		idAction 에서 true가 나오면 다시 이 페이지로 돌아와 가입 진행
		idAction을 무조거 거쳐야 갑입 진행가능
	 -->
	<form action="<%= request.getContextPath() %>/addCustomerAction.jsp" mehtod="post">
		<table border="1">
			<tr>
				<td>customerId</td>
				<td>
					<input type="text" name="customerId" id="customerId" value="<%= ckId%>" readonly="readonly" >
				</td>
			</tr>
			<tr>
				<td>customerPw</td>
				<td> <input type="passsword" name="customerPw" id="customerPw" > </td>
			</tr>
			<tr>
				<td>customerName</td>
				<td> <input type="text" name="customerName" id="customerName" >  </td>
			</tr>
			<tr>
				<td>customerAddress</td>
				<td> <input type="text" name="customerAddress" id="customerAddress" >  </td>
			</tr>
			<tr>
				<td>customerTelephone</td>
				<td> <input type="text" name="customerTelephone" id="customerTelephone" >  </td>
			</tr>
		</table>
		<button type="submit">회원가입</button>	
	</form>
</body>
<%@ include file="footer.jsp"%>
</html>