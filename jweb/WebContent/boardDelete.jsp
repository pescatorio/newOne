<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<!-- page, request, session, application -->
<jsp:useBean id="boardDAO" class="com.jweb.board.BoardDAO" scope="application" />
<%
	//자료 수집
	int bnum = Integer.parseInt(request.getParameter("bnum"));
	
	//dao - delete
	boardDAO.deleteBoard(bnum);
	
	//게시판 목록으로 이동
	response.sendRedirect("boardList.jsp");
%>
<body>

</body>
</html>