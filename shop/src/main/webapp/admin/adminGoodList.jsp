<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>    
<%@ page import="vo.*" %>
<%@ page import="service.*" %>
<%@ page import="repository.*" %>   
<%@ include file="../adminHeader.jsp"%>
<%	

	request.setCharacterEncoding("utf-8");

	if(session.getAttribute("Id") == null){
  		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=Not logged in");
  		return;
  	} else if(session.getAttribute("Id") != null && "customer".equals((String)session.getAttribute("user"))) {
  		// 관리자가 아닌경우 막기
  		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=No permission");
  	}



	int rowPerPage = 10; // 한페이지당 몇개?
	int currentPage = 1;// 어디서부터 시작?
	int lastPage = 0;
	
	if ( (request.getParameter("currentPage")) != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}

	
	GoodsService goodsService = new GoodsService();
	List <Goods> list= new ArrayList<Goods>();
	
	list = goodsService.selectGoodsListByPage(rowPerPage, currentPage);
	lastPage = goodsService.getLastPage(rowPerPage);
	System.out.print(lastPage);
	
	


%>    
    
    
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
<!-- <div> -->
<!-- 	<ul> 상단메뉴  -->
<%-- 		<li><a href="<%=request.getContextPath()%>/admin/employeeList.jsp" > 사원관리 </a></li> --%>
<%-- 		<li><a href="<%=request.getContextPath()%>/admin/adminGoodsList.jsp" > 상품관리 </a></li> --%>
<!-- 		<!--  └상품목록/등록/수정(품절)/삭제 --> 
<!-- 		<!--  					 └삭제는 근데 장바구니, 주문이 단 한번이라도 없는 경우에만 가능 -->
<%-- 		<li><a href="<%=request.getContextPath()%>/admin/adminCustomerList.jsp" > 고객관리 </a></li><!-- 주문목록/수정 --> --%>
<%-- 		<li><a href="<%=request.getContextPath()%>/admin/adminOrderList.jsp" > 주문관리 </a></li><!-- 고객목록/탈퇴/비밀번호수정(전달구현X) --> --%>
<%-- 		<li><a href="<%=request.getContextPath()%>/admin/adminNoticeList.jsp" > 공지관리 </a></li><!-- CRUD --> --%>
<!-- 	</ul> -->
<!-- </div> -->

<h1> 상품관리</h1>

<a href="<%=request.getContextPath()%>/admin/addGoodsForm.jsp"> 상품추가</a>
<div>
	<table border="1">
	<thead>
		<tr>
			<td> goodsNo </td>
			<td> goodsName </td>
			<td> goodsPrice </td>
			<td> updateDate </td>
			<td> createDate </td>
			<td> soldOut </td>
		</tr>
		</thead>
		<tbody>
			<%
			for (Goods g : list) {
			%>
			<tr>
				<td><%=g.getGoodsNo() %></td>
				<td>
					<a href="<%=request.getContextPath()%>/admin/​​​admingoodsImgOne.jsp?goodsNo=<%=g.getGoodsNo() %>">
					<%=g.getGoodsName() %></a>
				</td>
				<td><%=g.getGoodsPrice() %></td>
				<td><%=g.getUpdateDate() %></td>
				<td><%=g.getCreateDate() %></td>
				<td><%=g.getSoldOut() %></td>
			</tr>
		
	<% 
	}
	%>
	</tbody>
	</table>	
	
	
	
	<!--  페이징  -->
	<%
		if (currentPage > 1) {
		%>
		<a href="<%=request.getContextPath()%>/admin/adminGoodsList.jsp?currentPage=<%=currentPage-1%>" type="button" class="btn btn-dark">이전</a>
		<%
		}
		// 페이지 번호
		
		 	int pageCount = 10;
			int startPage = ((currentPage - 1) / pageCount) * pageCount + 1;
	    	int endPage = (((currentPage - 1) / pageCount) + 1) * pageCount;
	    	if (lastPage < endPage) { endPage = lastPage; }
	    	
		if (currentPage < lastPage) {
		%>
		<a href="<%=request.getContextPath()%>/admin/adminGoodsList.jsp?currentPage=<%=currentPage+1%>" type="button" class="btn btn-dark">다음</a>

		<%
		}
		%>
	
	
</body>
<%@ include file="/footer.jsp"%>
</html>