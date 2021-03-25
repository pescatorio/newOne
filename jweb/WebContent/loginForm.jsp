<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 폼</title>
<link rel="stylesheet" href="resources/css/style.css">
<script type="text/javascript" src="resources/js/check.js"></script>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>로그인</h1>
		</div>
		<form action="loginProcess.do" method="post">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="passwd"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="로그인">
						<input type="reset" value="취소">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>