<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addMember.css">
</head>

<script>
$(function(){
    let isIdChecked = false; // 중복체크 여부

    // ✅ 아이디 중복확인
    $('#checkIdBtn').on('click', function(){
        let id = $('#customerId').val().trim();
        let idPattern = /^[a-zA-Z0-9]{4,20}$/;

        $('#idError').text('');
        if(!idPattern.test(id)) {
            $('#idError').text('아이디는 영어와 숫자로 4~20자여야 합니다.');
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/out/checkId",
            type: "POST",
            data: { customerId: id },
            success: function(response){
                if(response === 'exists'){
                    $('#idError').text('이미 사용 중인 아이디입니다.').css('color','red');
                    isIdChecked = false;
                } else {
                    $('#idError').text('사용 가능한 아이디입니다.').css('color','green');
                    isIdChecked = true;
                }
            },
            error: function(){
                $('#idError').text('중복 확인 중 오류가 발생했습니다.').css('color','red');
                isIdChecked = false;
            }
        });
    });


    // ✅ 회원가입 버튼 클릭
    $('#submitBtn').on('click', function(){
        $('.error').text(''); // 모든 에러 초기화
        $('#idError').css('color', 'red');

        let id = $('#customerId').val().trim();
        let pw = $('#customerPw').val().trim();
        let pwConfirm = $('#customerPwConfirm').val().trim();
        let name = $('#customerName').val().trim();
        let address = $('#address').val().trim();

        let phone1 = $('#phone1').val().trim();
        let phone2 = $('#phone2').val().trim();
        let phone3 = $('#phone3').val().trim();
        let phone = phone1 + phone2 + phone3;
        $('#customerPhone').val(phone);

        let isValid = true;

        // 아이디 검사
        let idPattern = /^[a-zA-Z0-9]{4,20}$/;
        if(!idPattern.test(id)) {
            $('#idError').text('아이디는 영어와 숫자로 4~20자여야 합니다.');
            isValid = false;
        } else if(!isIdChecked) {
            $('#idError').text('아이디 중복체크를 해주세요.');
            isValid = false;
        }

        // 비밀번호 검사
        let pwPattern = /^[a-zA-Z0-9]{4,20}$/;
        if(!pwPattern.test(pw)) {
            $('#pwError').text('비밀번호는 영어와 숫자로 4~20자여야 합니다.');
            isValid = false;
        }

        // ✅ 비밀번호 확인 (여기 개선됨)
        if(pw !== pwConfirm) {
            $('#pwConfirmError').text('비밀번호가 일치하지 않습니다.');
            isValid = false;
        }

        // 이름 검사
        if(name === "") {
            $('#nameError').text('이름을 입력해주세요.');
            isValid = false;
        } else if(name.length !== 4) {
            $('#nameError').text('이름은 정확히 4자여야 합니다.');
            isValid = false;
        }

        // 전화번호 검사
        let phonePattern = /^01[0-9]{8,9}$/;
        if(!phonePattern.test(phone)) {
            $('#phoneError').text('전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)');
            isValid = false;
        }

        // 주소 검사
        if(address === "") {
            $('#addressError').text('주소를 입력해주세요.');
            isValid = false;
        }

        // ✅ 마지막에 한 번만 submit (중간에 return하지 않음)
        if(isValid){
            $('#joinForm').submit();
        }
    });
});
</script>

<body>

<h2 style="text-align:center;">회원가입</h2>

<form id="joinForm" method="post" action="${pageContext.request.contextPath}/out/addMember">
    <table>
        <tr>
		  <td>아이디</td>
		  <td>
		    <div class="input-with-btn">
		      <input type="text" id="customerId" name="customerId" maxlength="20" placeholder="아이디를 입력하세요">
		      <button type="button" id="checkIdBtn" class="check-btn">사용가능여부확인</button>
		    </div>
		    <div class="error" id="idError"></div>
		  </td>
		</tr>
        <tr>
            <td>비밀번호</td>
            <td>
                <input type="password" id="customerPw" name="customerPw" maxlength="20">
                <div class="error" id="pwError"></div>
            </td>
        </tr>
         <tr>
            <td>비밀번호 확인</td>
            <td>
                <input type="password" id="customerPwConfirm" name="customerPwConfirm" maxlength="20">
                <div class="error" id="pwConfirmError"></div>
            </td>
        </tr>
        <tr>
            <td>이름</td>
            <td>
                <input type="text" id="customerName" name="customerName" maxlength="4">
                <div class="error" id="nameError"></div>
            </td>
        </tr>
        <%--
        <tr>
            <td>전화번호</td>
            <td>
                <input type="text" id="customerPhone" name="customerPhone" placeholder="010-1234-5678">
                <div class="error" id="phoneError"></div>
            </td>
        </tr>
         --%>
         <tr>
		    <td>전화번호</td>
			  <td>
			      <div class="phone-input">
			          <input type="text" id="phone1" maxlength="3" placeholder="010"> -
			          <input type="text" id="phone2" maxlength="4" placeholder="1234"> -
			          <input type="text" id="phone3" maxlength="4" placeholder="5678">
			          <input type="hidden" id="customerPhone" name="customerPhone">
			      </div>
			      <div class="error" id="phoneError"></div>
			 </td>
		</tr>
         
        <tr>
            <td>주소</td>
            <td>
                <input type="text" id="address" name="address">
                <div class="error" id="addressError"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center;">
                <button type="button" id="submitBtn">회원가입</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>