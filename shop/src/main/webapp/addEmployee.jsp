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
	<!-- 아이디 중복검사 -->
	<div class="container">
	<h3>스탭회원가입</h3>
		<h5>ID 체크</h5>
		<table border="1">
		<tr>
			<td>
				<input type="text" name="idck" id="idck">
			</td>
		</tr>
		</table>
		<button type="button" value="중복확인" id="btn">아이디 중복 검사</button>
	</div>
	<!-- 스탭 가입 form -->
	<!-- 
		idAction 에서 true가 나오면 다시 이 페이지로 돌아와 가입 진행
		idAction을 무조거 거쳐야 갑입 진행가능
	 -->
	 
	 <div class="container">
	 <form action="<%= request.getContextPath() %>/addEmployeeAction.jsp"  method="past">
	 	<table border="1">
	 		<tr>
		 		<td>사용 가능한 아이디</td>
		 		<td>
					<input type="text" name="employeeId" id="employeeId" readonly="readonly" >
				</td>
	 		</tr>
	 		<tr>
	 			<td>비밀번호</td>
	 			<td><input type = "text" name="employeePw" id="employeePw"></td>
	 		</tr>
	 		
	 		<tr>
	 			<td>사용자 이름</td>
	 			<td><input type="text" name="employeeName" id="employeeName"></td>
	 		</tr>	 		
	 	</table>
	 	<button type="submit">회원가입</button>
	 </form>
	 </div>
</body>
<script>
	$('#btn').click(function() {
		if($('#idck').val().length < 4) {
			alert('id는 4자이상!');
		} else {
			console.log("ddddd");
			// 비동기 호출	
			$.ajax({
				url : '/shop/idckController',
				type : 'post',
				data : {idck : $('#idck').val()},
				success : function(json) {
					// alert(json);
					if(json == 'y') {
						alert('사용가능한 아이디 입니다.');
						$('#employeeId').val($('#idck').val());
					} else {
						alert('이미 사용중인 아이디 입니다.');
						$('#employeeId').val('');
					}
				}
			});
		}
	});
	</script>
<%@ include file="footer.jsp"%>
</html>