<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
</head>
<!-- page, request, session, application -->
<jsp:useBean id="board" class="com.jweb.board.Board" />
<jsp:setProperty property="bnum" name="board"/>
<jsp:setProperty property="title" name="board"/>
<jsp:setProperty property="content" name="board"/>
<jsp:useBean id="boardDAO" class="com.jweb.board.BoardDAO" scope="application" />
<%
	//dao - update
	boardDAO.updateBoard(board);
	out.println("<script> alert('글이 수정되었습니다.')");
	out.println("location.href='boardList.jsp' </script>");
%>
<body>

</body>
</html>