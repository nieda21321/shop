<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/removeCustomerByEmpWindow.css">
</head>
<script>
$(function() {
   
	// ✅ 확인 버튼 클릭 시
    $('#confirmBtn').on('click', function() {
        let memo = $('textarea[name="memo"]').val().trim();

        if (memo === '') {
            alert("탈퇴 사유를 입력해주세요.");
            return;
        }

        if (confirm("정말로 이 고객을 탈퇴 처리하시겠습니까?")) {

        	$.ajax({
        	    url: '${pageContext.request.contextPath}/emp/removeCustomerByEmpAjax',
        	    type: 'POST',
        	    data: $('#removeForm').serialize(),
        	    dataType: 'json',
        	    success: function(response) {
        	        if (response.status === 'success') {

        	            alert("탈퇴 처리가 완료되었습니다.");
        	            window.close();
        	        } else {

        	            alert("처리 중 오류가 발생했습니다: " + response.message);
        	        }
        	    },
        	    error: function() {

        	        alert("서버 통신 중 오류가 발생했습니다.");
        	    }
        	});
        }
    });

    // ✅ 취소 버튼 클릭 시 팝업 닫기
    $('#cancelBtn').on('click', function() {

        window.close(); // 팝업 닫기
    });
});
</script>
<body>
	<div class="form-container">
        <h2>회원 탈퇴 처리</h2>

        <form id="removeForm" method="post" action="${pageContext.request.contextPath}/emp/removeCustomerByEmp">
           
            <input type="hidden" name="customerId" value="${param.customerId}">
            <input type="hidden" name="customerName" value="${param.customerName}">

            <table>
                <tr>
                    <td>고객명</td>
                    <td>${param.customerName}</td>
                </tr>
                <tr>
                    <td>고객 아이디</td>
                    <td>${param.customerId}</td>
                </tr>
                <tr>
                    <td>탈퇴 사유</td>
                    <td>
                        <textarea name="memo" rows="5" placeholder="탈퇴 사유를 입력하세요.">test</textarea>
                    </td>
                </tr>
            </table>

            <div class="button-group">
                <button type="button" class="btn btn-confirm" id="confirmBtn">확인</button>
                <button type="button" class="btn btn-cancel" id="cancelBtn">취소</button>
            </div>
        </form>
    </div>
</body>
</html>