<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%-- 달러표시해줘야함--%>
<link rel="stylesheet" href="{pageContext.request.contextPath}/css/common.css">
</head>
<body>
	<h1></h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	<%-- 공지사항관리 리스트처럼 페이징 만들어주고
		m.put("questionCode", rs.getInt("questionCode"));    
				m.put("customerName", rs.getString("customerName")); 
				m.put("customerId", rs.getString("customerId"));     
				m.put("orderCode", rs.getInt("orderCode"));          
				m.put("goodsName", rs.getString("goodsName"));       
				m.put("category", rs.getString("category"));         
				m.put("questionMemo", rs.getString("questionMemo")); 
				m.put("createdate", rs.getString("createdate"));     
				m.put("commentCode", rs.getInt("commentCode"));      
				m.put("commentMemo", rs.getString("commentMemo")); 
				
				이 데이터를 표출시킬건데 commentCode가 null 일경우 답변하기 버튼을 생성할거고
				해당 답변하기 생성버튼을 클릭하면 /emp/question/updateQuestionComment로 보낼거야
	 --%>
</body>
</html>