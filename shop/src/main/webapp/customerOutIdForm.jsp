<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%

	String deleteAction = "";
	if(session.getAttribute("user").equals("Employee")){
		deleteAction="/removeEmployee.jsp";
	} else if (session.getAttribute("user").equals("Customer")){
		deleteAction="/removeCustomer.jsp";
	}else{
		deleteAction="/removeCustomer.jsp";
	}

%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		
	<form method="post" action="<%=request.getContextPath()%>/removeEmployee.jsp">
			<fieldset>
				<legend>비밀번호를 입력하세요 </legend>
				<input type="hidden" name="Id" value="<%=session.getAttribute("Id") %>">				
				<input type="password" name="Pw">				
				<button type="submit">탈퇴</button>
			</fieldset>
	</form>
</body>
</html>