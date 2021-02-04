<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>従業員勤怠管理</h2>
        <table id="employee">
            <tbody>
                <tr>
                    <th class="name">氏名</th>
                    <th class="reports">勤怠</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="name"><c:out value="${employee.name}" /></td>
                        <td class="reports"><a href="<c:url value='/attendance/eachReports?id=${employee.id}' />">この従業員の勤怠表を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${attendance_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((attendance_count - 1) / 15) + 1}" step="1">
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