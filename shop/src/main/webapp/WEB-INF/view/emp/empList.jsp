<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/empList.css">
</head>
<script>
$(function() {

    // ✅ 사원 추가 버튼 클릭 시 이동
    $('#addEmpBtn').click(function() {
        
    	window.location.href = '${pageContext.request.contextPath}/emp/addEmp';
    });

    // ✅ 이전 페이지 이동
    $('#prevBtn').click(function() {
        
    	let currentPage = parseInt('${currentPage}');
       
        if (currentPage > 1) {
          
        	location.href = '${pageContext.request.contextPath}/emp/empList?currentPage=' + (currentPage - 1);
        } else {
           
        	alert("첫 페이지입니다.");
        }
    });

    // ✅ 다음 페이지 이동
    $('#nextBtn').click(function() {
       
    	let currentPage = parseInt('${currentPage}');
        
    	let lastPage = parseInt('${lastPage}');
        
    	if (currentPage < lastPage) {
           
    		location.href = '${pageContext.request.contextPath}/emp/empList?currentPage=' + (currentPage + 1);
        } else {
           
        	alert("마지막 페이지입니다.");
        }
    });

    // ✅ 활성화/비활성화 버튼 토글
    $('.toggle-active').click(function() {
       
    	let btn = $(this);
        let empCode = btn.data('empcode');
        let active = btn.data('active');

        // 비동기 요청
        $.ajax({
            url: '${pageContext.request.contextPath}/emp/modifyEmpActive',
            type: 'POST',
            data: { empCode: empCode, active: active },
            success: function(response) {
                // 상태 토글
                if (active == 1) {
                 
                	btn.text('미사용');
                    btn.removeClass('btn-active').addClass('btn-inactive');
                    btn.data('active', 0);
                } else {
                   
                	btn.text('사용중');
                    btn.removeClass('btn-inactive').addClass('btn-active');
                    btn.data('active', 1);
                }
            },
          
            error: function() {
                alert('상태 변경 중 오류가 발생했습니다.');
            }
        });
    });

});

</script>
<body>
    <h1>사원 리스트</h1>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>

    <div class="top-controls">
        <button type="button" id="addEmpBtn" class="btn btn-add">사원 추가</button>
        <div>
            <button type="button" id="prevBtn" class="btn btn-page">이전</button>
            <span class="page-info">${currentPage} / ${lastPage}</span>
            <button type="button" id="nextBtn" class="btn btn-page">다음</button>
        </div>
    </div>

    <table>
        <tr>
            <th>empCode</th>
            <th>empId</th>
            <th>empName</th>
            <th>createdate</th>
            <th>사용상태</th>
        </tr>

        <c:forEach var="e" items="${empList}">
            <tr>
                <td>${e.empCode}</td>
                <td>${e.empId}</td>
                <td>${e.empName}</td>
                <td>${e.createdate}</td>
                <td>
                    <button type="button" 
                            class="btn ${e.active == 1 ? 'btn-active' : 'btn-inactive'} toggle-active"
                            data-empcode="${e.empCode}" 
                            data-active="${e.active}">
                        ${e.active == 1 ? '사용중' : '미사용'}
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
