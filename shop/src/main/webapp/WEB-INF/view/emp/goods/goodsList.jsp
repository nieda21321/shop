<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 관리</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/goodsList.css">
</head>
<script>
    $(function() {
       
    	//  상품 추가 버튼 클릭 시 이동
        $('#addGoodsBtn').on('click', function() {

            const ctx = '${pageContext.request.contextPath}';
            window.location.href = ctx + '/emp/goods/addGoods';
        });
        
     // ✅ 이전 페이지 이동
        $('#prevBtn').click(function() {
            
        	let currentPage = parseInt('${currentPage}');
           
            if (currentPage > 1) {
              
            	location.href = '${pageContext.request.contextPath}/emp/goods/goodsList?currentPage=' + (currentPage - 1);
            } else {
               
            	alert("첫 페이지입니다.");
            }
        });

        // ✅ 다음 페이지 이동
        $('#nextBtn').click(function() {
           
        	let currentPage = parseInt('${currentPage}');
            
        	let lastPage = parseInt('${lastPage}');
            
        	if (currentPage < lastPage) {
               
        		location.href = '${pageContext.request.contextPath}/emp/goods/goodsList?currentPage=' + (currentPage + 1);
            } else {
               
            	alert("마지막 페이지입니다.");
            }
        });
    });
</script>
<body>

    <div class="page-container">
        <h1 class="page-title">상품 관리</h1>
        <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>

        <!-- 상단 컨트롤바 -->
		<div class="top-controls">
		    <button type="button" id="addGoodsBtn" class="btn btn-add">상품 등록</button>
		
		    <div class="pagination-controls">
		        <button type="button" id="prevBtn" class="btn btn-page">이전</button>
		        <span class="page-info">${currentPage} / ${lastPage}</span>
		        <button type="button" id="nextBtn" class="btn btn-page">다음</button>
		    </div>
		</div>

        <!-- 상품 리스트 테이블 -->
        <div class="table-section">
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>상품이미지</th>
                        <th>상품코드</th>
                        <th>상품명</th>
                        <th>가격</th>
                        <th>남은수량</th>
                        <th>적립률</th>
                        <th>등록일</th>
                        <th>등록사원ID</th>
                        <th>등록사원명</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="g" items="${goodsList}">
                        <tr>
                            <td><img src="${pageContext.request.contextPath}/upload/${g.filename}" alt="상품이미지" class="goods-thumb"></td>
                            <td>${g.goodsCode}</td>
                            <td>${g.goodsName}</td>
                            <td>${g.goodsPrice}원</td>
                            <td>${g.soldout}</td>
                            <td>${g.pointRate}%</td>
                            <td>${g.createdate}</td>
                            <td>${g.empId}</td>
                            <td>${g.empName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>