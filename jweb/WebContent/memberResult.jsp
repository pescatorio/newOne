<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>처리 결과 메시지</title>
<link rel="stylesheet" href="resources/css/style.css">
</head>
<jsp:useBean id="memDAO" class="com.jweb.member.MemberDAO" scope="application" />
<body>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>회원 정보</h1>
		</div>
		<div class="msg">
			<c:choose>
				<c:when test="${loginResult == 0}">
					<script type="text/javascript">
						alert("아이디가 일치하지 않습니다.");
						history.go(-1);
					</script>
				</c:when>
				<c:when test="${loginResult == -1}">
					<script type="text/javascript">
						alert("비밀번호가 일치하지 않습니다.");
						history.go(-1);
					</script>
				</c:when>
				<c:when test="${loginResult == -2}">
					<script type="text/javascript">
						alert("데이터베이스 오류입니다.");
						history.go(-1);
					</script>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${msg eq 'join'}">
					<h1>회원 가입을 축하합니다.</h1>
				</c:when>
				<c:when test="${msg eq 'login'}">
					<h1> <i>${name}</i>님 환영합니다.</h1>
				</c:when>
				<c:when test="${msg eq 'delete' }">
					<h1>회원 정보가 삭제되었습니다.</h1>
				</c:when>
				<c:when test="${msg eq 'update' }">
					<h1>회원 정보가 수정되었습니다.</h1>
				</c:when>
			</c:choose>
		
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>