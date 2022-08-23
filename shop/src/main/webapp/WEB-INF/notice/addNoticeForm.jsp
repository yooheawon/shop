<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>공지사항 등록 페이지</h1>
	<form action="addNotice" method="post">
	<table border="1">
		<tr>
			<td>제목</td>
			<td><input type ="text" id="noticeTitle" name="noticeTitle"></td>
		</tr> 
		<tr>
			<td>내용</td>
			<td><textarea rows="15" cols="50" id="noticeContent" name="noticeContent"></textarea></td>
		</tr>
	</table>
	<button type="submit">등록</button>
	</form>
</body>
</html>