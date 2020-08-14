<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Join</title>
  </head>
  <body>

<jsp:include page="/menu.jsp" /><br>

<h3>登録情報を入力してください。</h3>


<form action="/hotelReservation/AccountServlet?action=join" method="post">
<table border="1">
	<tr>
	<td>姓</td><td><input type="text" name="familyName"></td>
	</tr>
	<tr>
	<td>名</td><td><input type="text" name="firstName"></td>
	</tr>
	<tr>
	<td>郵便番号(ハイフンなし)</td><td><input type="text" name="postal"></td>
	</tr>
	<tr>
	<td>住所</td><td><input type="text" name="address"></td>
	</tr>
	<tr>
	<td>電話番号(ハイフンなし)</td><td><input type="text" name="tel"></td>
	</tr>
	<tr>
	<td>メールアドレス(任意)</td><td><input type="text" name="email"></td>
	</tr>
	<tr>
	<td>パスワード(半角英数字8〜12文字以内)</td><td><input type="password" name="password"></td>
	</tr>

 </table>
 <input type="submit" value="確認画面へ">
</form>



</body>
</html>
