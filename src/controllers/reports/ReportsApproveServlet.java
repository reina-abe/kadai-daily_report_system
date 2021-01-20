package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approvals;
import models.Employee;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/approve")
public class ReportsApproveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsApproveServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Approvals a = new Approvals();

        a.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        a.setReport(r);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);
        a.setApproval(1);
        r.setApproval(1);
        a.setComment(request.getParameter("comment"));
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "承認しました。");

        response.sendRedirect(request.getContextPath() + "/reports/unapproved");
    }

}
