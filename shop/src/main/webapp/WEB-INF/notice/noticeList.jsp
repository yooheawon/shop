<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h1>공지사항</h1>
   
   <div>
   	   	
      <a href="${pageContext.request.contextPath}/addNotice"> 공지사항 추가 </a>
   </div>
   
   <table border="1">
      <thead>
         <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회</th>
         </tr>
      </thead>
      <tbody>
         <c:forEach var="n" items="${list}">
         	<tr>
	            <td>${n.noticeNo}</td> <!-- b.getBoardNo() -->
	            <td>
	               <a href="${pageContext.request.contextPath}/noticeOne?noticeNo=${n.noticeNo}">
	                  ${n.noticeTitle}
	               </a>
	            </td>
	            <td>${n.noticeContent}</td>
	            <td>${n.updateDate}</td>
	            <td>${n.createDate}</td>
            </tr>
         </c:forEach>
      </tbody>
   </table>
   <div>
      <c:if test="${currentPage > 1}">
         <a href="${pageContext.request.contextPath}/noticeList.jsp?currentPage=${currentPage-1}">이전</a>
      </c:if>
      
      <c:if test="${currentPage < lastPage}">
         <a href="${pageContext.request.contextPath}/noticeList.jsp?currentPage=${currentPage+1}">다음</a>
      </c:if>
   </div>
</body>
</html>