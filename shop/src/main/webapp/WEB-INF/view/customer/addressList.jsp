<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%-- 달러표시해줘야함--%>
<link rel="stylesheet" href="{pageContext.request.contextPath}/css/addEmp.css">
</head>
<body>
	<h1>addressList</h1>
	<%-- customer menu include --%>
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
	
	<div>
		<%-- 배송지 최대 5개 : 6번쨰 입력 시 가장 오래된 데이터 삭제 후 입력(트랜젝션)--%>
		<a href = "${pageContext.request.contextPath}/customer/addAddress">배송지추가</a>
	</div>
</body>
</html>