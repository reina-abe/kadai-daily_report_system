<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>日報管理システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/' />">日報管理システム</a></h1>&nbsp;&nbsp;&nbsp;
                    <c:if test="${sessionScope.login_employee != null}">
                        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                            <a href="<c:url value='/employees/index' />">従業員管理</a>&nbsp;
                        </c:if>
                        <a href="<c:url value='/attendance/button' />">出退勤</a>&nbsp;
                        <a href="<c:url value='/attendance/report' />">勤怠表</a>&nbsp;
                        <c:if test="${sessionScope.login_employee.position != 0}">
                        <a href="<c:url value='/attendance/employees' />">従業員勤怠</a>&nbsp;
                        </c:if>
                        <a href="<c:url value='/reports/index' />">日報管理</a>&nbsp;
                        <a href="<c:url value='/reports/timeline' />">タイムライン</a>&nbsp;
                        <a href="<c:url value='/reports/following' />">フォロー</a>&nbsp;
                        <a href="<c:url value='/reports/followed' />">フォロワー</a>&nbsp;
                        <c:if test="${sessionScope.login_employee.position != 0}">
                        <a href="<c:url value='/reports/unapproved' />">未承認一覧</a>&nbsp;
                        </c:if>
                    </c:if>
                </div>
                <c:if test="${sessionScope.login_employee != null}">
                    <div id="employee_name">
                        <c:out value="${sessionScope.login_employee.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="content">
                ${param.content}
            </div>
        </div>
    </body>
</html>