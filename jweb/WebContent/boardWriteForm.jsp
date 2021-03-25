<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 양식</title>
<link rel="stylesheet" href="resources/css/style.css">
<script type="text/javascript" src="resources/js/checkBoard.js"></script>
</head>

<body>
	<c:if test="${empty sessionId}">
		<script type="text/javascript">
			alert("로그인이 필요합니다.")
			location.href="loginForm.jsp";
		</script>
	</c:if>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>글쓰기</h1>
		</div>
		<form name="writeForm" action="boardWriteAdd.do" method="post">
			<table id="table2">
				<tr>
					<td><input type="text" name="title" placeholder="글제목"
					           class="w_title"></td>
				</tr>
				<tr>
					<td><textarea name="content" placeholder="글내용"></textarea></td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="button" value="글쓰기" onclick="checkBoard()">
						<input type="reset" value="취소">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>