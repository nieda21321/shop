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
	<h1>customerIndex</h1>
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
	
	<div>
		${loginCustomer.customerName}님 반갑습니다.
		(point : ${loginCustomer.point})
		<a href = "${pageContext.request.contextPath}/customer/customerLogout">로그아웃</a>
	</div>
	
	<h2>베스트 상품목록</h2>
	<%-- 베스트 : 가장 많이 주무(주문완료)된 상품 5개 --%>
	<div>
		베스트 상품(5개)	
	</div>
	
	
	<h2> 상품목록</h2>
	<%-- 베스트 : 가장 많이 주무(주문완료)된 상품 5개 --%>
	<div>
		<table>
			<tr>
				<%-- c:forEach varStatus : index(0 ~ ), count(1 ~ ), first(true, false), last(true, false) --%>
				<c:forEach var = "m" items = "${customerGoodsList}" varStatus="state">
					
						<td>
							<%-- image --%>
							<div>
								<img src="${pageContext.request.contextPath}/upload/${m.filename}">
							</div>
							<%-- 이름, 가격 --%>
							<div>
								${m.goodsName}<br>
								${m.goodsPrice}
							</div>
						</td>
						<c:if test = "${state.last == false && state.count % 5 == 0 }">
							</tr><tr>
						</c:if>
				</c:forEach>
			</tr>
		</table>	
	</div>
</body>
</html>