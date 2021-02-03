package controllers.attendance;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

@WebServlet("/attendance/leave")
public class AttendanceLeaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceLeaveServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        java.sql.Date date = java.sql.Date.valueOf(str);

        //退勤時刻を記録
        Attendance a;
        a = em.createNamedQuery("getAttendedEmployee", Attendance.class)
                            .setParameter("employee", employee)
                            .setParameter("date", date)
                            .getSingleResult();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setFinish_at(currentTime);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/attendance/button");
    }
}