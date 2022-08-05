<%@page import="service.CustomerService"%>
<%@page import="service.SignService"%>
<%@page import="vo.Customer"%>
<%
//인코딩
request.setCharacterEncoding("utf-8");

Customer customer = new Customer();
// 값 불러오기
String customerId = request.getParameter("customerId");
String customerPw = request.getParameter("customerPw");
String customerName = request.getParameter("customerName");
String customerAddress = request.getParameter("customerAddress");
String customerTelephone = request.getParameter("customerTelephone");

// 디버깅
System.out.println("customerId= " + customerId);
System.out.println("pw = " + customerPw);
System.out.println("name = " + customerName);
System.out.println("address = " + customerAddress);
System.out.println("phoneNumber = " + customerTelephone);

customer.setCustomerId(customerId);
customer.setCustomerPw(customerPw);
customer.setCustomerName(customerName);
customer.setCustomerAddress(customerAddress);
customer.setCustomerTelephone(customerTelephone);

System.out.println("addCaction");
CustomerService customerService = new CustomerService();
boolean result = customerService.insertCustomer(customer);

System.out.println("addCaction1");
if(result) {
	System.out.println(result + "성공");
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
	System.out.println("addCaction2");
} else {
	System.out.println(result + "실패");
	response.sendRedirect(request.getContextPath() + "/addCustomer.jsp");
}

%>