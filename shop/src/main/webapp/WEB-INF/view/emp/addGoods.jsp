<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addEmp.css">
</head>
<body>
	<h1>addGoods</h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	
	<form method = "post" enctype = "multipart/form-data" action = "${pageContext.request.contextPath}/emp/addGoods">
		<table>
			<tr>
				<td>goodsName</td>
				<td>
					<input type = "text" name = "goodsName">
				</td>
			</tr>
			<tr>
				<td>goodsPrice</td>
				<td>
					<input type = "number" name = "goodsPrice">
				</td>
			</tr>
			<tr>
				<td>pointRate</td>
				<td>
					<input type = "number" name = "pointRate">
				</td>
			</tr>
			<tr>
				<td>goodsImg(png / jpg / gif)</td>
				<td>
					<input type = "file" name = "goodsImg">
				</td>
			</tr>
		</table>
		<button type = "submit">상품등록</button>
	</form>
</body>
</html>