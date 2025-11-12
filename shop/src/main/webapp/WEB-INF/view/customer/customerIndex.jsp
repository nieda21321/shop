<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shop - 고객 메인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/customerIndex.css">
</head>
<body>
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>

	<section class="welcome-section">
		<div class="welcome-box">
			<h2>${loginCustomer.customerName}님 반갑습니다 😊</h2>
			<p>현재 보유 포인트: <strong>${loginCustomer.point}</strong>점</p>
			<a href="${pageContext.request.contextPath}/customer/customerLogout" class="logout-link">로그아웃</a>
		</div>
	</section>

	<section class="best-section">
		<h2>🔥 베스트 상품</h2>
		<div class="goods-grid">
			<c:forEach var="bm" items="${customerBestGoodsList}">
				<article class="goods-card">
					<a href="${pageContext.request.contextPath}/customer/goods/goodsOne?goodsCode=${bm.goodsCode}">
						<img src="${pageContext.request.contextPath}/upload/${bm.filename}" alt="${bm.goodsName}">
						<h3>${bm.goodsName}</h3>
						<p class="price">${bm.goodsPrice}원</p>
					</a>
				</article>
			</c:forEach>
		</div>
	</section>

	<section class="goods-section">
	<h2>🛍 전체 상품 목록</h2>

	<div class="goods-table-wrap">
		<table class="goods-table">
			<tr>
				<c:forEach var="m" items="${customerGoodsList}" varStatus="state" end="19">
					<td class="goods-cell">
						<div class="goods-box">
							<a href="${pageContext.request.contextPath}/customer/goods/goodsOne?goodsCode=${m.goodsCode}">
								<div class="goods-img">
									<img src="${pageContext.request.contextPath}/upload/${m.filename}" alt="${m.goodsName}">
								</div>
								<div class="goods-info">
									<p class="goods-name">${m.goodsName}</p>
									<p class="goods-price">${m.goodsPrice}원</p>
								</div>
							</a>
						</div>
					</td>

					<c:if test="${!state.last && state.count % 5 == 0}">
						</tr><tr>
					</c:if>
				</c:forEach>
			</tr>
		</table>
	</div>
	</section>


	<!-- 페이지네이션 -->
	<div class="pagination-controls">
		<%-- 맨처음으로 --%>
        <c:if test="${currentPage > 1}">
            <button type="button" class="btn-page"
                    onclick="location.href='${pageContext.request.contextPath}/customer/customerIndex?currentPage=1'">&laquo; 처음</button>
        </c:if>

		
        <c:if test="${startPage > 1}">
            <button type="button" class="btn-page"
                    onclick="location.href='${pageContext.request.contextPath}/customer/customerIndex?currentPage=${startPage-10}'">이전</button>
        </c:if>

        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <span class="page-current">${i}</span>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn-page"
                            onclick="location.href='${pageContext.request.contextPath}/customer/customerIndex?currentPage=${i}'">${i}</button>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${lastPage > endPage}">
            <button type="button" class="btn-page"
                    onclick="location.href='${pageContext.request.contextPath}/customer/customerIndex?currentPage=${startPage+10}'">다음</button>
        </c:if>
		
		<%-- 맨끝으로 --%>
        <c:if test="${currentPage < lastPage}">
            <button type="button" class="btn-page"
                    onclick="location.href='${pageContext.request.contextPath}/customer/customerIndex?currentPage=${lastPage}'">끝 &raquo;</button>
        </c:if>
    </div>
</body>
</html>
