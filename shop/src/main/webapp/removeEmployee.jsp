<%@page import="vo.Employee"%>
<%@page import="repository.OutIdDao"%>
<%@page import="vo.OutId"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "service.*" %>

<% 
//get parameter
String employeeId = request.getParameter("employeeId");
String employeePass = request.getParameter("employeePass");


//variable
boolean result = false;

//파라미터 객체 세팅
Employee paramEmployee = new Employee();
paramEmployee.setEmployeeId(employeeId);
paramEmployee.setEmployeePw(employeePass);
//debugging
System.out.println("-------탈퇴커리------");

//쿼리 실행
EmployeeService employeeService = new EmployeeService();
result = employeeService.removeEmployee(paramEmployee);

//debugging
System.out.println("탈퇴return : " + result);

if(result == true){
   //로그아웃 처리
   session.invalidate();
   //debugging
   System.out.println(": 탈퇴");
   //redirection
   response.sendRedirect(request.getContextPath()+"/loginForm.jsp");
} else {
   //debugging
   System.out.println("탈퇴 여부 : 실패");
   //redirection
   response.sendRedirect(request.getContextPath()+"/index.jsp");
}


//debugging
System.out.println("탈퇴");
%>


