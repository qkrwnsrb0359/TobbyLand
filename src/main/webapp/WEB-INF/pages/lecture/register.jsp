<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 2015-05-31
  Time: 오전 4:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="/lecture/reg" method="post">

  강의명: <input type="text" name="lecture_name"><br>
  학과명: <input type="text" name="dept"><br>
  교수명: <input type="text" name="prof"><br>
          <input type="hidden" name="member_id" value="<s:authentication property="name"/>">
          <input type="submit" value="등록">

</form>
</body>
</html>
