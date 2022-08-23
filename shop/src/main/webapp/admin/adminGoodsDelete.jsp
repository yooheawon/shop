<%@page import="service.GoodsService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("Id") == null){
		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=Not logged in");
		return;
	} else if(session.getAttribute("Id") != null && "customer".equals((String)session.getAttribute("user"))) {
		// 관리자가 아닌경우 막기
		response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errorMsg=No permission");
	}

	// goods no 값 받기
	int goodsNo = Integer.parseInt(request.getParameter("goodsNo"));
	// 디버깅
	System.out.println("delet Action goodsNO : " + goodsNo);
	
	GoodsService goodsService = new GoodsService();
	
	boolean deleteGoodsByAdmin = goodsService.removeGoodsByAdmin(goodsNo);
	// 디버깅 값 전달 확인   
	System.out.println("delet Action deleteGoodsByAdmin : " + deleteGoodsByAdmin);
	
	if(deleteGoodsByAdmin){
		//true
		response.sendRedirect(request.getContextPath() + "/adin");
	}
	
%>