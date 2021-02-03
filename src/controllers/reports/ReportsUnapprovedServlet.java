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

@WebServlet("/reports/unapproved")
public class ReportsUnapprovedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public ReportsUnapprovedServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //自分の役職以下の従業員の認証待ち日報を取得
        EntityManager em = DBUtil.createEntityManager();

        Employee employee = (Employee)request.getSession().getAttribute("login_employee");
        int login_employee = employee.getPosition();
        int login_id = employee.getId();
        int unapproved = 0;

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Report> approvals = em.createNamedQuery("getAllUnapproved", Report.class)
                                 .setParameter("unapproved", unapproved)
                                 .setParameter("login_id", login_id)
                                 .setParameter("login_employee", login_employee)
                                 .setFirstResult(15 * (page - 1))
                                 .setMaxResults(15)
                                 .getResultList();

        long approvals_count = (long)em.createNamedQuery("getUnapprovedCount", Long.class)
                                          .setParameter("unapproved", unapproved)
                                          .setParameter("login_employee", login_employee)
                                          .setParameter("login_id", login_id)
                                          .getSingleResult();

        em.close();

        request.setAttribute("approvals", approvals);
        request.setAttribute("approvals_count", approvals_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/unapproved.jsp");
        rd.forward(request, response);

    }


}
