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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNotice.css">
</head>
<script>
$(function() {
    // ✅ 확인 버튼 클릭 시
    $('#saveBtn').on('click', function() {
        const title = $('#noticeTitle').val().trim();
        const content = $('#noticeContent').val().trim();

        if (title === '' || content === '') {
            alert('제목과 내용을 모두 입력해주세요.');
            return;
        }

        if (confirm('공지사항을 등록하시겠습니까?')) {
            $('#addNoticeForm').submit();
        }
    });

    // ✅ 취소 버튼 클릭 시
    $('#cancelBtn').on('click', function() {
        if (confirm('공지사항 작성을 취소하시겠습니까?')) {
            location.href = '${pageContext.request.contextPath}/emp/notice/noticeList';
        }
    });
});
</script>
<body>
	<h1></h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	
	<div class="notice-form-container">
        <h2>공지사항 등록</h2>

        <form id="addNoticeForm" method="post" action="${pageContext.request.contextPath}/emp/notice/addNotice">

            <!-- 히든값: 로그인한 사원 코드 -->
            <input type="hidden" name="empCode" value="${loginEmpCode}">

            <!-- 사원정보 -->
            <div class="form-row">
                <label for="empId">작성자 ID</label>
                <input type="text" id="empId" name="empId" value="${empId}" readonly>

                <label for="empName" style="margin-left:20px;">작성자 이름</label>
                <input type="text" id="empName" name="empName" value="${empName}" readonly>
            </div>

            <!-- 제목 -->
            <div class="form-row">
                <label for="noticeTitle">제목</label>
                <input type="text" id="noticeTitle" name="noticeTitle" placeholder="공지 제목을 입력하세요" required>
            </div>

            <!-- 내용 -->
            <div class="form-row">
                <label for="noticeContent">내용</label>
                <textarea id="noticeContent" name="noticeContent" placeholder="공지 내용을 입력하세요" required></textarea>
            </div>

            <!-- 버튼 영역 -->
            <div class="btn-area">
                <button type="button" class="btn btn-save" id="saveBtn">확인</button>
                <button type="button" class="btn btn-cancel" id="cancelBtn">취소</button>
            </div>
        </form>
    </div>

</body>
</html>