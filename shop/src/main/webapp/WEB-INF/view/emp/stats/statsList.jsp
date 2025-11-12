<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.5.0"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/statsList.css">
</head>

<body>
	<h1>stats</h1>
	<!-- customer meun include -->
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	<hr>


	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<input type="text" id="fromYM" value="2025-01-01">
	~
	<input type="text" id="toYM" value="2025-12-31">
	
	<br>
	<div class="button-container">
	    <button type="button" id="totalPriceBtn">월별 누적 주문금액</button>
	    <button type="button" id="totalOrderBtn">월별 누적 주문수량</button>
	    <button type="button" id="reviewAvgRankBtn">평균 리뷰평점 TOP10</button>
	    <button type="button" id="goodsPriceRankBtn">상품별 주문금액 TOP10</button>
	    <button type="button" id="customerPriceRankBtn">고객별 총금액 TOP10</button>
	    <button type="button" id="goodsCntRankBtn">상품별 주문횟수 TOP10</button>
	    <button type="button" id="customerCntRankBtn">고객별 주문횟수 TOP10</button>
	    <button type="button" id="genderOrderBtn">성별 총 주문금액</button>
	    <button type="button" id="orderCntByGenderBtn">성별 총 주문수량</button>
	    <button type="button" id="orderPriceByYMBtn">월별 주문금액</button>
	    <button type="button" id="orderCntByYMBtn">월별 주문수량</button>
	</div>
	<canvas id="myChart" style="width:100%;max-width:700px"></canvas>
	
	<script>
	let myChart = null;

	// ====== 1️⃣ 특정년도의 월별 누적 주문금액 : 선 차트 ======
	$('#totalPriceBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderTotalPriceByYM',
	        type: 'get',
	        data: {
	            fromYM: $('#fromYM').val(),
	            toYM: $('#toYM').val()
	        },
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.ym);
	                y.push(m.totalPrice);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "line",
	                data: {
	                    labels: x,
	                    datasets: [{
	                        label: "월별 누적 주문금액",
	                        data: y,
	                        borderColor: "#007bff",
	                        fill: false
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "특정년도 월별 누적 주문금액 추이",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 2️⃣ 특정년도의 월별 누적 주문수량 : 선 차트 ======
	$('#totalOrderBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderTotalCntByYM',
	        type: 'get',
	        data: {
	            fromYM: $('#fromYM').val(),
	            toYM: $('#toYM').val()
	        },
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.ym);
	                y.push(m.totalOrder);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "line",
	                data: {
	                    labels: x,
	                    datasets: [{
	                        label: "월별 누적 주문수량",
	                        data: y,
	                        borderColor: "red",
	                        fill: false
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "특정년도 월별 누적 주문수량 추이",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 3️⃣ 상품별 평균 리뷰 평점 1위 ~ 10위 : 막대 차트 ======
	$('#reviewAvgRankBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderReviewAvgGoodsRank',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.goodsname);
	                y.push(m.avg);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "평균 리뷰 평점",
	                        backgroundColor: "orange",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "상품별 평균 리뷰 평점 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 4️⃣ 성별 총 주문 금액 : 파이 차트 ======
	$('#genderOrderBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderPriceSumByGender',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.gender);
	                y.push(m.totalPrice);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "pie",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "총 주문 금액",
	                        backgroundColor: ["#00aaff", "#ff0077"],
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "성별 총 주문 금액 비율",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 5️⃣ 상품별 주문금액 1위 ~ 10위 : 막대 차트 ======
	$('#goodsPriceRankBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderPriceGoodsRank',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.goodsName);
	                y.push(m.totalPrice);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "상품별 주문금액 순위",
	                        backgroundColor: "#6f42c1",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "상품별 총 주문금액 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 6️⃣ 고객별 총금액 1위 ~ 10위 : 막대 차트 ======
	$('#customerPriceRankBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderPriceCustomerRank',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.customerName);
	                y.push(m.totalPrice);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "고객별 주문 금액 순위",
	                        backgroundColor: "#17a2b8",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "고객별 총 주문금액 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 7️⃣ 특정년도의 월별 주문 금액 : 막대 차트 ======
	$('#orderPriceByYMBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderPriceByYM',
	        type: 'get',
	        data: {
	            fromYM: $('#fromYM').val(),
	            toYM: $('#toYM').val()
	        },
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.ym);
	                y.push(m.totalPrice);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "월별 주문 금액",
	                        backgroundColor: "green",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "특정년도 월별 주문금액",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 8️⃣ 상품별 주문횟수 1위 ~ 10위 : 막대 차트 ======
	$('#goodsCntRankBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderCntGoodsRank',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.goodsName);
	                y.push(m.cnt);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label: "상품별 주문 횟수",
	                        backgroundColor: "#ffc107",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "상품별 주문횟수 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 9️⃣ 고객별 주문횟수 1위 ~ 10위 : 막대 차트 ======
	$('#customerCntRankBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderCntCustomerRank',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.customerName);
	                y.push(m.cnt);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
							label : "고객별 주문 횟수",
	                        backgroundColor: "#28a745",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "고객별 주문횟수 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});


	// ====== 🔟 성별 총 주문 수량 : 파이 차트 ======
	$('#orderCntByGenderBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderCntByGender',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.gender);
	                y.push(m.cnt);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "pie",
	                data: {
	                    labels: x,
	                    datasets: [{
	                    	label : "성별 총 주문 수량",
	                        backgroundColor: ["#ff6384", "#36a2eb"],
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "성별 총 주문 수량 비율",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});
	
	// ====== 11 특정년도의 월별 주문 수량 : 막대 차트 ======
	$('#orderCntByYMBtn').click(function(){
	    $.ajax({
	        url: $('#contextPath').val() + '/emp/stats/orderCntByYM',
	        type: 'get',
	        success: function(result){
	            let x = [], y = [];
	            result.forEach(m => {
	                x.push(m.ym);
	                y.push(m.cnt);
	            });

	            if (myChart) myChart.destroy();
	            myChart = new Chart("myChart", {
	                type: "bar",
	                data: {
	                    labels: x,
	                    datasets: [{
							label : "월별 주문 수량",
	                        backgroundColor: "#28a745",
	                        data: y
	                    }]
	                },
	                options: {
	                    plugins: {
	                        title: {
	                            display: true,
	                            text: "고객별 주문횟수 TOP10",
	                            font: {size: 16}
	                        }
	                    }
	                }
	            });
	        }
	    });
	});

	</script>
</body>
</html>