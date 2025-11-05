<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>goodsList</h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	
	<a href = "${pageContext.request.contextPath}/emp/addGoods">상품추가</a>
	
	<div>
		<%-- 리스트 출력 --%>
	</div>
</body>
</html>