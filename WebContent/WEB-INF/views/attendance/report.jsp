<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>勤怠管理表</h2>
        <c:if test="${lastMonth != 1 && e != 1}">
        <a href="<c:url value='/attendance/lastmonth' />">＜＜前月</a>
        </c:if>
        <c:if test="${thisMonth != 1 && e != 1}">
        <a href="<c:url value="/attendance/report" />">今月＞＞</a>
        </c:if>
        <table id="report">
            <tbody>
                <tr>
                    <th class="date">日付</th>
                    <th class="start_at">出勤時刻</th>
                    <th class="finish_at">退勤時刻</th>
                    <th class="break_time">休憩時間</th>
                    <th class="work_time">実働時間</th>
                </tr>
                <c:forEach var="attendance" items="${report}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="date"><c:out value="${attendance.date}" /></td>
                        <td class="start_at"><fmt:formatDate value="${attendance.start_at}" type="TIME" pattern="HH：mm" /></td>
                        <td class="finish_at"><c:if test="${attendance.start_at == attendance.finish_at}">00：00</c:if>
                            <c:if test="${attendance.start_at != attendance.finish_at}">
                                <fmt:formatDate value="${attendance.finish_at}" type="TIME" pattern="HH：mm" />
                            </c:if></td>
                        <td class="break_time"><c:out value="${attendance.break_time}" /></td>
                        <td class="work_time"><c:if test="${attendance.start_at == attendance.finish_at}">00：00</c:if>
                            <c:if test="${attendance.start_at != attendance.finish_at}">
                                <c:out value="${attendance.work_time}" />
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>