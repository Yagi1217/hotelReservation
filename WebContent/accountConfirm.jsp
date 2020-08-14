<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Confirm</title>
  </head>
  <body>

  <jsp:include page="/menu.jsp" /><br>
  <h3>以下の内容で登録を行いますか</h3>

<form action="/hotelReservation/AccountServlet?action=register" method="post">
<table border="1">
	<tr>
	<td>姓</td><td>${familyName} </td>
	</tr>
	<tr>
	<td>名</td><td>${firstName} </td>
	</tr>
	<tr>
	<td>郵便番号</td><td>${postal} </td>
	</tr>
	<tr>
	<td>住所</td><td>${address} </td>
	</tr>
	<tr>
	<td>電話番号</td><td>${tel} </td>
	</tr>
	<tr>
	<td>メールアドレス</td><td>${email} </td>
	</tr>
	<tr>
	<td>パスワード</td><td>●●●●●●●●</td>
	</tr>
	
 </table>

  <input type="submit" value="登録">
  
  </form>




  </body>
  </html>
