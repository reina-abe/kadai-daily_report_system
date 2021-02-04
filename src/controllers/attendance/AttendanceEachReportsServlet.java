package controllers.attendance;

import java.io.IOException;
import java.sql.Date;
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

@WebServlet("/attendance/eachReports")
public class AttendanceEachReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceEachReportsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee employee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        //月初を取得
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        Date start_date = new Date(cal1.getTimeInMillis());
        //月末を取得
        Calendar cal2 = Calendar.getInstance();
        int max = cal2.getActualMaximum(Calendar.DATE);
        cal2.set(Calendar.DAY_OF_MONTH, max);
        Date end_date = new Date(cal2.getTimeInMillis());

        int e = 1;

        //１か月の勤怠管理表
        List<Attendance> report = em.createNamedQuery("getAttendReport", Attendance.class)
                .setParameter("employee", employee)
                .setParameter("start_date", start_date)
                .setParameter("end_date", end_date)
                .getResultList();

        request.setAttribute("report", report);
        request.setAttribute("e", e);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/report.jsp");
        rd.forward(request, response);
    }

}