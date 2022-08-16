<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<!-- id check -->
		<div class="container">
		<h3>고객회원가입</h3>
		<table border="1">
			<tr>
				<td>
					ID 체크
					<input type="text" name="idck" id="idck">
					<button type="button" value="중복확인" id="btn" >아이디 중복 검사</button>
				</td>			
			</tr>
		</table>
		</div>

	
	<!-- 고객 가입 form -->
	<!-- 
		idAction 에서 true가 나오면 다시 이 페이지로 돌아와 가입 진행
		idAction을 무조거 거쳐야 갑입 진행가능
	 -->
	 <div class="container">
	<form action="<%= request.getContextPath() %>/addCustomerAction.jsp" mehtod="post">
		<table border="1">
			<tr>
				<td>사용 가능한 아이디</td>
				<td>
					<input type="text" name="customerId" id="customerId" readonly="readonly" >
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td> <input type="passsword" name="customerPw" id="customerPw" > </td>
			</tr>
			<tr>
				<td>회원 이름</td>
				<td> <input type="text" name="customerName" id="customerName" >  </td>
			</tr>
			<tr>
				<td>주소</td>
				<td> <input type="text" name="customerAddress" id="customerAddress" >  </td>
			</tr>
			<tr>
				<td>연락처</td>
				<td> <input type="text" name="customerTelephone" id="customerTelephone" >  </td>
			</tr>
		</table>
		<button type="submit">회원가입</button>	
	</form>
	</div>
</body>
<script>
	$('#btn').click(function() {
		console.log("ddddd");
		if($('#idck').val().length < 4) {
			alert('id는 4자이상!');
		} else {
			// 비동기 호출	
			$.ajax({
				url : '/shop/idckController',
				type : 'post',
				data : {idck : $('#idck').val()},
				success : function(json) {
					// alert(json);
					if(json == 'y') {
						alert('사용가능한 아이디 입니다.');
						$('#customerId').val($('#idck').val());
					} else {
						alert('이미 사용중인 아이디 입니다.');
						$('#customerId').val('');
					}
				}
			});
		}
	});
</script>
<%@ include file="footer.jsp"%>
</html>