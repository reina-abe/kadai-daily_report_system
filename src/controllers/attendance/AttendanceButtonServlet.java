package controllers.attendance;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

@WebServlet("/attendance/button")
public class AttendanceButtonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceButtonServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        java.sql.Date date = java.sql.Date.valueOf(str);

        //jspで出勤と退勤ボタンの表示分けのため
        long attend = (long)em.createNamedQuery("CheckAttended", Long.class)
                                .setParameter("employee", employee)
                                .setParameter("date", date)
                                .getSingleResult();

        long left = (long)em.createNamedQuery("CheckLeft", Long.class)
                                .setParameter("employee", employee)
                                .setParameter("date", date)
                                .getSingleResult();

        em.close();

        request.setAttribute("date", date);
        request.setAttribute("attend", attend);
        request.setAttribute("left", left);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/button.jsp");
        rd.forward(request, response);

    }

}
