<%@page import="vo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	//페이징
	// 10개씩 보여지도록
	int rowPerPage=10;
	// 현재 페이지
	int currentPage=1;
	int lastPage=0;
	EmployeeService employeeService = new EmployeeService();
	ArrayList<Employee> list = new ArrayList<Employee>();
	list = employeeService.getEmployeeList(rowPerPage, currentPage);
	lastPage=employeeService.getlastPage(rowPerPage);
%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <meta charset="utf-8">
    <title>MultiShop - Online Shop Website Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <!-- Favicon -->
    <link href="../multishop/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">  

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../multishop/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../multishop/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../multishop/css/style.css" rel="stylesheet">
</head>
<body>
	<%-- 
	메뉴 - employeeHeader에 있음
	<div>
		<br> <a href="<%=request.getContextPath()%>/customerList.jsp">고객관리</a> 
			 <a href="<%=request.getContextPath()%>/employeeList.jsp">사원 관리</a>
			 <a href="<%=request.getContextPath()%>/adminGoodList.jsp">상품 관리</a> 
			 <a href="<%=request.getContextPath()%>/.jsp">주문 관리</a> 
		 	 <a href="<%=request.getContextPath()%>/.jsp">공지 관리(게시판)</a>
		 	 <a href="<%=request.getContextPath()%>/employeeIndex.jsp">로그인 확인 페이지</a>
	</div> 
	
	--%>

	<div class="container">

		<table border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>NAME</th>
					<th>UPDATE DATE</th>
					<th>CREATE DATE</th>
					<th>ACTIVE</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Employee e : list) {
				%>
				<tr>
					<td><%=e.getEmployeeId()%></td>
					<td><%=e.getEmployeeName()%></td>
					<td><%=e.getUpdateDate()%></td>
					<td><%=e.getCreateDate()%></td>
					<td><%=e.getActive()%></td>
					<td>
						<form action="<%=request.getContextPath()%>/admin/updateAdminAction.jsp" method="post">
							<input type="hidden" name="employeeId" value="<%=e.getEmployeeId()%>"> <select name="active">
								<%
								if (e.getActive().equals("N")) {
								%>
								<option>Y</option>
								<option selected="selected">N</option>
								<%
								} else {
								%>
								<option selected="selected">Y</option>
								<option>N</option>
								<%
								}
								%>
							</select>
						<button type="submit">수정하기</button>
					</form>
					</td>
					<%
					}
					%>
				</tr>
			</tbody>
		</table>

		<!-- 페이징 -->
		<%
		if (currentPage > 1) {
		%>
		<a
			href="<%=request.getContextPath()%>/employeeList.jsp?currentPage=<%=currentPage - 1%>">이전</a>
		<%
		}

		if (currentPage < lastPage) {
		%>
		<a
			href="<%=request.getContextPath()%>/employeeList.jsp?currentPage=<%=currentPage + 1%>">다음</a>

		<%
		}
		%>

	</div>
</body>
</html>
<%@ include file="/footer.jsp"%>
