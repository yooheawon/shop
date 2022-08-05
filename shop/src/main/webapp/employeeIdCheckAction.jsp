<%@page import="vo.Employee"%>
<%@page import="service.SignService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ckId = request.getParameter("ckId");
	//디버깅
	System.out.println("아이디 확인" + ckId);
	
 	SignService signService = new SignService();
 	
	//디버깅
 	
	if (!signService.idCheck(ckId)) {
		// 사용할수 없으면 에러 메세지
		response.sendRedirect(request.getContextPath()+"/addEmployee.jsp?errorMsg=ID already exists");
		return;
	}else{
	// 아이디를 사용할 수 있으면 전송될 아이디 확인
	System.out.println("아이디 확인" + ckId);
	response.sendRedirect(request.getContextPath()+"/addEmployee.jsp?ckId="+ckId);}
%>
