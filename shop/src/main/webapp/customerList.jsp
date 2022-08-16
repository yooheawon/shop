<%@page import="vo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 사원 중 액티브가 y인 사람만 접근 가능
	if (!(session.getAttribute("user").equals("Employee") && session.getAttribute("active").equals("Y"))) {
		response.sendRedirect(request.getContextPath() + "/adminIndex.jsp");
	}

	//페이징
	// 10개씩 보여지도록
	int rowPerPage=10;
	// 현재 페이지
	int currentPage=1;
	int lastPage=0;
	EmployeeService employeeService = new EmployeeService();
	ArrayList<Employee> list = new ArrayList<Employee>();
	list = employeeService.getEmployeeList(rowPerPage, currentPage); // List
	lastPage=employeeService.getlastPage(rowPerPage);	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%-- 
	
	메뉴라인 헤더에 있음
	<div>
		<br> <a href="<%=request.getContextPath()%>/customerList.jsp">고객관리</a> 
			 <a href="<%=request.getContextPath()%>/employeeList.jsp">사원 관리</a>
			 <a href="<%=request.getContextPath()%>/.jsp">상품 관리</a> 
			 <a href="<%=request.getContextPath()%>/.jsp">주문 관리</a> 
		 	 <a href="<%=request.getContextPath()%>/.jsp">공지 관리(게시판)</a>
	</div>
 --%>
	<div class = "contanier">

		<table>
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
						<form action="<%=request.getContextPath()%>/updateEmployeeAction.jsp" method="post">
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
</body>
</html>