<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Done</title>
  </head>
  <body>

<jsp:include page="/menu.jsp" /><br>

<%--ログイン完了または登録完了のメッセージ--%>

  <h2>${message}</h2>
  <h3>${accountId}</h3>
  <h4>${link}</h4>



  </body>
  </html>
