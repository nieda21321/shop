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
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	
	<div>
		<div>
			<img src ="${pageContext.request.contextPath}/upload/${goods.filename}">
		</div>
		<div>
			<form action="">
			<input type = "hidden" name = "goodsCode" value = "${goods.goodsCode}">
			<input type = "hidden" name = "contextPath" value = "${pageContext.request.contextPath}">
			<table>
				<tr>
					<td>goodsName</td>
					<td>${goods.goodsName}</td>
				</tr>
				<tr>
					<td>goodsPrice</td>
					<td>${goods.>goodsPrice}</td>
				</tr>
				<tr>
					<td>pointRate</td>
					<td>${goods.pointRate}</td>
				</tr>
				<tr>
					<td>soldout</td>
					<td>${goods.soldout}</td>
				</tr>
				<tr>
				<td>
					<select name = "cartQuantity">
						<c:forEach var = "n" begin = "1" end = "10">
							<option value = "${n}"><${n}></option>
						</c:forEach>
					</select>
				</td>
				</tr>
			</table>
			<%-- insert cart --%>
			<button type = "button" id = "cartBtn">장바구니</button>
			<%-- 결제(화면) --%>
			<button type = "button" id = "orderBtn">바로주문</button>
			</form>
		</div>
	</div>
</body>
<script>
	$('#cartBtn').click(function() {
		
		$('#myForm').attr('action', $('#contextPath') + "/customer/goods/addCart" );
		$('#myForm').submit();
	});
	
	$('#orderBtn').click(function() {
		
		$('#myForm').attr('action', $('#contextPath') + "/customer/goods/addOrders" );
		$('#myForm').submit();
	});
</script>
</html>