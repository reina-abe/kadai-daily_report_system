<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>あなたをフォローしているユーザー</h2>
        <table id="followed">
            <tbody>
                <tr>
                    <th class="followee_name">氏名</th>
                    <th class="follow_status">フォロー状況</th>
                </tr>
                <c:forEach var="follow_employees" items="${followees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="followee_name"><c:out value="${follow_employees.follower.name}" /></td>
                        <td class="follow_status">
                        <c:choose>
                                <c:when test="${follow_check[status.index]==0}">
                                    <p><a href="<c:url value="/reports/followback?id=${follow_employees.id}" />">このユーザーをフォローする</a></p>
                                </c:when>
                                <c:otherwise>
                                    <p>フォロー中</p>
                                </c:otherwise>
                        </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${followees_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((followees_count - 1) / 15) + 1}" step="1">
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