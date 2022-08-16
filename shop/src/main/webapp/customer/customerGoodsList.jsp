<%@page import="service.GoodsService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "service.CustomerService"%>
<%@ page import = "java.util.*" %>
<%
   // Controller : java class <- Serlvet 
   int rowPerPage = 20;
   if(request.getParameter("rowPerPage") != null) {
      rowPerPage = Integer.parseInt(request.getParameter("rowPerPage"));
   }
   
   int currentPage = 1;
   if(request.getParameter("currentPage") != null) {
      currentPage = Integer.parseInt(request.getParameter("currentPage"));
   }
   
   GoodsService goodsService = new GoodsService();
   
   // 페이징
   //last page
   int lastPage = goodsService.getLastPage(rowPerPage);
   System.out.println("lastPage : "+lastPage);
   // startPage , endPage
   // 숫자 페이징
   int startPage = ((currentPage - 1) / rowPerPage) * rowPerPage +1; // 페이지 시작
   int endPage = startPage + rowPerPage -1; // 페이지 끝
   // endPage와 lastPage를 비교해서 작은 것을 endPage로
   endPage = Math.min(endPage, lastPage);
   System.out.println("endPage : "+endPage);
   
   // list
   List<Map<String, Object>> list = goodsService.getCustomerGoodsListByPage(rowPerPage, currentPage);
   System.out.println("list : "+list);
%>

<!-- 분리하면 servlet / 중계기술(forword - 겹친다, 덮어쓴다(request - 세션과 같이 저장할 수 있다 차이점 이 jsp가 끝나면 저장된것이 사라진다 또 forword 하지 않는 이상, response)) / jsp -->
<!-- 태그 -->
<!-- for / if : java -- 대체기술 : 커스텀태그(JSTL & EL) -- jsp(전용뷰가 아님 서블릿이랑 같은 것) 사용  -->
<!-- View : 태그 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <!--  for / if 대체기술 : 커스텀태그(JSTL & EL) JSP -->
<div class="row">
           <div class="col-md-8">
               <ul class="list-inline shop-top-menu pb-3 pt-1">
                   <li class="list-inline-item">
                       <a class="h4 text-dark text-decoration-none mr-3" href="#">인기순</a>
                   </li>
                   <li class="list-inline-item">
                       <a class="h4 text-dark text-decoration-none mr-3" href="#">판매량순</a>
                   </li>
                   <li class="list-inline-item">
                       <a class="h4 text-dark text-decoration-none" href="#">낮은가격순</a>
                   </li>
                   <li class="list-inline-item">
                       <a class="h4 text-dark text-decoration-none" href="#">높은가격순</a>
                   </li>
                   <li class="list-inline-item">
                       <a class="h4 text-dark text-decoration-none" href="#">최신순</a>
                   </li>
                   <li class="list-inline-item">
                   	<form action="<%=request.getContextPath()%>/theme/updateRowPerPage.jsp" method="post">
                  	<select name="rowPerPage">
                  	<%
                  		if(rowPerPage == 20){
                  	%>
                  		<option value="20">20개씩 보기</option>
                  		<option value="40">40개씩 보기</option>
                  	<%
                  		} else {
                  	%> 	
                  		<option value="20">20개씩 보기</option>
                  		<option value="40" selected="selected">40개씩 보기</option>
                  	<%
                  		}
                  	%>
                  	</select>
                  	<button type="submit" class="btn">변경</button>
                 	</form>
                 </li>
             </ul>
         </div>
    </div>
   <table border="1">
      <tr>
         <%
            int i = 1;
            for(Map<String, Object> m : list) {
         %>
               <td>
                  <div>
                  	<a href="">
                  		<img src="<%=request.getContextPath()%>../upload/<%=m.get("filename")%>" width="200" height="200"></a>
                  </div>
                  <div><%=m.get("goodsName")%></div>
                  <div><%=m.get("goodsPrice")%></div>
                  <!-- 리뷰 개수 -->
               </td>
         <%
               if(i%4==0) {
         %>
                  </tr><tr>
         <%         
               }
               i++;
            }
            
            int tdCnt = 4 - (list.size() % 4);
            if(tdCnt == 4) {
               tdCnt = 0;
            }
            
            for(int j=0; j<tdCnt; j++) {
         %>
               <td>&nbsp;</td>
         <%      
            }
         %>
      </tr>
   </table>
   <!--  페이징 + 상품검색 -->
</body>
</html>