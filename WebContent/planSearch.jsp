<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan Search Page</title>
</head>
<body>
<jsp:include page="/form.jsp" />

<c:if test="${not empty Plan}"><table border="1">
<tr>
	<td>プラン名</td>
	<td>宿名</td>
	<td>住所</td>
	<td>部屋タイプ</td>
	<td>単価(1部屋)</td>
	<td></td>
</tr>
<c:forEach items="${Plan}" var="Plans">
<tr>
	<td> ${Plans.planName} </td>
	<td> ${Plans.hotelName} </td>
	<td> ${Plans.hotelAddress} </td>
	<td align="center"> ${Plans.roomType} </td>
	<td align="right"> <fmt:formatNumber value="${Plans.planPrice}" pattern="###,###" /> 円</td>
	<td>
		<form action="/hotelReservation/PlanSearchServlet?action=entry" method="post">
		<input type="hidden" name="hotelAddress" value="${Plans.hotelAddress}">
		<input type="hidden" name="checkInDate" value="${checkIn}">
		<input type="hidden" name="checkOutDate" value="${checkOut}">
		<input type="hidden" name="roomType" value="${Plans.roomType}">
		<input type="hidden" name="hotelName" value="${Plans.hotelName}">
		<input type="hidden" name="planId" value="${Plans.planId}">
		<input type="hidden" name="hotelId" value="${Plans.hotelId}">
		<input type="hidden" name="planName" value="${Plans.planName}">
		<input type="hidden" name="planPrice" value="${Plans.planPrice}">
		<input type="hidden" name="roomQuantity" value="${Plans.roomQuantity}">
		<input type="submit" value="予約"></form>
	</td>
</tr>
</c:forEach>
</table> </c:if>

</body>
</html>
