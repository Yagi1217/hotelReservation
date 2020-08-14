<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約内容確認</title>
</head>
<body>

<jsp:include page="/menu.jsp" /><br>

<h3>下記の内容で予約を行いますか？</h3>
ホテル名：<b>${detail.hotelName}</b><br>
プランID：<b>${detail.planId}</b><br>
プラン名：<b>${detail.planName}</b><br>
部屋数：<b>${detail.reservedCount}</b><br>
チェックイン日付：<b>${detail.checkInDate}</b><br>
チェックアウト日付：<b>${detail.checkOutDate}</b><br>
宿泊日数：<b>${detail.lodgmentDays}</b><br>
合計金額：<b>${detail.price}</b><br>
<br>
<form action="/hotelReservation/reservedServlet?action=order" method="post">

<!-- 
<input type="hidden" name="accountId" value="hogehoge">
<input type="hidden" name="planId" value="hogehoge">
<input type="hidden" name="quantity" value="hogehoge">
<input type="hidden" name="price" value="hogehoge">
<input type="hidden" name="checkInDate" value="hogehoge">
<input type="hidden" name="checkOutDate" value="hogehoge">
 -->
<input type="submit" value="予約する">
</form>

</body>
</html>