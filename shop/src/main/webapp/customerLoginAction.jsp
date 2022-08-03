<%@page import="model.CustomerDao"%>
<%@page import="vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
if(session.getAttribute("id") != null){
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errMsg=alreadyLogined");
	return;
}

request.setCharacterEncoding("utf-8");

String customerId = request.getParameter("customerId");
String customerPw = request.getParameter("customerPass");
// 디버깅
System.out.println(customerId + " : 아이디");
System.out.println(customerPw + " : 비밀번호");

Customer customer = new Customer();
customer.setCustomerId(customerId);
customer.setCustomerPw(customerPw);
// 디버깅
System.out.println(customer+"커스터머");

CustomerDao customersDao = new CustomerDao();
Customer c = customersDao.login(customer);

// Redirect
if(c == null){
	System.out.println("로그인 실패");
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errMsg=loginFail");
	return;
}else{
	session.setAttribute("user", "손님");
	session.setAttribute("Id", c.getCustomerId());
	session.setAttribute("Name", c.getCustomerName());
	System.out.println("로그인 성공");
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	
}
%>