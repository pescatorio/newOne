<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	main world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

vo: ${sampleVo}
voList: ${sampleVOList}
<form id="fileUpload" action="/fileUpload" method="post" enctype="multipart/form-data">
	<input name="file1" type="file" value="파일 선택">
	<input name="file2" type="file" value="파일 선택">
	<input name="file3" type="file" value="파일 선택">
	<input name="file4" type="file" value="파일 선택">
	<input name="file5" type="file" value="파일 선택">
	<input type="submit" value="제출">
</form>

</body>
</html>
