<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%-- 달러표시해줘야함--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ordersList.css">
</head>
<!-- 스크립트 -->
<script>
$(function(){
    // 페이징
    $('#prevBtn').on('click', function() {
        let currentPage = parseInt('${currentPage}');
        if (currentPage > 1) {
            location.href = '${pageContext.request.contextPath}/emp/orders/ordersList?currentPage=' + (currentPage - 1);
        } else {
            alert('첫 페이지입니다.');
        }
    });

    $('#nextBtn').on('click', function() {
        let currentPage = parseInt('${currentPage}');
        let lastPage = parseInt('${lastPage}');
        if (currentPage < lastPage) {
            location.href = '${pageContext.request.contextPath}/emp/orders/orderList?currentPage=' + (currentPage + 1);
        } else {
            alert('마지막 페이지입니다.');
        }
    });

    // 편집 버튼 클릭 시 (추후 기능 확장 가능)
    $('#editBtn').on('click', function() {
        alert('편집 모드로 전환됩니다. (추후 구현)');
    });
});
</script>
<body>
	<div class="page-container">
    <h1 class="page-title">주문 관리</h1>

    <%-- 관리자 메뉴 include --%>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>

    <!-- 상단 컨트롤 -->
    <div class="top-controls">
        <button type="button" id="editBtn" class="btn btn-edit">편집</button>

        <div class="pagination-controls">
            <button type="button" id="prevBtn" class="btn btn-page">이전</button>
            <span class="page-info">${currentPage} / ${lastPage}</span>
            <button type="button" id="nextBtn" class="btn btn-page">다음</button>
        </div>
    </div>

    <!-- 주문 리스트 -->
    <table class="styled-table">
        <thead>
            <tr>
                <th>주문번호</th>
                <th>상품이미지</th>
                <th>상품명</th>
                <th>가격</th>
                <th>수량</th>
                <th>총금액</th>
                <th>고객명</th>
                <th>전화번호</th>
                <th>주소</th>
                <th>주문일</th>
                <th>상태</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="ol" items="${ordersList}">
                <tr>
                    <td>${ol.orderCode}</td>
                    <td>
                        <img src="${pageContext.request.contextPath}/upload/${ol.fileName}" alt="상품이미지" class="goods-img">
                    </td>
                    <td>${ol.goodsName}</td>
                    <td>${ol.goodsPrice}원</td>
                    <td>${ol.orderQuantity}</td>
                    <td>${ol.orderPrice}원</td>
                    <td>${ol.customerName}</td>
                    <td>${ol.customerPhone}</td>
                    <td>${ol.address}</td>
                    <td>${ol.createdate}</td>
                    <td>${ol.orderState}</td>
                </tr>
            </c:forEach>

            <c:if test="${empty ordersList}">
                <tr>
                    <td colspan="11" class="no-data">주문 내역이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>