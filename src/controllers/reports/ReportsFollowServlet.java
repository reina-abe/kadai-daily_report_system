package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow_employees;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/follow")
public class ReportsFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsFollowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //該当日報の従業員をフォローする
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee followee = r.getEmployee();

        Follow_employees fe = new Follow_employees();

        fe.setFollower((Employee)request.getSession().getAttribute("login_employee"));
        fe.setFollowee(followee);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        fe.setCreated_at(currentTime);
        fe.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(fe);
        em.getTransaction().commit();
        request.getSession().setAttribute("flush", "フォローしました。");
        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/following");

    }

}
