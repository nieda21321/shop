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
	    let isIdChecked = false; // 사용여부 확인
	
	    // ✅ 아이디 사용여부 확인 버튼 클릭
	    $('#checkIdBtn').on('click', function(){
	        
	    	let id = $('#customerId').val().trim();
	        let idPattern = /^[a-zA-Z0-9]{4,20}$/;
	
	        $('#idError').css('color','red').text('');
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

	                    $('#idError').text('이미 사용 중인 아이디입니다.');
	                    isIdChecked = false;
	                } else {

	                    $('#idError').css('color', 'green').text('사용 가능한 아이디입니다.');
	                    isIdChecked = true;
	                }
	            },
	            error: function(){

	                $('#idError').text('중복 확인 중 오류가 발생했습니다.');
	                isIdChecked = false;
	            }
	        });
	    });
	
	    //  회원가입 버튼 클릭
	    $('#submitBtn').on('click', function(){
	       
	    	$('.error').text('');
	        $('#idError').css('color', 'red'); // 초기화
	
	        let id = $('#customerId').val().trim();
	        let pw = $('#customerPw').val().trim();
	        let pwConfirm = $('#customerPwConfirm').val().trim();
	        let name = $('#customerName').val().trim();
	        let phone = $('#customerPhone').val().trim();
	        let address = $('#address').val().trim();
	
	        let isValid = true;
	        
	        // 아이디 유효성 검사
	        let idPattern = /^[a-zA-Z0-9]{4,20}$/;
	        if(!idPattern.test(id)) {
	          
	        	$('#idError').text('아이디는 영어와 숫자로 4~20자여야 합니다.');
	            isValid = false;
	        }
	
	        if(!isIdChecked) {
	           
	        	$('#idError').text('아이디 중복체크를 해주세요.');
	            isValid = false;
	        }
	
	        // PW 검사 (4~20자, 영어+숫자)
	        let pwPattern = /^[a-zA-Z0-9]{4,20}$/;
	        if(!pwPattern.test(pw)) {
	            
	        	$('#pwError').text('비밀번호는 영어와 숫자로 4~20자여야 합니다.');
	            isValid = false;
	        }
	        
	        // 비밀번호 확인 검사
	        if(pw !== pwConfirm) {
	           
	        	$('#pwConfirmError').text('비밀번호가 일치하지 않습니다.');
	            isValid = false;
	        }
	
	        // 이름 검사 (정확히 4자)
	        if(name.length !== 4) {
	          
	        	$('#nameError').text('이름은 정확히 4자여야 합니다.');
	            isValid = false;
	        }
	
	        // 전화번호 검사 (000-0000-0000 or 000-000-0000)
	        let phonePattern = /^01[0-9]-\d{3,4}-\d{4}$/;
	        if(!phonePattern.test(phone)) {
	           
	        	$('#phoneError').text('전화번호 형식은 010-0000-0000 또는 010-000-0000 이어야 합니다.');
	            isValid = false;
	        } else {
	          
	        	phone = phone.replace(/-/g, '');
	            $('#customerPhone').val(phone);
	        }
	        
	    	 // 공백 검사
	        if (id === "") {
	           
	        	$('#idError').text('아이디를 입력해주세요.');
	            isValid = false;
	        }
	        if (pw === "") {
	            
	        	$('#pwError').text('비밀번호를 입력해주세요.');
	            isValid = false;
	        }
	        if (pwConfirm === "") {
	          
	        	$('#pwConfirmError').text('비밀번호 확인을 입력해주세요.');
	            isValid = false;
	        }
	        if (name === "") {
	           
	        	$('#nameError').text('이름을 입력해주세요.');
	            isValid = false;
	        }
	        if (phone === "") {
	           
	        	$('#phoneError').text('전화번호를 입력해주세요.');
	            isValid = false;
	        }
	        if (address === "") {
	           
	        	$('#addressError').text('주소를 입력해주세요.');
	            isValid = false;
	        }
	
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
                <input type="text" id="customerId" name="customerId" maxlength="20">
                <button type="button" id="checkIdBtn" class="small-btn">사용가능여부확인</button>
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
        <tr>
            <td>전화번호</td>
            <td>
                <input type="text" id="customerPhone" name="customerPhone" placeholder="010-1234-5678">
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