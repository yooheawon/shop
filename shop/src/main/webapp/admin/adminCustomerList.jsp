<%@page import="vo.Customer"%>
<%@page import="java.util.List"%>
<%@page import="service.CustomerService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<%
  	if(session.getAttribute("Id") == null){
  		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=Not logged in");
  		return;
  	} else if(session.getAttribute("Id") != null && "customer".equals((String)session.getAttribute("user"))) {
  		// 관리자가 아닌경우 막기
  		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=No permission");
  	}
	
	// 페이징
	int currentPage = 1; // 현재페이지
	final int ROW_PER_PAGE = 10; // 한페이지에 표시될 양
	
	if(request.getParameter("currentPage") != null){
		// 받아오는 페이지가 있다면 현재페이지수에 담기
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	//메서드를 위한 객체 생성
	CustomerService customerService = new CustomerService();
	//마지막 페이지 구하기
	int lastPage = customerService.getLastPage(ROW_PER_PAGE);
	
	// 숫자 페이징
	int startPage = ((currentPage - 1 )/ROW_PER_PAGE) * ROW_PER_PAGE + 1;
	int endPage = startPage + ROW_PER_PAGE - 1;
	// endPage는 lastPage보다 크면 안도니다
	endPage = Math.min(endPage, lastPage ); // Math.min 두 값중 최소값을 찾아 대입
	
	// 리스트
	List<Customer> list = customerService.getCustomerList(ROW_PER_PAGE, currentPage);
	System.out.println("go");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="contanier">
		<h4> 회원 정보 </h4>
		<table border="1">
			<thead>
				<tr>
					<th>회원아이디</th>
					<th>회원비밀번호</th>
					<th>회원이름</th>
					<th>회원주소</th>
					<th>회원연락처</th>
					<th>수정날자</th>
					<th>생성날자</th>
				</tr>
			</thead>
			<tbody>
			<%
			System.out.println("go2");
				for(Customer c: list){
			%>
					<tr>
						<td >
							<a href="<%= request.getContextPath() %>/admin/adminOrdersListById.jsp?customerId=<%=c.getCustomerId() %>">
							<%= c.getCustomerId() %></a>
						</td>
					
						<td><%= c.getCustomerPw() %></td>
					
						<td><%=  c.getCustomerName()%></td>
					
						<td><%= c.getCustomerAddress() %></td>
					
						<td><%= c.getCustomerTelephone() %></td>
					
						<td><%= c.getUpdateDate() %></td>
					
						<td><%= c.getCreateDate() %></td>
						
						<td>
            				<a href="<%=request.getContextPath()%>/admin/adminUpdateCustomer.jsp?customerId=<%=c.getCustomerId()%>" class="btn">수정</a>
            			</td>
            			
            			<td>
            				<a href="<%=request.getContextPath()%>/admin/adminDeleteCustomer.jsp?customerId=<%=c.getCustomerId()%>" class="btn">삭제</a>
            			</td>
					</tr>
			<%
				}
			%>
			</tbody>
		</table>
		</div>
			 <!-- 숫자페이징 -->
		   		<%
	               	for(int i = startPage; i <= endPage; i++){
	               		if(i == currentPage){
		         %>
       			<li class="page-item disabled">
       				 <a class="page-link active rounded-0 mr-3 shadow-sm border-top-0 border-left-0" href="<%=request.getContextPath()%>/admin/adminCustomerList.jsp?currentPage=<%=i%>"><%=i%></a>
				<%
	               		}
					}
				%>
</body>
</html>