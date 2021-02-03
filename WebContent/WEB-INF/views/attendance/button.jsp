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
        <br><br><br><br>
        <div class="box2">
            <div class="box1">
                <p><fmt:formatDate value="${date}" type="DATE" pattern="yyyy年MM月dd日"/></p>
            </div>
        </div>

        <script type="text/javascript">
            function showClock1() {
                var nowTime = new Date();
                var nowHour = nowTime.getHours();
                var nowMin = nowTime.getMinutes();
                var nowSec = nowTime.getSeconds();
                var msg = nowHour + ":" + nowMin + ":" + nowSec;
                document.getElementById("RealtimeClockArea").innerHTML = msg;
            }
            setInterval('showClock1()', 1000);
        </script>

        <div style="text-align: center;">
            <div style="font-size: 5em">
                <p id="RealtimeClockArea"></p>
            </div>
        </div>

        <div style="text-align: center;">
            <c:if test="${attend == 0 && left == 0}">
                <a href="<c:url value='/attendance/attend' />" class="button1"><h5>出勤</h5></a>
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <a href="" class="button0"><h5>退勤</h5></a>
            </c:if>

            <c:if test="${attend != 0 && left == 0}">
                <a href="" class="button0"><h5>出勤</h5></a>
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <a href="<c:url value='/attendance/leave' />" class="button2"><h5>退勤</h5></a>
            </c:if>

            <c:if test="${left != 0}">
                <a href="" class="button0"><h5>出勤</h5></a>
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <a href="" class="button0"><h5>退勤</h5></a>
            </c:if>

        </div>
        <br><br><br><br>
    </c:param>
</c:import>