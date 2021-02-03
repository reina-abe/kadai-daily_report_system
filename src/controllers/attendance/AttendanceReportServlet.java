package controllers.attendance;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

@WebServlet("/attendance/report")
public class AttendanceReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        java.sql.Date date = java.sql.Date.valueOf(str);

        //月初を取得
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        Date start_date = new Date(cal1.getTimeInMillis());
        //月末を取得
        Calendar cal2 = Calendar.getInstance();
        int max = cal2.getActualMaximum(Calendar.DATE);
        cal2.set(Calendar.DAY_OF_MONTH, max);
        Date end_date = new Date(cal2.getTimeInMillis());

        //１か月の勤怠管理表
        List<Attendance> report = em.createNamedQuery("getAttendReport", Attendance.class)
                .setParameter("employee", employee)
                .setParameter("start_date", start_date)
                .setParameter("end_date", end_date)
                .getResultList();

        //実働時間を計算
        try{
            Attendance a;
            a = em.createNamedQuery("getAttendedEmployee", Attendance.class)
                    .setParameter("employee", employee)
                    .setParameter("date", date)
                    .getSingleResult();

            em.close();

            LocalDateTime start = a.getStart_at().toLocalDateTime();
            LocalDateTime finish = a.getFinish_at().toLocalDateTime();
            Duration duration = Duration.between(start, finish);
            long diff_hours = duration.toHours();
            long diff_minutes = duration.toMinutes() % 60;

            String work_time = diff_hours + ":" + diff_minutes;
            String work_time_break = diff_hours -1 + ":" + diff_minutes;

            //６時間以上なら休憩１時間ひく
            if(diff_hours <= 6){
                request.setAttribute("report", report);
                request.setAttribute("work_time", work_time);

            //６時間未満ならそのまま
            }else{
                request.setAttribute("report", report);
                request.setAttribute("work_time_break", work_time_break);
            }

        } catch (Exception e) {
            request.setAttribute("report", report);
            request.setAttribute("work_time", "00：00");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/report.jsp");
        rd.forward(request, response);
    }

}
