<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.jweb.board.Board" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	}

	//자료 수집
	int bnum = 0;
	if(request.getParameter("bnum") !=null){
		bnum = Integer.parseInt(request.getParameter("bnum"));
	}
	
	//dao - getOnBoard(bnum) 호출
	Board board = boardDAO.getOneBoard(bnum);
	
	//dao - 조회수 1증가
	boardDAO.updateHit(bnum);

%>
<body>
<div class="snowflakes" aria-hidden="true"> <div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div><div class="snowflake">❄</div> <style> .snowflake { color: #fff; font-size: 1em; font-family: Arial; text-shadow: 0 0 1px #000; } @-webkit-keyframes snowflakes-fall{0%{top:-10%}100%{top:100%}}@-webkit-keyframes snowflakes-shake{0%{-webkit-transform:translateX(0px);transform:translateX(0px)}50%{-webkit-transform:translateX(80px);transform:translateX(80px)}100%{-webkit-transform:translateX(0px);transform:translateX(0px)}}@keyframes snowflakes-fall{0%{top:-10%}100%{top:100%}}@keyframes snowflakes-shake{0%{transform:translateX(0px)}50%{transform:translateX(80px)}100%{transform:translateX(0px)}}.snowflake{position:fixed;top:-10%;z-index:9999;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;cursor:default;-webkit-animation-name:snowflakes-fall,snowflakes-shake;-webkit-animation-duration:10s,3s;-webkit-animation-timing-function:linear,ease-in-out;-webkit-animation-iteration-count:infinite,infinite;-webkit-animation-play-state:running,running;animation-name:snowflakes-fall,snowflakes-shake;animation-duration:10s,3s;animation-timing-function:linear,ease-in-out;animation-iteration-count:infinite,infinite;animation-play-state:running,running}.snowflake:nth-of-type(0){left:1%;-webkit-animation-delay:0s,0s;animation-delay:0s,0s}.snowflake:nth-of-type(1){left:10%;-webkit-animation-delay:1s,1s;animation-delay:1s,1s}.snowflake:nth-of-type(2){left:20%;-webkit-animation-delay:6s,.5s;animation-delay:6s,.5s}.snowflake:nth-of-type(3){left:30%;-webkit-animation-delay:4s,2s;animation-delay:4s,2s}.snowflake:nth-of-type(4){left:40%;-webkit-animation-delay:2s,2s;animation-delay:2s,2s}.snowflake:nth-of-type(5){left:50%;-webkit-animation-delay:8s,3s;animation-delay:8s,3s}.snowflake:nth-of-type(6){left:60%;-webkit-animation-delay:6s,2s;animation-delay:6s,2s}.snowflake:nth-of-type(7){left:70%;-webkit-animation-delay:2.5s,1s;animation-delay:2.5s,1s}.snowflake:nth-of-type(8){left:80%;-webkit-animation-delay:1s,0s;animation-delay:1s,0s}.snowflake:nth-of-type(9){left:90%;-webkit-animation-delay:3s,1.5s;animation-delay:3s,1.5s} </style> </div>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>글 상세 보기</h1>
		</div>
		<form action="boardUpdate.do?bnum=${board.bnum}" method="post">
			<table id="table3">
				<tr>
				    <td>글 번호</td>
					<td><input type="text" name="bnum" value="${board.bnum}" 
					           class="input" disabled></td>
				</tr>
				<tr>
				    <td>글 제목</td>
					<td><input type="text" name="title" value="${board.title }" 
					           class="input"></td>
				</tr>
				<tr>
				    <td>작성자</td>
					<td><input type="text" name="memberId" value="${board.memberId}" 
					           class="input" disabled></td>
				</tr>
				<tr>
				    <td>글 내용</td>
					<td><textarea name="content">${board.content}</textarea></td>
				</tr>
				<tr>
				    <td>등록일</td>
					<td><input type="text" name="regDate" value="${board.regDate}" 
					           class="input" disabled></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="boardList.do"><input type="button" value="목록"></a>
						<%-- <% if(sessionId != null && sessionId.equals(board.getMemberId())){ %> --%>
						<c:if test="${sessionId==board.memberId}">
							<a href="boardUpdate.do?bnum=${board.bnum}">
							   <input type="submit" value="수정">
							</a>
							<a onclick="return confirm('정말로 삭제하시겠습니까?')"
							   href="boardDelete.do?bnum=${board.bnum}">
							   <input type="button" value="삭제">
							</a>
						</c:if>
						<%-- <%} %> --%>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="disqus_thread"></div>
	<script>
    /**
    *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
    *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables    */
    /*
    var disqus_config = function () {
    this.page.url = PAGE_URL;  // Replace PAGE_URL with your page's canonical URL variable
    this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };
    */
    (function() { // DON'T EDIT BELOW THIS LINE
    var d = document, s = d.createElement('script');
    s.src = 'https://pescatorio.disqus.com/embed.js';
    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
    })();
	</script>
	<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
</body>
</html>