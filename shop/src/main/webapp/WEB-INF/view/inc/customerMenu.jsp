<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/empMenu.css">
<div>
	<div class="menu-container">
	
		<%-- 상품목록 / 상세보기 / 주문 /  --%>
		<a href="${pageContext.request.contextPath}/customer/customerIndex">상품목록</a>
	
        <%-- 개인정보 - 개인정보 열람 / 비밀번호 수정 pw_history에 현재 비밀번호 입력 ( 비밀번호 입력이 6개가 되었으면 가장빠른데이터는 삭제 ) / 연락처 수정 /
         회원 탈퇴 ( outId 입력 + customer 삭제 ) / --%>
        <a href="${pageContext.request.contextPath}/customer/manage/customerInfoPage">개인정보</a>
        <%-- 배송지 목록 / 배송지 추가 ( 최대 5개 6번째 입력 시 가장 먼저 입력된 주소지 삭제) / 삭제 --%>
        <a href="${pageContext.request.contextPath}/customer/addressList">배송지관리</a>

        
        <a href="${pageContext.request.contextPath}/customer/cartList">장바구니</a>        
    </div>
</div>
