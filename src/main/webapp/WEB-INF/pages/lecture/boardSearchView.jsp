<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
  <script src="<c:url value="/resources/js/lecture.js" />"></script>

  <title></title>
</head>
<body>

<jsp:include page="/top" flush="true"/>

<br /><br />

<jsp:include page="/lecture/search_form" flush="true"/>

<br /><br />

<jsp:include page="/evaluation/lecture?lecture_id=${board.lecture_id}" flush="true"/>

<br /><br />

  제목 : <text>${board.title}</text> <br />
  작성일 : <text>${board.write_date}</text> &nbsp; 조회수 : <text>${board.hit}</text>
  <br />
  <br />

  <textarea name="contents" rows="20" cols="50" wrap="hard" readonly="readonly">${board.contents}</textarea><br/>

  <br/>

  <input type="hidden" id="lb_id" value="${board.lb_id}" />
  <input type="hidden" id="lecture_id" value="${board.lecture_id}" />

  <input type="button" id="boardMod_btn" value="수정" /> &nbsp;&nbsp;
  <input type="button" id="boardDel_btn" value="삭제" /> &nbsp;&nbsp;
  <input type="button" id="boardRep_btn" value="신고(${board.report})" /> &nbsp;&nbsp;
  <a href="/lecture/boardList/${board.lecture_id}/">최신목록</a> &nbsp;&nbsp;
  <button onclick="history.back()" >이전페이지로</button>

  <br /><br />

  --------------------------------------------------------<br />

  <c:forEach var="reply" items="${replys}" varStatus="status">

    ${reply.write_date} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="삭제" onclick="reDeleteAjax(${reply.lb_id})"> &nbsp;&nbsp;
    <input type="button" value="신고(${reply.report})" onclick="reReportAjax(${reply.lb_id})">
    <br />
    내용 :
    <br />
    <textarea name="contents" rows="3" cols="50" readonly="readonly">${reply.contents}</textarea><br/><br/>
  </c:forEach>

  <br />

  --------------------------------------------------------<br />

  <form id="re_frm" method="post">

    댓글입력 <br />

    <textarea name="contents" id="re_con" rows="3" cols="50" wrap="hard" placeholder="내용을 입력해주세요"></textarea><br/>

    <input type="hidden" name="lecture_id" value="${board.lecture_id}"/>
    <input type="hidden" name="upper_id" value="${board.lb_id}" />
    <input type="button" id="reReg_btn" value="등록">

  </form>

<br /><br />


<table border="1">
  <tr>
    <td>글번호</td>
    <td>제목</td>
    <td>작성일</td>
    <td>조회수</td>
  </tr>
  <c:forEach var="board" items="${boards}" varStatus="status">
    <tr>
      <td>${board.rnum}</td>
      <td>
        <a href="/lecture/boardView/${board.lecture_id}/?lb_id=${board.lb_id}&page=${paging.pageNo}&searchType=${search.searchType}&searchWord=${search.searchWord}">${board.title}
          <c:choose>
            <c:when test="${board.count == 0}">
            </c:when>
            <c:otherwise>
              [${board.count}]
            </c:otherwise>
          </c:choose>
        </a>
      </td>
      <td>${board.write_date}</td>
      <td>${board.hit}</td>
    </tr>
  </c:forEach>
</table><br /><br />

<a href="/lecture/boardList/${lecture_id}/">전체목록</a>&nbsp;&nbsp;<a href="/lecture/boardRegForm?lecture_id=${lecture_id}">글작성</a>

<br/><br/>

<jsp:include page="/paging" flush="true">
  <jsp:param name="url" value="/lecture/boardList/${lecture_id}/?page=" />
  <jsp:param name="search" value="&searchType=${search.searchType}&searchWord=${search.searchWord}" />
  <jsp:param name="totalCount" value="${paging.totalCount}" />
  <jsp:param name="firstPageNo" value="${paging.firstPageNo}" />
  <jsp:param name="prevPageNo" value="${paging.prevPageNo}" />
  <jsp:param name="startPageNo" value="${paging.startPageNo}" />
  <jsp:param name="pageNo" value="${paging.pageNo}" />
  <jsp:param name="endPageNo" value="${paging.endPageNo}" />
  <jsp:param name="nextPageNo" value="${paging.nextPageNo}" />
  <jsp:param name="finalPageNo" value="${paging.finalPageNo}" />
</jsp:include>

<form action="/lecture/boardList/${lecture_id}/" method="get" name="board_search_frm">

  <select name="searchType">
    <c:choose>
      <c:when test="${search.searchType eq 'contents'}">
        <option value="title">제목</option>
        <option value="contents" selected="selected">내용</option>
        <option value="title_contents">제목+내용</option>
      </c:when>
      <c:when test="${search.searchType eq 'title_contents'}">
        <option value="title">제목</option>
        <option value="contents">내용</option>
        <option value="title_contents" selected="selected">제목+내용</option>
      </c:when>
      <c:otherwise>
        <option value="title" selected="selected">제목</option>
        <option value="contents">내용</option>
        <option value="title_contents">제목+내용</option>
      </c:otherwise>
    </c:choose>
  </select>

  <input type="text" name="searchWord" value="${search.searchWord}">
  <input type="button" value="검색" onclick="boardSearchLecture()">

</form>

현재 페이지 : <c:out value="${paging.pageNo}"/><br />
전체게시글수 : <c:out value="${paging.totalCount}"/><br />
전체시작페이지 : <c:out value="${paging.firstPageNo}"/><br />
페이징시작페이지 : <c:out value="${paging.startPageNo}"/><br />
페이징끝페이지 : <c:out value="${paging.endPageNo}"/><br />
전체끝페이지 : <c:out value="${paging.finalPageNo}"/><br />

</body>
</html>