<%@page import="vo.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) session.getAttribute("employeeId");
	if (employee == null) {
    out.print("<script>alert('권한없음');location.href='/admin/adminGoodList.jsp'</script>");
	}	
%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div  class="container">
	<h3>상품등록</h3>
	<form action="<%= request.getContextPath() %>/admin/addGoodsAction.jsp" method ="post" enctype="multipart/form-data">
		<table border="1"> 
			<tr>
				<td> 제품명 </td>
				<td><input type = "text" name="goodsName"></td>
			</tr>
			<tr>
				<td> 가격</td>
				<td><input type = "text" name="goodsPrice"></td>
			</tr>
			<tr>
				<td> 품절여부</td>
				<td>
					<select name="soldOut" class="form-control" id="soldOut">
						<option value="defualt">-- 선택 --</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
			</tr>
			<tr>
				<td> 제품 이미지</td>
				<td><input type = "file" name="imgFile"></td>
			</tr>
		</table>
	<button type="submit">등록</button>		
	</form>
	</div>
</body>
</html>