<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>토비랜드</title>
</head>
<body>
	<h1>HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username : ${username} <br/>잘못된 접근입니다.</h2>
		</c:otherwise>
	</c:choose>

	<a href="#" onClick="history.go(-2);">이전페이지로</a>

</body>
</html>