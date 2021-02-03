<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td><c:out value="${report.content}" /></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>いいね数</th>
                            <td><c:choose>
                                    <c:when test="${report.like_count == 0}">
                                        <c:out value="${report.like_count}" />
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/reports/like_employees?id=${report.id}' />">${report.like_count}</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>承認ステータス</th>
                            <td><c:choose>
                                    <c:when test="${report.approval == 0}">承認待ち</c:when>
                                    <c:when test="${report.approval == 1}">承認済み</c:when>
                                    <c:when test="${report.approval == 2}">差戻し</c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br>
                <c:if test="${report.approval != 0 && report.employee.name == login_employee}">
                    <h4>＜承認履歴＞</h4>
                    <table id="approval">
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
                                            <c:when test="${approvals.approval == 1}">承認済み</c:when>
                                            <c:when test="${approvals.approval == 2}">差戻し</c:when>
                                        </c:choose></td>
                                    <td class="approval_comment"><c:out value="${approvals.comment}" /></td>
                                    <td class="approval_date"><fmt:formatDate value="${approvals.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if
                    test="${report.approval == 2 && report.employee.name == login_employee}">
                    <br>
                    <a href="<c:url value='/reports/edit?id=${report.id}' />"><button type="button">編集して再投稿する</button></a>
                </c:if>

                <c:if test="${sessionScope.login_employee.id != report.employee.id && like_check == 0}">
                    <p><a href="<c:url value="/reports/like?id=${report.id}" />">この日報にいいねする</a></p>
                </c:if>

                <c:if test="${sessionScope.login_employee.id != report.employee.id && follow_check == 0}">
                    <p><a href="<c:url value="/reports/follow?id=${report.id}" />">この作成者をフォローする</a></p>
                </c:if>

                <c:if test="${sessionScope.login_employee.id == report.employee.id && report.approval != 1 && report.approval == 0}">
                    <!-- 作成者以外の人が該当の日報を編集できないよう、違う人の日報の場合は edit へのリンクを出さない -->
                    <p><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>

                <c:if test="${sessionScope.login_employee.id != report.employee.id && sessionScope.login_employee.position >= report.employee.position && report.approval == 0}">
                    <form method="POST" action="<c:url value='/reports/approve?id=${report.id}'/>">
                        <label for="comment">承認コメント</label><br />
                        <textarea name="comment" rows="4" cols="40">${approvals.comment}</textarea>
                        <button type="submit">承認する</button>
                    </form>
                    <form method="POST" action="<c:url value='/reports/remand?id=${report.id}'/>">
                        <label for="comment">差戻しコメント</label><br />
                        <textarea name="comment" rows="4" cols="40">${approvals.comment}</textarea>
                        <button type="submit">差戻す</button>
                    </form>
                </c:if>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">日報一覧に戻る</a></p>
    </c:param>
</c:import>
