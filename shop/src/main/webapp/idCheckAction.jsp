<%@page import="service.SignService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ckId = request.getParameter("ckId");
	SignService signService = new SignService();
	
	if (!signService.idCheck(ckId)) {
		// service의 리턴값이 false일때
		response.sendRedirect(request.getContextPath()+"/addCustomer.jsp?errorMsg=ID already exists");
		return;
	}else{
	//service의 리턴값이 true일때
	response.sendRedirect(request.getContextPath()+"/addCustomer.jsp?ckId="+ckId);}
%> 