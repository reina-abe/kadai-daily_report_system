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

@WebServlet("/attendance/attend")
public class AttendanceAttendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceAttendServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Attendance a = new Attendance();

        a.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        //出勤ボタンでレコード追加
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);
        a.setStart_at(currentTime);
        a.setFinish_at(currentTime);

        String work_time = "00：00";
        a.setWork_time(work_time);

        String break_time = "00：00";
        a.setBreak_time(break_time);

        String str = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        java.sql.Date date = java.sql.Date.valueOf(str);
        a.setDate(date);

        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/attendance/button");

    }

}
