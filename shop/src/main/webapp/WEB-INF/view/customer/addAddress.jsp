<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%-- 달러표시해줘야함--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addEmp.css">
</head>
<body>
	<h1>addAddress</h1>
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
	
	<div>
		<form action="">
			<div>
				기본 주소 : <input type = "text" name = "address" id = "address" readonly="readonly">
				<%-- open API 호출(kakao 주소 API)--%>
				<button type = "button">주소검색</button>
				상세주소 : <input type = "text" name = "address2" id = "address2">
			</div>
			<button type = "button">배송지추가</button>
		</form>
	</div>
</body>
</html>