<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.jweb.board.Board" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 양식</title>
<link rel="stylesheet" href="resources/css/style.css">
<script type="text/javascript" src="resources/js/check.js"></script>
</head>
<jsp:useBean id="boardDAO" class="com.jweb.board.BoardDAO" 
             scope="application"/> 
<%
	//세션 유지
	String sessionId = null;
	if(session.getAttribute("sessionId") != null){
		sessionId = (String)session.getAttribute("sessionId");
	}else{
		out.println("<script>alert('로그인을 해주세요.')");
		out.println("location.href='loginForm.jsp' </script>");
	}

	//자료 수집
	int bnum = 0;
	if(request.getParameter("bnum") !=null){
		bnum = Integer.parseInt(request.getParameter("bnum"));
	}
	
	//dao - getOnBoard(bnum) 호출
	Board board = boardDAO.getOneBoard(bnum);
%>
<body>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>게시글 수정 화면</h1>
		</div>
		<form action="boardUpdateProcess.jsp?bnum=<%=board.getBnum() %>" method="post">
			<table id="table3">
				<tr>
				    <td class="len">글 번호</td>
					<td><input type="text" name="bnum" value="<%=board.getBnum() %>" 
					           class="input" disabled></td>
				</tr>
				<tr>
				    <td class="len">글 제목</td>
					<td><input type="text" name="title" value="<%=board.getTitle() %>" 
					           class="input"></td>
				</tr>
				<tr>
				    <td class="len">작성자</td>
					<td><input type="text" name="memberId" value="<%=board.getMemberId() %>" 
					           class="input" disabled></td>
				</tr>
				<tr>
				    <td>글 내용</td>
					<td><textarea name="content"><%=board.getContent() %></textarea></td>
				</tr>
				<tr>
				    <td class="len">등록일</td>
					<td><input type="text" name="regDate" value="<%=board.getRegDate() %>" 
					           class="input" disabled></td>
				</tr>
	
				<tr>
					<td colspan="2">
						<a href="boardList.jsp"><input type="button" value="목록"></a>
					    <input type="submit" value="저장">
					    <input type="reset" value="취소">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>