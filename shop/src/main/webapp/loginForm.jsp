<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <meta charset="utf-8">
    <title>MultiShop - Online Shop Website Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <!-- Favicon -->
    <link href="multishop/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">  

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="multishop/lib/animate/animate.min.css" rel="stylesheet">
    <link href="multishop/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="multishop/css/style.css" rel="stylesheet">
</head>
		<div class="container-fluid pt-5 pb-3">
        <div class="row px-xl-5">
            <div class="col-md-6">
                <div class="product-offer mb-30" style="height: 300px;">
                    <img class="multishop/img-fluid" src="multishop/img/offer-1.jpg" alt="">
                    <div class="offer-text">
                         <form id="customerForm" action="<%= request.getContextPath()%>/customerLoginAction.jsp"method="post">
							<fieldset>
								<legend>쇼핑몰 고객 로그인</legend>
								<table border="1">
									<tr>
										<td>ID</td>
										<td><input type="text" name="customerId" id="customerId"></td>
									</tr>
									<tr>
										<td>PASSWORD</td>
										<td><input type="text" name="customerPass" id="customerPass"></td>
									</tr>
								</table>
								<button type=button id="customerBtn">고객 로그인</button>
							</fieldset>
						</form>
		                <a href="<%= request.getContextPath()%>/addCustomer.jsp" class="btn btn-danger">고객 회원가입</a>
						
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="product-offer mb-30" style="height: 300px;">
                    <img class="multishop/img-fluid" src="multishop/img/offer-2.jpg" alt="">
                    <div class="offer-text">
            			<form id="employeeForm" action="<%= request.getContextPath()%>/employeeLoginAction.jsp"method="post">
							<fieldset>
								<legend>쇼핑몰 스탭 로그인</legend>
								<table border="1">
									<tr>
										<td>ID</td>
										<td><input type="text" name="employeeId"></td>
									</tr>
									<tr>
										<td>PASSWORD</td>
										<td><input type="text" name="employeePass"></td>
									</tr>
								</table>
								<button type="button" id="employeeBtn">스탭 로그인</button>
							</fieldset>
						</form>
						<a href="<%= request.getContextPath()%>/addEmployee.jsp" class="btn btn-danger">스탭 회원가입</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
	<!--  -->
	<!--  -->
	<!--  -->
	
</body>
<script>
	/* 
	let test = function(){
		
	};
	function test(){} 
	
	자바스크립트는 문자열만 존재 
	*/
/* 고객 버튼을 눌렀을떄 알람*/
    $('#customerBtn').click(function(){
      if($('#customerId').val() == '') {
         alert('고객 아이디를 입력하세요');
      } else if($('#customerPass').val() == '') {
         alert('고객 패스워드를 입력하세요');
      } else {
         customerForm.submit();
      }
   });
   
   $('#employeeBtn').click(function(){
      if($('#employeeId').val() == '') {
         alert('스텝 아이디를 입력하세요');
      } else if($('#employeePass').val() == '') {
         alert('스텝 패스워드를 입력하세요');
      } else {
         employeeForm.submit();
      }
   });
</script>
<%@ include file="footer.jsp"%>
</html>