<%@page import="java.util.ArrayList"%>
<%@page import="service.CustomerService"%>
<%@page import="vo.Customer"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
if(session.getAttribute("Id")==null){
	response.sendRedirect(request.getContentType()+"../loginForm.jsp?errorMst = not logged in");
return;
}else if(session.getAttribute("Id")!=null && "customer".equals((String)session.getAttribute("user"))){
	// 관리자가 아닌경우 막기
  		response.sendRedirect(request.getContextPath() + "../loginForm.jsp?errorMsg=No permission");
}
	
	
String customerId = request.getParameter("customerId");
		

	// 디버깅
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<div class = "container">
		<h4>고객 정보 수정</h4>
		<form action="<%= request.getContextPath()%>/admin/updateCustomerAction.jsp" method="post" id="form">
			<table border="1">
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="customerId" value=<%= customerId %> readonly = "readonly">
					</td>
				</tr>
				<tr>
           			<th>비밀번호 변경</th>
           			<td>
           				<input type="text" name="customerPass" id="customerPass" class="form-control"> 
           			</td>
           		</tr>
           	</table>
           	<button type="submit" class="btn">수정</button>
		</form>
	</div>
</body>
<script>
  	$('#btn').click(function(){
  		if($('#customerPass').val().length < 4){
  			alert('비밀번호를 4자 이상 기입해주세요');
  		} else {
  			$('#form').submit();
  		}
  	});
  </script>
</html>