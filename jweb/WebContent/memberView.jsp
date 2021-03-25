<%@page import="com.jweb.member.Member"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 보기</title>
<link rel="stylesheet" href="resources/css/style.css">
</head>
<script type="text/javascript">
	function checkForm(){
		var form = document.updateForm;
		var pw1 = form.passwd.value;
		var pw2 = form.passwd_confirm.value;
		
		if(pw1!=pw2){
			alert("비밀번호를 동일하게 입력해주세요.");
			form.passwd_confirm.focus();
			return false;
		}
		form.submit();
	}



</script>

<body>
	<jsp:include page="menu.jsp" />
	<div id="container">
		<div class="title">
			<h1>상세 보기</h1>
		</div>
		<form action="memberUpdate.do?memberId=${member.memberId}" method="post" 
			name="updateForm">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId" 
					           value="${member.memberId }" disabled>
			    </tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="text" name="passwd" 
					           value="${member.passwd }" >
			    </tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="text" name="passwd_confirm" 
					           placeholder="passwd_confirm">
			    </tr>
				<tr>
					<td>이 름</td>
					<td><input type="text" name="name" 
					           value="${member.name}">
			    </tr>
				<tr>
					<td>성 별</td>
					<td>
				    <c:if test="${member.gender eq '남'}">
					    <input type="radio" name="gender" value="남" checked>남
					    <input type="radio" name="gender" value="여">여
				    </c:if>
				    <c:if test="${member.gender eq '여'}">
					    <input type="radio" name="gender" value="남" >남
					    <input type="radio" name="gender" value="여" checked>여
				    </c:if>
					</td>
			    </tr>
				<tr>
					<td>가입일</td>
					<td><input type="text" name="joinDate" 
					           value="${member.joinDate}" disabled>
			    </tr>
				<tr>
					<td colspan="2">
					    <input type="button" value="수정" onclick="checkForm()">
					    <a onclick="return confirm('정말로 탈퇴하시겠습니까?')" 
					       href="memberDelete.do?memberId=${member.memberId}">
					    	<input type="button" value="탈퇴">
					    </a>
					</td>
			    </tr>
			</table>
		</form>
	</div>
</body>
</html>