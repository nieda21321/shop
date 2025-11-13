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
	
	
	<div>
		<form action ="${pageContext.request.contextPath}/customer/goods/addOrders" method = "post">
			<table>
				<tr>
					<th>filename</th>
					<th>goodsName</th>
					<th>goodsPrice</th>
					<th>pointRate</th>
					<th>cartQuantity</th>
				</tr>
				<c:forEach var = "m" items = "${list}">
					<input type = "hidden" name = "goodsCode" value = "${m.goodsName}">
					<input type = "hidden" name = "goodsPrice" value = "${m.goodsPrice}">
					<input type = "hidden" name = "orderQuantity" value = "${m.goodsQuantity}">
					<tr>
						<td><img src = "${pageContext.request.contextPath}/upload/${m.filename}"></td>
						<td>${m.goodsName}</td>
						<td>${m.goodsPrice}</td>
						<td>${m.pointRate}</td>
						<td>${m.cartQuantity}</td>
					</tr>
				</c:forEach>
			</table>
			
			
			<div>
				<button type = "button" id = "addressBtn"> 배송지 선택 </button>
				<select id = "addressList" size = "5">
					<c:forEach var = "aa" items = "${addressList}">
						<option class = "addrOpt" value = "${aa.addressCode}">${aa.address}</option>
					</c:forEach>
				</select>
				
				<input type = "text" id = "addressCode" name = "addressCode" readonly="readonly">
				<input type = "text" id = "address" readonly="readonly">
			</div>

			<div>
				결제금액 : <input type = "number" name = "orderPrice" value = "${orderPrice}" readonly="readonly">
				<%-- 결제 테이블을 별도로 생성해서 결제코드/결제일자/결제금액/포인트사용금액/.... --%> 
			</div>				

			<button type = "button">결제하기(주문완료)</button>
		</form>
	</div>
	
	<script>
		
		$('#addressList').dblclick(function() {

			$('#addressCode').val($('#addressList').val());
			
			$('#address').val($('.addrOpt:selected').text());
		});

	</script>
	
</body>
</html>