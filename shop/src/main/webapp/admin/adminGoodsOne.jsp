<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "vo.*" %>    
<%@ page import = "service.*" %>
<%@ page import = "repository.*" %>
    
    
<%
	request.setCharacterEncoding("utf-8");
	int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
	System.out.println("goodsno : " + goodsNo);
	Goods goods = new Goods();
	GoodsService goodsService = new GoodsService();
	Map<String, Object> map = goodsService.getGoodsAndImgOne(goodsNo);
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 제품 상세보기 </h1>
<table border="1">
	<thead>
	<tr> 
		<td>goodsNo</td>
		<td>goodsImg</td>
		<td>goodsName</td>
		<td>goodsPrice</td>
		<td>updateDate</td>
		<td>createDate</td>
		<td>soldOut</td>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%=map.get("goodsNo")%></td>
		<td><img src="<%=request.getContextPath()%>/upload/<%=map.get("imgFileName")%>"></td>
		<td><%=map.get("goodsName")%></td>
		<td><%=map.get("goodPrice")%></td>
		<td><%=map.get("updateDate")%></td>
		<td><%=map.get("createDate")%></td>
		<td><%=map.get("soldOut")%></td>
	</tr>
	</tbody>	
</table>
	<div>
		<a href="<%= request.getContextPath() %>/admin/adminGoodsUpdateForm.jsp?goodsNo=<%=goodsNo%>">수정</a>
		<a href="<%= request.getContextPath() %>/admin/adminGoodsDelete.jsp">삭제</a>
	</div>


	
</body>
</html>