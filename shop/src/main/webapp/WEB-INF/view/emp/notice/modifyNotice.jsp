<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifyNotice.css">
</head>
<script>
    $(function() {
        // ✅ 확인 버튼 클릭 시
        $('#btnSave').on('click', function() {
            if (confirm('공지사항을 수정하시겠습니까?')) {
                $('#noticeEditForm').submit();
            }
        });

        // ✅ 취소 버튼 클릭 시
        $('#btnCancel').on('click', function() {
            if (confirm('수정을 취소하고 목록으로 돌아가시겠습니까?')) {
                window.location.href = '${pageContext.request.contextPath}/emp/notice/noticeList';
            }
        });
    });
    </script>
<body>
	<div class="notice-edit-container">
        <h2>공지사항 수정</h2>
          <form id="noticeEditForm" method="post" action="${pageContext.request.contextPath}/emp/notice/modifyNotice">
            <input type="hidden" name="noticeCode" value="${noticeOne.noticeCode}">
            <input type="hidden" name="empCode" value="${noticeOne.empCode}">

            <table>
                <tr>
                    <th>작성자</th>
                    <td><input type="text" value="${noticeOne.empName}" readonly></td>
                </tr>
                <tr>
                    <th>작성일자</th>
                    <td><input type="text" value="${noticeOne.createdate}" readonly></td>
                </tr>
                <tr>
                    <th>공지 제목</th>
                    <td><input type="text" name="noticeTitle" value="${noticeOne.noticeTitle}" required></td>
                </tr>
                <tr>
                    <th>공지 내용</th>
                    <td><textarea name="noticeContent" required>${noticeOne.noticeContent}</textarea></td>
                </tr>
            </table>

            <div class="btn-area">
                <button type="button" id="btnSave" class="btn btn-save">확인</button>
                <button type="button" id="btnCancel" class="btn btn-cancel">취소</button>
            </div>
        </form>
    </div>
</body>
</html>