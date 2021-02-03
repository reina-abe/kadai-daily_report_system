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

import models.Approvals;
import models.Employee;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report report = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //取得した１件を"report"に、セッションidを"_token"にしてshow.jspに渡す
        request.setAttribute("report", report);
        request.setAttribute("_token", request.getSession().getId());

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");

        //該当の日報にすでにいいねしてるかチェック
        long like_check = (long)em.createNamedQuery("checkLikeEmployees", Long.class)
                .setParameter("report", report)
                .setParameter("employee", employee)
                .getSingleResult();

        //該当の従業員をフォローしてるかチェック
        long follow_check = (long)em.createNamedQuery("checkFollowEmployee", Long.class)
                .setParameter("follower", employee)
                .setParameter("followee", report.getEmployee())
                .getSingleResult();

        //承認履歴
        int r = report.getId();

        List<Approvals> histry = em.createNamedQuery("getApprovedHistry", Approvals.class)
                .setParameter("report", r)
                .getResultList();

        Employee e = (Employee)request.getSession().getAttribute("login_employee");
        String login_employee = e.getName();

        em.close();

        request.setAttribute("like_check", like_check);
        request.setAttribute("follow_check", follow_check);
        request.setAttribute("histry", histry);
        request.setAttribute("login_employee", login_employee);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}