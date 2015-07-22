<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
  <script src="<c:url value="/resources/js/lecture.js" />"></script>

  <title></title>
</head>
<body>

  <jsp:include page="/top" flush="true"/> <br />

  제목 : <text>${board.title}</text> &nbsp;&nbsp; 작성일 : <text>${board.write_date}</text>
  <br />
  내용 : <br />

  <textarea name="contents" rows="20" cols="40" wrap="hard" readonly="readonly">${board.contents}</textarea><br/>


  <br/>
  <input type="hidden" id="lb_id" value="${board.lb_id}" />
  <input type="hidden" id="lecture_id" value="${board.lecture_id}" />

  <input type="button" id="boardMod_btn" value="수정" /> &nbsp;&nbsp;
  <input type="button" id="boardDel_btn" value="삭제" /> &nbsp;&nbsp;
  <input type="button" id="boardRep_btn" value="신고(${board.report})" /> &nbsp;&nbsp;
  <a href="/lecture/boardList/${board.lecture_id}/1">목록</a>

  <br /><br />


    댓글 <br />

    <c:forEach var="reply" items="${replys}" varStatus="status">

      작성일 : ${reply.write_date} &nbsp;&nbsp; <input type="button" value="삭제" onclick="reDeleteAjax(${reply.lb_id})"> &nbsp;&nbsp; <input type="button" value="신고(${reply.report})" onclick="reReportAjax(${reply.lb_id})">
      <br />
      내용 :
      <br />
      <textarea name="contents" rows="3" cols="40" readonly="readonly">${reply.contents}</textarea><br/>
    </c:forEach>

  <br />

  <form id="re_frm" method="post">

    내용: <br />

    <textarea name="contents" id="re_con" rows="3" cols="40" wrap="hard" placeholder="내용을 입력해주세요"></textarea><br/>

    <input type="hidden" name="lecture_id" value="${board.lecture_id}"/>
    <input type="hidden" name="upper_id" value="${board.lb_id}" />
    <input type="button" id="reReg_btn" value="등록">

  </form>

</body>
</html>
