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
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/histry")
public class ReportsApprovedHistry extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsApprovedHistry() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        int report = r.getId();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Approvals> histry = em.createNamedQuery("getApprovedHistry", Approvals.class)
                                       .setParameter("report", report)
                                       .setFirstResult(5 * (page - 1))
                                       .setMaxResults(5)
                                       .getResultList();

        em.close();

        request.setAttribute("histry", histry);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/histry.jsp");
        rd.forward(request, response);
    }


}
