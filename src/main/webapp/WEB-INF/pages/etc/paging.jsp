<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link rel="stylesheet" href="<c:url value="${ctx}/resources/css/paging.css" />">

</head>

<body>

<div align="center">
    <ul class="pagination">
        <c:if test="${param.totalCount !=0}">

            <c:choose>

                <c:when test="${empty param.search}">

                    <!-- 전체 페이지가 10페이지 이하면 맨처음로 이동 표시 안함-->

                    <c:if test="${param.finalPageNo-10 <= 0}">

                    </c:if>

                    <!-- 전체 페이지가 10페이지 초과고 시작페이지가 1이 아니면 맨처음로 이동 표시 -->

                    <c:if test="${param.finalPageNo-10 > 0 && param.startPageNo-1 !=0}">
                        <li><a href="${param.url}${param.firstPageNo}" class="first">&laquo;맨처음</a></li>
                    </c:if>

                    <!-- 시작페이지가 1부터면 이전 표시("<<") ​ 안함 -->

                    <c:if test="${param.startPageNo-1 ==0 }">

                    </c:if>

                    <!-- 시작페이지가 1이 아니면 << 이전 표시.  링크는 시작페이지가 11부터 20까지일 경우 10페이지를 가르킴 -->​

                    <c:if test="${param.startPageNo-1 !=0 }">
                        <li><a href="${param.url}${param.startPageNo-1}" class="prev">&lt;</a></li>
                    </c:if>

                    <%--                    <!-- 현재 페이지가 1페이지면 이전페이지 표시("<") ​ 안함 -->

                                        <c:if test="${param.pageNo == 1}">

                                        </c:if>

                                        <!-- 현재 페이지가 1페이지가 아니면 이전페이지 표시("<") ​ -->

                                        <c:if test="${param.pageNo != 1}">

                                            <a href="${param.url}${param.prevPageNo}" class="prevPage">&lt;&nbsp;</a>

                                        </c:if>
                    --%>

                    <!-- 10개씩 페이지 표시-->​

                    <c:forEach var="i" begin="${param.startPageNo}" end="${param.endPageNo}" step="1">
                        <c:choose>
                            <c:when test="${i eq param.pageNo}">
                                <li class="active"><a href="${param.url}${i}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${param.url}${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <%--
                                        <!-- 현재 페이지가 마지막페이지면 다음페이지 표시(">") ​ 안함 -->

                                        <c:if test="${param.pageNo == param.finalPageNo}">

                                        </c:if>

                                        <!-- 현재 페이지가 마지막페이지가 아니면 아니면 다음페이지 표시(">") ​ -->

                                        <c:if test="${param.pageNo != param.finalPageNo}">

                                            <a href="${param.url}${param.nextPageNo}" class="nextPage">&nbsp;&gt;</a>

                                        </c:if>
                    --%>

                    <!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 10개 단위가 아니면 다음바로가기 표시 않함 -->​​

                    <c:if test="${param.endPageNo % 10 != 0 && param.endPageNo == param.finalPageNo}">

                    </c:if>

                    <!-- 마지막 페이지 번호가 10, 20 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->​

                    <c:if test="${param.endPageNo % 10 == 0 && param.finalPageNo > param.endPageNo}">

                        <li><a href="${param.url}${param.endPageNo+1}" class="next">&gt;</a></li>

                    </c:if>

                    <!-- 전체 페이지가 10페이지 이하면 맨뒤로 이동 표시 안함 -->

                    <c:if test="${param.finalPageNo-10 <= 0}">

                    </c:if>

                    <!-- 전체 페이지가 10페이지 초과고 마지막 페이지 번호와 전체 페이지 번호가 같지 않으면 맨뒤 이동 표시 -->

                    <c:if test="${param.finalPageNo-10 > 0 && param.endPageNo != param.finalPageNo}">

                        <li><a href="${param.url}${param.finalPageNo}" class="last">맨뒤&raquo;</a></li>

                    </c:if>

                </c:when>
                <c:otherwise>

                    <c:if test="${param.finalPageNo-10 <= 0}">

                    </c:if>

                    <c:if test="${param.finalPageNo-10 > 0 && param.startPageNo-1 !=0}">
                        <li><a href="${param.url}${param.firstPageNo}${param.search}" class="first">&laquo;맨처음</a></li>
                    </c:if>

                    <c:if test="${param.startPageNo-1 ==0 }">

                    </c:if>

                    <c:if test="${param.startPageNo-1 !=0 }">

                        <li><a href="${param.url}${param.startPageNo-1}${param.search}" class="prev">&lt;</a></li>

                    </c:if>

                    <%--
                                        <c:if test="${param.pageNo == 1}">

                                        </c:if>

                                        <c:if test="${param.pageNo != 1}">

                                            <a href="${param.url}${param.prevPageNo}${param.search}" class="prevPage">&lt;&nbsp;</a>

                                        </c:if>
                    --%>

                        <c:forEach var="i" begin="${param.startPageNo}" end="${param.endPageNo}" step="1">
                            <c:choose>
                                <c:when test="${i eq param.pageNo}">
                                    <li class="active"><a href="${param.url}${i}${param.search}">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${param.url}${i}${param.search}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                    <%--                    <c:if test="${param.pageNo == param.finalPageNo}">

                                        </c:if>

                                        <c:if test="${param.pageNo != param.finalPageNo}">

                                            <a href="${param.url}${param.nextPageNo}${param.search}" class="nextPage">&nbsp;&gt;</a>

                                        </c:if>--%>

                    <c:if test="${param.endPageNo % 10 != 0 && param.endPageNo == param.finalPageNo}">

                    </c:if>

                    <c:if test="${param.endPageNo % 10 == 0 && param.finalPageNo > param.endPageNo}">

                        <li><a href="${param.url}${param.endPageNo+1}${param.search}" class="next">&gt;</a></li>

                    </c:if>

                    <c:if test="${param.finalPageNo-10 <= 0}">

                    </c:if>

                    <c:if test="${param.finalPageNo-10 > 0 && param.endPageNo != param.finalPageNo}">

                        <li><a href="${param.url}${param.finalPageNo}${param.search}" class="last">맨뒤&raquo;</a></li>

                    </c:if>

                </c:otherwise>

            </c:choose>

        </c:if>
    </ul>
</div>
</body>
</html>
