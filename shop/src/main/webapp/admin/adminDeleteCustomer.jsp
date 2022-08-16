<%@page import="service.CustomerService"%>
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
	

	// id값 받아오기
	String customerId = request.getParameter("customerId");
	// 디버깅
	System.out.println("삭제할 customerId : "+customerId);
	
	CustomerService customerService = new CustomerService();
	
	// deleteConplete가 성공적이면  true값을 가지고 있음
	boolean deleteByAdmin = customerService.removeCustomerByAdmin(customerId);
	System.out.println("deleteByAdmin 값 확인 : "+deleteByAdmin);
	
	// true
	if(deleteByAdmin){
		System.out.println("deleteByAdmin 값  삭제성공 : "+deleteByAdmin );
		response.sendRedirect(request.getContextPath()+"/admin/adminCustomerList.jsp");
	}else{
	// false	
		System.out.println("deleteByAdmin 값  삭제실패 : "+deleteByAdmin );
		response.sendRedirect(request.getContextPath()+"/admin/adminCustomerList.jsp");
	}
			
	
%>