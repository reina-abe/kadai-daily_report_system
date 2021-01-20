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

@WebServlet("/reports/first/remand")
public class ReportsFirstRemandServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsFirstRemandServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        int approval = r.getApproval() + 2;
        r.setApproval(approval);

        Approvals a = new Approvals();
        a.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        a.setReport(r);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        a.setCreated_at(currentTime);
        a.setUpdated_at(currentTime);
        a.setApproval(approval);

        em.getTransaction().begin();
        //em.persist(a);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "差戻しました。");

        response.sendRedirect(request.getContextPath() + "/reports/unapproved");
    }

}
