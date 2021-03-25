<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to our-site</title>
<link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
	<%-- <%@ page include file="menu.jsp" %> --%>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>Welcome to World Job Friends</h1>
		</div>
		<div>
			<img src="resources/images/friends.png" 
			     width="400" height="300" alt="인공지능 스피커">
			<p>
			<%
				Date day = new Date();
				int hour = day.getHours();
				int minute = day.getMinutes();
				int second = day.getSeconds();
				
				String ct = hour + ":" + minute + ":" + second;
				
				out.println("현재 접속 시각 :" + ct);
				response.setIntHeader("Refresh", 5);
				// 5초에 한번씩 새로 고침
			%>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>