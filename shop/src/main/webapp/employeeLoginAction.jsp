<%@page import="model.EmployeeDao"%>
<%@page import="vo.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
if(session.getAttribute("id") != null){
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errMsg=alreadyLogined");
	return;
}

request.setCharacterEncoding("utf-8");

String employeeId = request.getParameter("employeeId");
String employeePw = request.getParameter("employeePass");
// 디버깅
System.out.println(employeeId + " : 아이디");
System.out.println(employeePw + " : 비밀번호");

Employee employee = new Employee();
employee.setEmployeeId(employeeId);
employee.setEmployeePw(employeePw);
// 디버깅
System.out.println(employee+"커스터머");

EmployeeDao customersDao = new EmployeeDao();
Employee e = customersDao.login(employee);

// Redirect
if(e == null){
	System.out.println("로그인 실패");
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp?errMsg=loginFail");
	return;
}else{
	session.setAttribute("user", "스탭");
	session.setAttribute("Id", e.getEmployeeId());
	session.setAttribute("Name", e.getEmployeeName());
	System.out.println("로그인 성공");
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	
}
%>