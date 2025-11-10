<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addGoods.css">
</head>
<script>
$(function() {
    // ✅ 상품 등록 버튼 클릭 시
    $('button[type="button"]').on('click', function() {

        const name = $('input[name="goodsName"]').val().trim();
        const price = $('input[name="goodsPrice"]').val().trim();
        const pointRate = $('input[name="pointRate"]').val().trim();
        const img = $('input[name="goodsImg"]').val().trim();

        if (name === '' || price === '' || pointRate === '' || img === '') {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        // ✅ 확장자 검증
        const allowedExt = ['png', 'jpg', 'jpeg', 'gif'];
        const ext = img.split('.').pop().toLowerCase();

        if (!allowedExt.includes(ext)) {

            alert("이미지 파일은 png, jpg, gif 형식만 업로드 가능합니다.");
            return;
        }

        if (confirm("상품을 등록하시겠습니까?")) {
            $('form').submit();
        }
    });
});
</script>
<body>
	<h1>상품 등록</h1>
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	
	<form enctype="multipart/form-data" action="${pageContext.request.contextPath}/emp/goods/addGoods" method="post">
		<table>
			<tr>
				<td>상품명</td>
				<td><input type="text" name="goodsName"></td>
			</tr>
			<tr>
				<td>상품 가격</td>
				<td><input type="number" name="goodsPrice"></td>
			</tr>
			<tr>
				<td>적립률</td>
				<td><input type="text" name="pointRate"></td>
			</tr>
			<tr>
				<td>상품이미지(png / jpg / gif 확장자)</td>
				<td><input type="file" name="goodsImg"></td>
			</tr>
		</table>
		<button type="button">상품등록</button>
	</form>
</body>
</html>