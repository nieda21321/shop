<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addEmp.css">
<script>
$(function() {
   

    	 // ✅ 아이디 입력 후 중복체크
        $('#empId').on('blur', function() {
        	
        	let empId = $(this).val().trim();
        	
        	if (empId.length < 4) return;

            $.ajax({
                url: '${pageContext.request.contextPath}/emp/checkId',
                type: 'POST',
                dataType: 'json',
                data: { empId: empId },
                success: function(response) {
                    if (response.duplicate) {
                        $('#empIdError').text('이미 사용 중인 아이디입니다.');
                        isIdChecked = false;
                    } else {
                        $('#empIdError').text('사용 가능한 아이디입니다.').css('color', 'green');
                        isIdChecked = true;
                    }
                },
                error: function() {
                    $('#empIdError').text('아이디 중복 확인 중 오류가 발생했습니다.');
                    isIdChecked = false;
                }
            });
        });
     
     $('#submitBtn').on('click', function() {
       
    	 $('.error').not('#empIdError').text('');
        
        let empId = $('#empId').val().trim();
        let empPw = $('#empPw').val().trim();
        let empName = $('#empName').val().trim();
        
        let isValid = true;

        
        //  ID 공백 및 길이검사
        if (empId === "") {
            $('#empIdError').text('아이디를 입력해주세요.');
            isValid = false;
        } else if (empId.length < 4) {
            $('#empIdError').text('아이디는 4자 이상이어야 합니다.');
            isValid = false;
        }

        //  비밀번호 공백검사
        if (empPw === "") {
            $('#empPwError').text('비밀번호를 입력해주세요.');
            isValid = false;
        }

        //  이름 공백검사
        if (empName === "") {
            $('#empNameError').text('이름을 입력해주세요.');
            isValid = false;
        }

        //  통과 시 submit
        if (isValid) {
            $('#empForm').submit();
        }
    });
    
 	// ✅ 취소 버튼 클릭 시 사원 리스트 페이지로 이동
    $('#cancelBtn').on('click', function() {

        window.location.href = '${pageContext.request.contextPath}/emp/empList';
    });
});
</script>
</head>

<body>
    <div class="form-container">
        <h2>직원 등록</h2>
        <form id="empForm" method="post" action="${pageContext.request.contextPath}/emp/addEmp">
            <label for="empId">아이디</label>
            <input type="text" id="empId" name="empId" maxlength="20">
            <div class="error" id="empIdError"></div>

            <label for="empPw">비밀번호</label>
            <input type="password" id="empPw" name="empPw" maxlength="20">
            <div class="error" id="empPwError"></div>

            <label for="empName">이름</label>
            <input type="text" id="empName" name="empName" maxlength="20">
            <div class="error" id="empNameError"></div>

            <div class="button-group">
                <button type="button" id="submitBtn" class="btn-submit">등록하기</button>
                <button type="button" id="cancelBtn" class="btn-cancel">취소</button>
            </div>
        </form>
    </div>
</body>
</html>
