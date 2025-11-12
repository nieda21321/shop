<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개인정보 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/customerInfo.css">
</head>
<body>
    <c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>

    <div class="info-container">
	    <h2 class="info-title">🔒 개인정보 관리</h2>
	
	    <!-- 1️⃣ 비밀번호 확인 영역 -->
	    <div id="password-section">
	        <div class="password-box">
	            <p>본인 확인을 위해 비밀번호를 입력해주세요.</p>
	            <input type="password" id="customerPw" class="password-input" placeholder="비밀번호 입력">
	            <br>
	            <button type="button" class="btn btn-confirm" id="btnConfirm">확인</button>
	            <div class="error-message" id="errorMsg"></div>
	        </div>
	    </div>
	
	    <!-- 2️⃣ 개인정보 표시 영역 (Ajax로 갱신됨) -->
	    <div id="info-section" style="display:none;">
		    <table class="info-table" id="customerInfoTable"></table>
		
		    <div class="btn-row">
		        <button type="button" class="btn btn-edit" id="btnEdit">수정하기</button>
		        <button type="button" class="btn btn-withdraw" id="btnWithdraw">회원탈퇴</button>
		    </div>
		</div>
	    
	</div>
	
	 <!-- ✅ 회원탈퇴 모달 -->
		<div class="modal-overlay" id="withdrawModalOverlay">
		    <div class="modal" id="withdrawModal">
		        <h3>회원 탈퇴</h3>
		        <label>아이디</label>
		        <input type="text" id="outId" readonly>
		        <label>탈퇴 사유</label>
		        <textarea id="outMemo" rows="4" placeholder="탈퇴 사유를 입력하세요."></textarea>
		        <div style="text-align:center;">
		            <button type="button" class="btn btn-cancel-modal" id="btnCancelWithdraw">취소</button>
		            <button type="button" class="btn btn-withdraw" id="btnConfirmWithdraw">탈퇴</button>
		        </div>
		    </div>
		</div>
