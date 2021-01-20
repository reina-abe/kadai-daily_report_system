<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${histry != null}">
                <h2>日報承認履歴</h2>

                <table id = "approval">
                    <tbody>
                        <tr>
                            <th class="approval_name">承認者</th>
                            <th class="approval_status">承認ステータス</th>
                            <th class="approval_comment">コメント</th>
                            <th class="approval_date">日時</th>
                        </tr>
                        <c:forEach var="approvals" items="${histry}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td class="approval_name"><c:out value="${approvals.employee.name}" /></td>
                            <td class="approval_status">
                            <c:choose>
                            <c:when test="${approvals.approval == 1}"> 承認</c:when>
                            <c:when test="${approvals.approval == 2}">差戻し</c:when>
                            </c:choose></td>
                            <td class="approval_comment"><c:out value="${approvals.comment}" /></td>
                            <td class="approval_date"><fmt:formatDate value="${approvals.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value="/" />">自分の一覧に戻る</a></p>
    </c:param>
</c:import>
