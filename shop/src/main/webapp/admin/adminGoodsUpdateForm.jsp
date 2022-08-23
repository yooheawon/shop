<%@page import="java.util.Map"%>
<%@page import="service.GoodsService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 로그인 안되어 있거나 user가 Employee가 아니면 로그인 폼으로...
	if(session.getAttribute("Id") == null || (!(session.getAttribute("user").equals("employee"))) ){
		response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
		return;
	} 
	// 인코딩
	request.setCharacterEncoding("utf-8");
	// 원래 저장되어있는 값 불러오기
	int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
	String goodsName = request.getParameter("goodsName");
	String goodsPrice = request.getParameter("goodsPrice");
	String imgFileName = request.getParameter("imgFileName");
	String soldOut = request.getParameter("soldOut");
	GoodsService goodsService = new GoodsService();
	Map<String,Object> map = goodsService.getGoodsAndImgOne(goodsNo);
	
	// 디버깅
	System.out.println("업데이트 폼 : " + map);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<h2>상품 수정</h2>
			
	<form action="<%=request.getContextPath()%>/admin/adminGoodsUpdateAction.jsp?goodsNo=<%=map.get("goodsNo")%>" 
		method="post" enctype="multipart/form-data" id="updateGoods">
	<table border="1">
		<tr>
			<td>상품이름</td>
			<td><input type = "text" name = "goodsName" id="goodsName" value=<%=map.get("goodsName") %>></td>
			
		</tr>
		<tr>
			<td>상품 가격</td>
			<td><input type = "text" name = "goodsPrice" id="goodsPrice" value=<%=map.get("goodPrice")%>></td>
		</tr>
		<tr>
			<td>상품 이미지</td>
			<td><input type = "file" name = "imgFileName" id="imgFileName" value=<%=map.get("imgFileName")%>></td>
		</tr>
		<tr>
			<td>품절여부</td>
			<td>
				<select name="soldOut">
					<%
						if(map.get("soldOut").equals("Y")) {
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
				<%
					if(request.getParameter("errorMsg") != null){
				%>
					<span style="color:red;"><%=request.getParameter("errorMsg")%></span>	
				<%
					}
				
				%>
			</td>
		</tr>
		<%
			if(session.getAttribute("user").equals("Employee") && session.getAttribute("active").equals("Y") ){ 
		%>
		<%
			}
		%>
		
	</table>
			<button type="submit" class="btn btn-info" id="updateBtn">수정하기</button>
	</form>
</body>
<script>	// 유효성 검사
	$('#updateBtn').click(function(){			
		if($('#name').val().length < 1){
			alert('이름을 입력해주세요');
		} else if($('#price').val().length < 1){
			alert('가격을 입력해주세요');
		} else if($('#file').val().length < 1){
			alert('파일을 첨부해주세요');
		} else if($('#file').val().length < 1){
				alert('파일을 첨부해주세요');
		} else{
			updateGoods.submit();
		}
	});
</script>
</html>