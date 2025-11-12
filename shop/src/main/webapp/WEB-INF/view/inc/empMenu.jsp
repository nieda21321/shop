<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/empMenu.css">
</head>
<body>
    <div class="menu-container">
        <a href="${pageContext.request.contextPath}/emp/empList">사원관리</a>
        <a href="${pageContext.request.contextPath}/emp/customer/customerList">고객관리</a>
        <a href="${pageContext.request.contextPath}/emp/outId/outIdList">탈퇴회원관리</a>
        <a href="${pageContext.request.contextPath}/emp/goods/goodsList">상품관리</a>
        <a href="${pageContext.request.contextPath}/emp/orders/ordersList">주문관리</a>
        <a href="${pageContext.request.contextPath}/emp/notice/noticeList">공지관리</a>
        <a href="${pageContext.request.contextPath}/emp/question/questionList">주문질문관리</a>
        <a href="${pageContext.request.contextPath}/emp/review/reviewList">상품리뷰관리</a>
        <%-- AJax 비동기적으로 --%>
        <a href="${pageContext.request.contextPath}/emp/stats/statsList">통계자료</a>
    </div>
</body>
</html>