</body>
<script>
$(document).ready(function() {
    // ✅ 비밀번호 확인 버튼 클릭
    $('#btnConfirm').click(function() {
        const pw = $('#customerPw').val().trim();
        if (pw === '') {
            $('#errorMsg').text('비밀번호를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/customer/manage/customerInfoAccessValidate',
            type: 'POST',
            data: { customerPw: pw },
            dataType: 'json',
            success: function(response) {
                if (response.result === 'SUCCESS') {
                    $('#password-section').hide();
                    loadCustomerInfo(); // 개인정보 불러오기
                } else {
                    $('#errorMsg').text('비밀번호가 일치하지 않습니다.');
                }
            },
            error: function() {
                $('#errorMsg').text('서버 오류가 발생했습니다.');
            }
        });
    });

    // ✅ 개인정보 조회 함수
    function loadCustomerInfo(editMode = false) {
    $.ajax({
        url: '${pageContext.request.contextPath}/customer/manage/customerInfo',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            const info = data[0];
            if (!info) {
                alert('개인정보를 불러올 수 없습니다.');
                return;
            }

            let html = '';

            if (!editMode) {
                // ✅ 보기 모드
                html = `
                    <tr><td>이름</td><td><input type="text" value="\${info.customerName}" readonly></td></tr>
                    <tr><td>아이디</td><td><input type="text" value="\${info.customerId}" readonly></td></tr>
                    <tr><td>주소</td><td><input type="text" value="\${info.address}" readonly></td></tr>
                    <tr><td>전화번호</td><td><input type="text" value="\${info.customerPhone}" readonly></td></tr>
                    <tr><td>가입일자</td><td><input type="text" value="\${info.createdate}" readonly></td></tr>
                `;
                $('#customerInfoTable').html(html);
                $('#info-section').fadeIn();

                // 보기 모드 버튼 구성
                $('.btn-row').html(`
                    <button type="button" class="btn btn-edit" id="btnEdit">수정하기</button>
                    <button type="button" class="btn btn-withdraw" id="btnWithdraw">회원탈퇴</button>
                `);
            } else {
                // ✅ 수정 모드
                html = `
                    <tr><td>이름</td><td><input type="text" value="\${info.customerName}" readonly></td></tr>
                    <tr><td>아이디</td><td><input type="text" value="\${info.customerId}" readonly></td></tr>
                    <tr><td>비밀번호</td><td><input type="password" id="editPw" value="\${info.customerPw}"></td></tr>
                    <tr><td>주소</td><td><input type="text" id="editAddress" value="\${info.address}"></td></tr>
                    <tr><td>전화번호</td><td><input type="text" id="editPhone" value="\${info.customerPhone}"></td></tr>
                    <tr><td>가입일자</td><td><input type="text" value="\${info.createdate}" readonly></td></tr>
                `;
                $('#customerInfoTable').html(html);

                // 수정 모드 버튼 구성 (회원탈퇴 X)
                $('.btn-row').html(`
                    <button type="button" class="btn btn-save" id="btnSave">수정 완료</button>
                    <button type="button" class="btn btn-cancel" id="btnCancel">취소</button>
                `);
            }
        },
        error: function() {
            alert('개인정보를 불러오는 중 오류가 발생했습니다.');
     	}
  	  });
	}

	 // ✅ 수정 버튼 클릭 시 — 수정 모드로 전환 (동적 요소에도 적용됨)
    $(document).on('click', '#btnEdit', function() {
        loadCustomerInfo(true);
    });

    // ✅ 저장 버튼 클릭 시 — 변경사항 서버로 전송
    $(document).on('click', '#btnSave', function() {
        const pw = $('#editPw').val().trim();
        const address = $('#editAddress').val().trim();
        const phone = $('#editPhone').val().trim();

        if (address === '' || phone === '') {
            alert('주소와 전화번호를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/customer/manage/customerInfoUpdate',
            type: 'POST',
            data: {
                customerPw: pw,
                address: address,
                customerPhone: phone
            },
            dataType: 'json',
            success: function(result) {
                if (result.result === 'SUCCESS') {
                    alert('개인정보가 수정되었습니다.');
                    loadCustomerInfo(false); // 다시 보기 모드로 갱신
                } else {
                    alert('수정 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('서버 오류가 발생했습니다.');
            }
        });
    });

    // ✅ 취소 버튼 클릭 시 — 다시 보기 모드로 복귀
    $(document).on('click', '#btnCancel', function() {
        loadCustomerInfo(false);
    });
    
    
    // 회원탈퇴
    
 	// ✅ 회원탈퇴 버튼 클릭 → 모달 열기
    $(document).on('click', '#btnWithdraw', function() {
        // 현재 표시 중인 고객 아이디 가져오기
        const idVal = $('#customerInfoTable input[readonly][value]').eq(1).val(); // 두 번째 readonly input(아이디)
        $('#outId').val(idVal);
        $('#outMemo').val('');
        $('#withdrawModalOverlay').fadeIn();
    });

    // ✅ 모달 닫기
    $('#btnCancelWithdraw').click(function() {
        $('#withdrawModalOverlay').fadeOut();
    });

    // ✅ 회원탈퇴 Ajax 처리
    $('#btnConfirmWithdraw').click(function() {
        const outId = $('#outId').val();
        const memo = $('#outMemo').val().trim();

        if (memo === '') {
            alert('탈퇴 사유를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/customer/manage/customerWithdraw',
            type: 'POST',
            data: { outId: outId, memo: memo },
            dataType: 'json',
            success: function(res) {
                if (res.result === 'SUCCESS') {
                    alert('회원 탈퇴가 완료되었습니다.');
                    location.href = '${pageContext.request.contextPath}/home';
                } else {
                    alert('탈퇴 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                alert('서버 오류가 발생했습니다.');
            }
        });
    });
});
</script>
</html>
