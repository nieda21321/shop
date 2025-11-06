<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송지 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addressList.css">
</head>

<body>
<div class="page-container">
    <h1 class="page-title">배송지 관리</h1>

    <%-- 고객 메뉴 --%>
    <c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>

    <!-- 상단 컨트롤 -->
    <div class="top-controls">
       <button type="button" id="openModalBtn" class="btn btn-add">주소 추가</button>
        <div class="pagination-controls">
            <button type="button" id="prevBtn" class="btn btn-page">이전</button>
            <span class="page-info">${currentPage} / ${lastPage}</span>
            <button type="button" id="nextBtn" class="btn btn-page">다음</button>
        </div>
    </div>

    <!-- 주소 리스트 -->
    <table class="styled-table">
        <thead>
            <tr>
                <th>주소</th>
                <th>등록 날짜</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="addr" items="${addressList}">
                <tr>
                    <td>${addr.address}</td>
                    <td>${addr.createdate}</td>
                </tr>
            </c:forEach>

            <c:if test="${empty addressList}">
                <tr>
                    <td colspan="2" class="no-data">등록된 주소가 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<!-- 주소 추가 모달(초기 숨김) -->
<div id="addressModal" class="modal-overlay" aria-hidden="true" role="dialog" aria-modal="true">
    <div class="modal-container" role="document">
        <h2 class="modal-title">배송지 추가</h2>

        <!-- 각 input에 고유 name 부여 -->
        <form id="addAddressForm" action="${pageContext.request.contextPath}/customer/addAddress" method="post">
            <input type="text" name="postcode" id="sample4_postcode" placeholder="우편번호" readonly>
            <input type="button" id="btnFindPostcode" value="우편번호 찾기" onclick="sample4_execDaumPostcode()"><br>

            <input type="text" name="roadAddress" id="sample4_roadAddress" placeholder="도로명주소" readonly>
            <input type="text" name="jibunAddress" id="sample4_jibunAddress" placeholder="지번주소" readonly>
            <span id="guide" style="color:#999;display:none"></span>

            <input type="text" name="detailAddress" id="sample4_detailAddress" placeholder="상세주소">
            <input type="text" name="extraAddress" id="sample4_extraAddress" placeholder="참고항목">

            <div class="modal-buttons">
                <button type="submit" class="btn btn-confirm">등록</button>
                <button type="button" id="cancelModalBtn" class="btn btn-cancel">취소</button>
            </div>
        </form>
    </div>
</div>

<!-- 스크립트: 모달 제어 + 페이징 + 카카오 우편 API -->
<script>
$(function() {
    // 페이징
    $('#prevBtn').on('click', function() {
        let currentPage = parseInt('${currentPage}');
        if (currentPage > 1) {
            location.href = '${pageContext.request.contextPath}/customer/addressList?currentPage=' + (currentPage - 1);
        } else {
            alert('첫 페이지입니다.');
        }
    });

    $('#nextBtn').on('click', function() {
        let currentPage = parseInt('${currentPage}');
        let lastPage = parseInt('${lastPage}');
        if (currentPage < lastPage) {
            location.href = '${pageContext.request.contextPath}/customer/addressList?currentPage=' + (currentPage + 1);
        } else {
            alert('마지막 페이지입니다.');
        }
    });

 	// 모달 열기
    $('#openModalBtn').on('click', function(e) {
        e.preventDefault();
        $('html').addClass('modal-open'); // 스크롤 잠금
        $('#addressModal')
            .css('display', 'flex')       // ✅ 여기서 flex로 표시
            .hide()
            .fadeIn(180);                 // ✅ 부드럽게 등장
        $('#sample4_postcode').focus();   // 포커스 이동
    });
 
    // 모달 닫기 (취소 버튼)
    $('#cancelModalBtn').on('click', function(e) {
        e.preventDefault();
        $('#addressModal').fadeOut(160, function(){
            $('html').removeClass('modal-open');
            $('#addressModal').attr('aria-hidden','true');
            $('#openModalBtn').focus();
            $('#addAddressForm')[0].reset();
            $('#guide').hide();
        });
    });

    // overlay 클릭 -> 닫기 (overlay 자체를 클릭했을 때만)
    $('#addressModal').on('click', function(e) {
        if (e.target === this) {
            $('#cancelModalBtn').trigger('click');
        }
    });

    // ESC로 닫기
    $(document).on('keydown', function(e) {
        if (e.key === 'Escape' && $('#addressModal').is(':visible')) {
            $('#cancelModalBtn').trigger('click');
        }
    });

    // (옵션) 폼 제출 시 기본 동작: 서버로 POST (현재는 기본 submit 유지)
    // 만약 Ajax로 처리하려면 여기에서 preventDefault 후 $.ajax 호출로 바꾸면 됩니다.
});
</script>

<!-- 카카오 우편 API 함수 (원본 그대로, id/name은 바뀜) -->
<script>
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var roadAddr = data.roadAddress;
            var extraRoadAddr = '';

            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            $('#sample4_postcode').val(data.zonecode);
            $('#sample4_roadAddress').val(roadAddr);
            $('#sample4_jibunAddress').val(data.jibunAddress);
            $('#sample4_extraAddress').val(roadAddr ? extraRoadAddr : '');

            var guideTextBox = $('#guide');
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.text('(예상 도로명 주소 : ' + expRoadAddr + ')').show();
            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.text('(예상 지번 주소 : ' + expJibunAddr + ')').show();
            } else {
                guideTextBox.hide();
            }

            // 상세주소 입력으로 포커스 이동
            $('#sample4_detailAddress').focus();
        }
    }).open();
}
</script>

</body>
</html>
