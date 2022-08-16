<%@page import="service.EmployeeService"%>
<%@page import="vo.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 인코딩
	request.setCharacterEncoding("utf-8");
	
	// 변수 가져오기
	String employeeId = request.getParameter("employeeId");
	String employeePw = request.getParameter("employeePw");
	String employeeName = request.getParameter("employeeName");
	
	// 객체생성
	Employee paramEmployee = new Employee();
	paramEmployee.setEmployeeId(employeeId);
	paramEmployee.setEmployeePw(employeePw);
	paramEmployee.setEmployeeName(employeeName);
	
	// 디버깅
	System.out.println("Id= " + employeeId);
	System.out.println("pw = " + employeePw);
	System.out.println("name = " + employeeName);
	
	
	EmployeeService employeeService = new EmployeeService();
	employeeService.addEmployee(paramEmployee);
	
	
	response.sendRedirect(request.getContextPath()+"/loginForm.jsp");

%>