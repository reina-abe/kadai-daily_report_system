package controllers.attendance;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

@WebServlet("/attendance/employees")
public class AttendanceEmployeesListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendanceEmployeesListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");
        int login_employee = employee.getPosition();

        List<Employee> employees = em.createNamedQuery("getEmployeesAttendance", Employee.class)
                .setParameter("login_employee", login_employee)
                .getResultList();

        long attendance_count = (long)em.createNamedQuery("getEmployeesAttendanceCount", Long.class)
                .setParameter("login_employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("attendance_count", attendance_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/employees.jsp");
        rd.forward(request, response);
    }

}
