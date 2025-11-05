<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/outIdList.css">
</head>
<script>
$(function() {

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
    
});
</script>
<body>
	<h1>탈퇴 회원 리스트</h1>
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
            <th>탈퇴 아이디</th>
            <th>탈퇴 사유</th>
            <th>탈퇴 일자</th>
        </tr>

        <c:forEach var="o" items="${outIdList}">
            <tr>
                <td>${o.id}</td>
                <td>${o.memo}</td>
                <td>${o.createdate}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>