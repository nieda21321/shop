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
        <a href="${pageContext.request.contextPath}/emp/customerList">고객관리</a>
        <a href="${pageContext.request.contextPath}/emp/outIdList">탈퇴회원관리</a>
        <a href="${pageContext.request.contextPath}/emp/goodsList">상품관리</a>
        <a href="${pageContext.request.contextPath}/emp/ordersList">주문관리</a>
        <a href="${pageContext.request.contextPath}/emp/notice/noticeList">공지관리</a>
    </div>
</body>
</html>