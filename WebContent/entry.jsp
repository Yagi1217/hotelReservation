<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約内容入力画面</title>
</head>
<body>

<jsp:include page="/menu.jsp" /><br>->

	<h3>予約する部屋の数を入力してください</h3>
	<form action="/hotelReservation/reservedServlet?action=check" method="post">
			ホテル名：<b>${plan.hotelName}</b><br>
			プラン名：<b>${plan.planName}</b><br>
			住所：<b>${plan.hotelAddress}</b><br>			
			部屋タイプ：<b>${plan.roomType}</b><br>
			金額：<b>${plan.planPrice}</b><br>
			<!-- 部屋数入力 -->
			部屋数：<input type="text" name="quantity" size="6" pattern="^[0-9]+$" title="部屋数は数字で入力して下さい。"  required>
			<br><br>
		<input type="submit" value="次へ">
	</form>
</body>
</html>


