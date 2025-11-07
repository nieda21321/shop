<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeList.css">
</head>

<script>
	$(function() {
	    // 공지사항 등록 버튼 클릭
	    $('#addNoticeBtn').on('click', function() {
	        location.href = '${pageContext.request.contextPath}/emp/notice/addNotice';
	    });
	});
</script>

<body>
<div class="page-container">
    <h1 class="page-title">공지사항 관리</h1>

    <%-- 관리자 메뉴 --%>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>

    <!-- 상단 버튼 -->
    <div class="top-controls">
        <button type="button" id="addNoticeBtn" class="btn btn-add">공지사항 등록</button>
    </div>

    <!-- 공지사항 테이블 -->
	<table class="styled-table">
	    <thead>
	        <tr>
	            <th>공지번호</th>
	            <th>제목</th>
	            <th>등록일자</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="n" items="${list}">
	            <tr>
	                <td>${n.noticeNum}</td>
	                <td>
	                    <a href="${pageContext.request.contextPath}/emp/notice/noticeOne?noticeCode=${n.noticeCode}" 
	                       class="notice-link">
	                        ${n.noticeTitle}
	                    </a>
	                </td>
	                <td>${n.createdate}</td>
	            </tr>
	        </c:forEach>
	
	        <c:if test="${empty list}">
	            <tr>
	                <td colspan="3" class="no-data">등록된 공지사항이 없습니다.</td>
	            </tr>
	        </c:if>
	    </tbody>
	</table>

    <!-- 페이지네이션 -->
    <div class="pagination-controls">
         <%-- 맨 처음으로 이동 --%>
        <c:if test="${currentPage > 1}">
            <button type="button" class="btn-page" 
                    onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList?currentPage=1'">&laquo; 처음</button>
        </c:if>

        <%-- 이전 버튼 --%>
        <c:if test="${startPage > 1}">
            <button type="button" class="btn-page" 
                    onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList?currentPage=${startPage-10}'">이전</button>
        </c:if>

        <%-- 페이지 번호 --%>
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <span class="page-current">${i}</span>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn-page" 
                            onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList?currentPage=${i}'">${i}</button>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%-- 다음 버튼 --%>
        <c:if test="${lastPage > endPage}">
            <button type="button" class="btn-page" 
                    onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList?currentPage=${startPage+10}'">다음</button>
        </c:if>

        <%-- 맨 끝으로 이동 --%>
        <c:if test="${currentPage < lastPage}">
            <button type="button" class="btn-page" 
                    onclick="location.href='${pageContext.request.contextPath}/emp/notice/noticeList?currentPage=${lastPage}'">끝 &raquo;</button>
        </c:if>
    </div>
</div>
</body>
</html>
