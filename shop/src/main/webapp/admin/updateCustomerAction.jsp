<%@page import="vo.Customer"%>
<%@page import="service.CustomerService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("Id")==null){
	response.sendRedirect(request.getContentType()+"../loginForm.jsp?errorMst = not logged in");
	return;
	}else if(session.getAttribute("Id")!=null && "customer".equals((String)session.getAttribute("user"))){
		// 관리자가 아닌경우 막기
	  		response.sendRedirect(request.getContextPath() + "../loginForm.jsp?errorMsg=No permission");
	}
	

	int Pw = 0;
	
	String customerId = request.getParameter("customerId");
	String customerPw = request.getParameter("customerPass");
	// 디버깅
	System.out.println("고객 비밀번호 변경1 : "+customerPw);
	Customer customer = new Customer();
	customer.setCustomerId(customerId);
	customer.setCustomerPw(customerPw);
	System.out.println("고객 비밀번호 변경2 : "+customerPw);
	CustomerService customerService = new CustomerService();
	Pw = customerService.modifyCustomerPw(customer);
	System.out.println("고객 비밀번호 변경3 : "+Pw);
	if(Pw == 1){		// 수정 성공
		System.out.println("고객 비밀번호 변경 final");
		response.sendRedirect(request.getContextPath() + "/admin/adminCustomerList.jsp");
	} else {			// 수정 실패
		System.out.println("고객 비밀번호 변경 실패!");
		response.sendRedirect(request.getContextPath() + "/employeeIndex.jsp");
	}
	
%>