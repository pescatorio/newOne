<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원</title>
<link rel="stylesheet" href="resources/css/style.css">
</head>
<jsp:useBean id="memDAO" class="com.jweb.member.MemberDAO" scope="application" />
<%
	String sessionId = (String)session.getAttribute("sessionId");
	String name = memDAO.getLoginNameById("sessionId");
%>
<body>
	<nav>
	<c:choose>
		<c:when test="${empty sessionId}">
		<ul>
			<li><a href="main.jsp">Home</a></li>
			<li><a href="loginForm.do">로그인</a></li>
			<li><a href="memberForm.do">회원 가입</a></li>
			<li><a href="boardList.do">게시판</a></li>
		</ul>
		</c:when>
		<c:otherwise>
		<ul>
			<li><a href="main.jsp">Home</a></li>
			<li><a href="logout.do">[${name}님]로그아웃</a></li>
			<li><a href="memberView.do">회원 수정</a></li>
			<li><a href="boardList.do">게시판</a></li>
		</ul>
		</c:otherwise>
	</c:choose>
	</nav>
</body>
</html>