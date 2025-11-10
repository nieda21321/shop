<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/customerList.css">
</head>
<script>
$(function() {
	
	// ✅ 이전 페이지 이동
    $('#prevBtn').click(function() {
        
    	let currentPage = parseInt('${currentPage}');
       
        if (currentPage > 1) {
          
        	location.href = '${pageContext.request.contextPath}/emp/customer/customerList?currentPage=' + (currentPage - 1);
        } else {
           
        	alert("첫 페이지입니다.");
        }
    });

    // ✅ 다음 페이지 이동
    $('#nextBtn').click(function() {
       
    	let currentPage = parseInt('${currentPage}');
        
    	let lastPage = parseInt('${lastPage}');
        
    	if (currentPage < lastPage) {
           
    		location.href = '${pageContext.request.contextPath}/emp/customer/customerList?currentPage=' + (currentPage + 1);
        } else {
           
        	alert("마지막 페이지입니다.");
        }
    });
    
    // 탈퇴버튼 기능 처리
    $(document).on('click', '.btn-remove', function() {

    	  const id = $(this).data('id');
    	  const name = $(this).data('name');
    	// 여기선 필요하면 전역으로 노출하거나 내부 호출
    	  openRemovePopup(id, name); 
    });


 	// 전역으로 노출할 함수들을 window에 할당
 	// 고객 주문내역 확인
    window.viewCustomerOrder = function(customerCode) {

        location.href = '${pageContext.request.contextPath}/emp/customer/customerOrderListByEmp?customerCode=' + encodeURIComponent(customerCode);
    };

    // 고객 회원탈퇴 처리 팝업창
    window.openRemovePopup = function(customerId, customerName) {

        let popupUrl = '${pageContext.request.contextPath}/emp/customer/removeCustomerByEmp'
            + '?customerId=' + encodeURIComponent(customerId)
            + '&customerName=' + encodeURIComponent(customerName);
        let popupName = "removeCustomer";
        let popupOption = "width=500,height=400,top=200,left=600,scrollbars=no,resizable=no";
        window.open(popupUrl, popupName, popupOption);
    };

});

</script>
<body>
	<h1>고객 리스트</h1>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
    
    <!-- 상단 컨트롤바 -->
	<div class="top-controls">
	    <div class="pagination-controls">
	        <button type="button" id="prevBtn" class="btn btn-page">이전</button>
	        <span class="page-info">${currentPage} / ${lastPage}</span>
	        <button type="button" id="nextBtn" class="btn btn-page">다음</button>
	    </div>
	</div>
	
	<table>
        <tr>
            <th>고객 코드</th>
            <th>계정</th>
            <th>계정 비밀번호</th>
            <th>고객명</th>
            <th>주소</th>
            <th>연락처</th>
            <th>포인트</th>
            <th>가입날짜</th>
            <th>상품주문내역</th>
            <th>계정탈퇴처리</th>
        </tr>

        <c:forEach var="c" items="${customerList}">
            <tr>
                <td>${c.customerCode}</td>
                <td>${c.customerId}</td>
                <td>${c.customerPw}</td>
                <td>${c.customerName}</td>
                <td>${c.address}</td>
                <td>${c.customerPhone}</td>
                <td>${c.point}</td>
                <td>${c.createdate}</td>
                <td>
                    <button type="button" class="btn-order" 
                        onclick="viewCustomerOrder(${c.customerCode})">
                        주문내역
                    </button>
                </td>
                <td>
                    <button type="button" class="btn-remove" 
                    data-id="${c.customerId}" data-name="${c.customerName}">탈퇴</button>
                </td>
            </tr>
        </c:forEach>
    </table>
	

</body>
</html>