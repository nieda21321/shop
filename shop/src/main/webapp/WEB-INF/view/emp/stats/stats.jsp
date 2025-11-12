<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.5.0"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>

	<h1></h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
		
	<input type = "hidden" id = "contextPath" value = "${pageContext.request.contextPath}">	
	
	<button type = "button" id ="totalOrderBtn">특정년도의 월별 누적 주문수량 : 선 차트</button>
	<button type = "button" id ="totalPriceBtn">특정년도의 월별 누적 주문금액 : 선 차트</button>
	
	<button type = "button" id ="orderBtn">특정년도의 월별 주문 수량 : 막대 차트</button>
	<button type = "button" id ="">특정년도의 월별 주문 금액 : 막대 차트</button>
	<button type = "button" id ="">고객별 주문횟수 1위 ~ 10위 : 막대 차트</button>
	<button type = "button" id ="">고객별 총금액 1위 ~ 10위 : 막대 차트</button>
	<button type = "button" id ="">상품별 주문횟수 1위 ~ 10위 : 막대 차트</button>
	<button type = "button" id ="">상품별 주문금액 1위 ~ 10위 : 막대 차트</button>
	<button type = "button" id ="">상품별 평균 리뷰 평점 1위 ~ 10위 : 막대 차트</button>
	<button type = "button" id ="genderOrderBtn">성별 총 주문 금액 : 파이 차트</button>
	<button type = "button" id ="">성별 총 주문 수량 : 파이 차트</button>

	<canvas id="myChart" style="width:100%;max-width:700px">
	
	</canvas>


	<script>
		
		let myChart = null;
		
		
		$('#orderBtn').click(function() {
			
			$.ajax({
				url : $('#contextPath').val + "/emp/status/genderOrder"
				,type : 'get'
				,data : {fromYM : '2025-01-01', toYM : '2025-12-31'}
				,success : function(result) {
					
					<%-- <canvas id="myChart" 가 있다면 제거 --%>
					if ( myChart != null ) {

						myChart.destroy();
					}
					
					let xValues = [];
					let yValues = [];
					const barColors = ["red","blue"];
					
					result.forEach(function(m) {

						xValues.push(m.gender);
						yValues.puh(m.cnt);

					});
					
					
					const ctx = document.getElementById('myChart');

					myChart = new Chart(ctx, {
					  type: "pie",
					  data: {
					    labels: xValues,
					    datasets: [{
					      backgroundColor: barColors,
					      data: yValues
					    }]
					  },
					  options: {
					    plugins: {
					      legend: {display: true},
					      title: {
					        display: true,
					        text: "남/여 총 주문량",
					        font: {size: 16}
					      }
					    }
					  }
					});
				}
			});
		});
		
		
		
		$('#orderBtn').click(function() {
			
			$.ajax({
				url : $('#contextPath').val + "/emp/status/order"
				,type : 'get'
				,data : {fromYM : '2025-01-01', toYM : '2025-12-31'}
				,success : function(result) {
					
					<%-- <canvas id="myChart" 가 있다면 제거 --%>
					if ( myChart != null ) {

						myChart.destroy();
					}
					
					let xValues = [];
					let yValues = [];
					const barColors = ["red", "green","blue","orange","brown"];
					
					result.forEach(function(m) {

						xValues.push(m.ym);
						yValues.puh(m.cnt);

					});
					
					
					const ctx = document.getElementById('myChart');

					myChart = new Chart(ctx, {
					  type: "bar",
					  data: {
					    labels: xValues,
					    datasets: [{
					      backgroundColor: barColors,
					      data: yValues
					    }]
					  },
					  options: {
					    plugins: {
					      legend: {display: false},
					      title: {
					        display: true,
					        text: "20250101 ~ 현재 월별 판매량",
					        font: {size: 16}
					      }
					    }
					  }
					});
				}
			});
		});
		
		$('#totalPriceBtn').click(function() {
	
			$.ajax({
				url : $('#contextPath').val + "/emp/status/totalPrice"
				,type : 'get'
				,data : {fromYM : '2025-01-01', toYM : '2025-12-31'}
				,success : function(result) {
					
					let x = [];
					let y = [];
					
					result.forEach(function (e) {
	
						x.push(e.ym);
						y1.push(e.totalPrice);
					});
					
					if ( myChart != null ) {

						myChart.destroy();
					}
					
					myChart = new Chart("myChart", {
					  type: "line",
					  data: {
					    labels: x,
					    datasets: [{
						  label : $('fromYM').val + ' ~ ' + $('toYM').val + '판매량 추이(누적)',
					      data: y,
					      borderColor: "blue",
					      fill: false
					    }]
					  },
					  options: {
					    legend: {display: true}
					  }
					});
				}
			});
		});
	
	
		$('#totalOrderBtn').click(function() {

			$.ajax({
				url : $('#contextPath').val + "/emp/status/totalOrder"
				,type : 'get'
				,data : {fromYM : '2025-01-01', toYM : '2025-12-31'}
				,success : function(result) {
					
					let x = [];
					let y = [];
					
					result.forEach(function (e) {

						x.push(e.ym);
						y1.push(e.totalOrder);
					});
					
					if ( myChart != null ) {

						myChart.destroy();
					}
					
					myChart = new Chart("myChart", {
					  type: "line",
					  data: {
					    labels: x,
					    datasets: [{
						  label : $('fromYM').val + ' ~ ' + $('toYM').val + '주문량 추이(누적)',
					      data: y,
					      borderColor: "red",
					      fill: false
					    }]
					  },
					  options: {
					    legend: {display: true}
					  }
					});
				}
			});
		});
	
	</script>

</body>
</html>