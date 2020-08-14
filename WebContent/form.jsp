<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>

<jsp:include page="/menu.jsp" /><br>

<%-- チェックイン日の情報が空白なら実行日の日付けを適用する --%>
<c:set var="checkInStr">
	${checkIn}
	<c:if test="${empty checkIn}"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></c:if>
</c:set>

<br>

<table>
	<caption>
<%-- ログイン状態なら、会員IDを表示する。 --%>
	<c:if test="${not empty sessionScope.account}">
  ようこそ　会員ID： ${sessionScope.account} さん<br>
  </c:if>

	検索するプラン情報を設定してください
</caption>
	<thead>
			<form action="/hotelReservation/PlanSearchServlet" method="post">
				<tr>
					<td align="right">プラン名：</td>
					<td><input type="text" name="planNamePartial" value="${planNamePartial}"></td>
				</tr>
				<tr>
					<td align="right">チェックイン日：</td>
					<td><input type="DATE" id="checkIn" name="checkIn" value="${checkInStr}" required></td>
				</tr>
				<tr>
					<td align="right"><span id="checkOutErr">チェックアウト日：</span></td>
					<td><input type="DATE" id="checkOut" onblur="myFnc();" name="checkOut" value="${checkOut}" required></td>
				</tr>
				<tr>
					<td align="right">部屋タイプ：</td>
					<td><select name="roomType">
							<option value="シングル" <c:if test="${roomType == 'シングル'}">selected</c:if>>シングル</option>
							<option value="ダブル" <c:if test="${roomType == 'ダブル'}">selected</c:if>>ダブル</option>
							<option value="ツイン" <c:if test="${roomType == 'ツイン'}">selected</c:if>>ツイン</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input type="submit" value="検索"></td>
				</tr>
			</form>
	</thead>
</table>

<script type="text/javascript">
function myFnc(){
	var checkIn = new Date(document.getElementById('checkIn').value); 
	var checkOut = new Date(document.getElementById('checkOut').value); 
	if (checkIn > checkOut){
		  alert("チェックアウト日はチェックイン日より\n後の日付けを指定してください");
		  document.getElementById('checkOutErr').style.background = '#ffcccc';
		 // document.getElementById('checkOut').select();
		
	}
}
</script>

<hr>