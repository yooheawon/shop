<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
	 <a href="${pageContext.request.contextPath}/updateNotice"> 공지사항 수정 </a>
     <a href="${pageContext.request.contextPath}/deleteNotice"> 공지사항 삭제 </a>
	</div>
	<h1>공지사항 수정</h1>
	<form action="" method="post">
		<table>
			<thead>
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>내용</td>
					<td>수정일시</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="n" items="${list}">
					
				</c:forEach>
			</tbody>	
		
		</table>
	
	
	
	
	</form>
</body>
</html>