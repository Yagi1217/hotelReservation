<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Login</title>
  </head>
  <body>

<jsp:include page="/menu.jsp" /><br>

<h3>会員番号とパスワードを入力してください。</h3>
<form action="/hotelReservation/AccountServlet?action=login" method="post">
  会員番号：<input type="text" name="accountId"><br>
  パスワード：<input type="password" name="password"><br>
  <input type="submit" value="ログイン">
</form>



</body>
</html>
