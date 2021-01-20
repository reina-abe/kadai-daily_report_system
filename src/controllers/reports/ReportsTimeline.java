package controllers.reports;

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
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/timeline")
public class ReportsTimeline extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsTimeline() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
             page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
             page = 1;
        }

            List<Report> reports = em.createNamedQuery("getFollowReports", Report.class)
                                        .setParameter("login_employee", login_employee)
                                        .setFirstResult(15 * (page - 1))
                                        .setMaxResults(15)
                                        .getResultList();

            /*@NamedQuery(  //フォローした人の日報を取得
                    name = "getFollowReports",
                    query = "SELECT r FROM Report AS r, Follow_employees AS f WHERE r.employee = f.followee AND f.follower = :login_employee ORDER BY r.id DESC"
                )*/

        long reports_count = (long)em.createNamedQuery("getFollowReportsCount", Long.class)
                                        .setParameter("login_employee", login_employee)
                                        .getSingleResult();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/timeline.jsp");
        rd.forward(request, response);
    }

}
