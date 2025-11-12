<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<script>

	$(document).ready(function() {
	    $("#loginButton").click(function() {

	        const id = $("#id").val().trim();
	        const pw = $("#pw").val().trim();
	        const userType = $("input[name='customerOrEmpSel']:checked").val();
	
	        if (loginValidate(id, pw)) {

	            console.log("전송 userType =", userType);
	            $("#loginForm").submit();
	        }
	    });
	});
	
	function loginValidate(id, pw) {

	    if (id === "") {

	        alert("아이디를 입력해주세요.");
	        $("#id").focus();
	        return false;
	    }
	    
	    if (pw === "") {

	        alert("비밀번호를 입력해주세요.");
	        $("#pw").focus();
	        return false;
	    }
	    
	    return true;
	}
</script>
<body>
	<h1>login</h1>
	<form method = "post" action = "${pageContext.request.contextPath}/out/login" id = "loginForm">
	<div>
		<div>
			<table>
				<tr>
					<td>id</td>
					<td>
						<input type = "text" name = "id" id = "id" value = "admin01">
					</td>
				</tr>
				<tr>
					<td>pw</td>
					<td>
						<input type = "password" name = "pw" id = "pw" value = "1234">
					</td>
				</tr>
			</table>
			<button type = "button" id = "loginButton">로그인</button>
		</div>
		<div>
			<input type = "radio" name = "customerOrEmpSel" class = "customerOrEmpSel" value ="customer">customer
			<input type = "radio" name = "customerOrEmpSel" class = "customerOrEmpSel" value ="emp"  checked="checked">emp
		</div>
	</div>
	</form>
	<a href = "${pageContext.request.contextPath}/out/addMember" id = "signUp">회원가입</a>
</body>
</html>