<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%-- 달러표시해줘야함--%>
<link rel="stylesheet" href="{pageContext.request.contextPath}/css/common.css">
</head>
<body>
	<h1></h1>
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>

	<form action="">
		<table>
			<tr>
				<th>선택</th>
				<th>goodsName</th>
				<th>goodsPrice</th>
				<th>cartQuantity</th>
				<th>totalPrice</th>
				<th>soldout</th>
			</tr>
			<c:forEach var = "m" items = "${cartList}">
				<tr>
					<td>
						<c:if test="${m.soldout == 'soldout'}">
							soldout
						</c:if>
						<c:if test="${m.soldout != 'soldout'}">
							<input type = "checkbox" name = "ck" value = "${m.cartCode}">
						</c:if>
					</td>
					<td>${m.goodsName}</td>
					<td>${m.goodsPrice}</td>
					<td>${m.cartQuantity}</td>
					<td>${m.totalPrice}</td>
					<td>${m.soldout}</td>
				</tr>
			</c:forEach>
		</table>
		<button type = "button"> 주문 하기 </button>
	</form>

</body>
</html>