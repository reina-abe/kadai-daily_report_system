<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${like_employees != null}">
                <h2>「いいね」した人の一覧</h2>

                <table>
                    <tbody>
                        <tr>
                            <th class="like_employees_name">氏名</th>
                            <th class="like_employees_date">日時</th>
                        </tr>
                        <c:forEach var="like_employees" items="${like_employees}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td class="like_employees_name"><c:out value="${like_employees.employee.name}" /></td>
                            <td class="like_employees_date"><fmt:formatDate value="${like_employees.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <div id="pagination">
                 （全 ${like_employees_count} 件）<br />
                 <c:forEach var="i" begin="1" end="${((like_employees_count - 1) / 15) + 1}" step="1">
                     <c:choose>
                         <c:when test="${i == page}">
                             <c:out value="${i}" />&nbsp;
                         </c:when>
                         <c:otherwise>
                             <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                         </c:otherwise>
                     </c:choose>
                 </c:forEach>
        </div>
        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>
