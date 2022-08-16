<%@page import="service.EmployeeService"%>
<%@page import="vo.Employee"%>
<%@page import="service.EmployeeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	int row = 0;
	String employeeId = request.getParameter("employeeId");
	String active = request.getParameter("active");
	
	// 디버깅
	System.out.println("updateEmployeeId : " + employeeId);
	System.out.println("updateActive : " + active);
	
	Employee employee = new Employee();
	employee.setEmployeeId(employeeId);
	employee.setActive(active);
	
	EmployeeService employeeService = new EmployeeService();
	row = employeeService.modifyEmployeeActive(employee);
	
	if(row == 1){		// 수정 성공
		System.out.println("관리자 권한 수정 성공!");
		response.sendRedirect(request.getContextPath() + "/admin/adminList.jsp");
	} else {			// 수정 실패
		System.out.println("관리자 권한 수정 실패!");
		response.sendRedirect(request.getContextPath() + "/admin/adminList.jsp");
	}
	
%>