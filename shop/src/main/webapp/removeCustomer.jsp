<%@page import="repository.OutIdDao"%>
<%@page import="vo.OutId"%>
<%@page import="vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "service.*" %>

<% 
//get parameter
String customerId = request.getParameter("customerId");
String customerPass = request.getParameter("customerPass");


//variable
boolean result = false;

//파라미터 객체 세팅
Customer paramCustomer = new Customer();
paramCustomer.setCustomerId(customerId);
paramCustomer.setCustomerPw(customerPass);
//debugging
System.out.println("-------탈퇴커리------");

//쿼리 실행
CustomerService customerService = new CustomerService();
result = customerService.removeCustomer(paramCustomer);

//debugging
System.out.println("탈퇴 서비스 return : " + result);

if(result == true){
   //로그아웃 처리
   session.invalidate();
   //debugging
   System.out.println("탈퇴 여부 : 성공");
   //redirection
   response.sendRedirect(request.getContextPath()+"/loginForm.jsp");
} else {
   //debugging
   System.out.println("탈퇴 여부 : 실패");
   //redirection
   response.sendRedirect(request.getContextPath()+"/index.jsp");
}


//debugging
System.out.println("-------end 탈퇴커리------");

%>


