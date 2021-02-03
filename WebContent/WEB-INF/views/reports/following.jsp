<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォローしているユーザー</h2>
        <table id="following">
            <tbody>
                <tr>
                    <th class="followee_name">氏名</th>
                    <th class="follow_delete">解除する</th>
                </tr>
                <c:forEach var="follow_employees" items="${following}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="followee_name"><c:out value="${follow_employees.followee.name}" /></td>
                        <td class="follow_delete"><a href="<c:url value='/reports/destroy?id=${follow_employees.id}' />">フォローを解除する</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${following_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((following_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/reports/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>