<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeOne.css">
</head>

<body>
<div class="page-container">
    <h1 class="page-title">공지사항 상세보기</h1>

    <%-- 관리자 메뉴 --%>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>

    <div class="notice-detail">
        <table class="notice-table">
             <tr>
                <th>작성자 계정</th>
                <td>${noticeOne.empId}</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${noticeOne.empName}</td>
            </tr>
            <tr>
                <th>작성일자</th>
                <td>${noticeOne.createdate}</td>
            </tr>
             <tr>
                <th>공지 제목</th>
                <td>${noticeOne.noticeTitle}</td>
            </tr>
            <tr class="content-row">
                <th>공지 내용</th>
                <td class="notice-content">${noticeOne.noticeContent}</td>
            </tr>
        </table>
    </div>

    <div class="btn-area">
        <!-- 목록 버튼 -->
        <button type="button" class="btn btn-list" 
            onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList'">
            목록
        </button>

        <!-- 수정 & 삭제 버튼 (작성자만 노출) -->
        <c:if test="${sessionUserCode == noticeOne.empCode}">
            <button type="button" class="btn btn-edit" 
                onclick="location.href='${pageContext.request.contextPath}/emp/notice/modifyNotice?noticeCode=${noticeOne.noticeCode}&empCode=${noticeOne.empCode}'">
                수정
            </button>

            <button type="button" class="btn btn-delete" 
                onclick="if(confirm('정말 삭제하시겠습니까?')) 
                         location.href='${pageContext.request.contextPath}/emp/notice/removeNotice?noticeCode=${noticeOne.noticeCode}&empCode=${noticeOne.empCode}'">
                삭제
            </button>
        </c:if>
    </div>
</div>
</body>
</html>
