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
	<form action="addNotice">
	<table border="1">
		<tr>
			<td>제목</td>
			<td><input type ="text" id="noticeTitle" name="noticeTitle"></td>
		</tr> 
		<tr>
			<td>내용</td>
			<td><textarea rows="50" cols="20" id="noticeContent" name="noticeContent"></textarea></td>
		</tr>
	</table>
	</form>
	<button type="submit" value="btn">등록</button>
</body>
</html>