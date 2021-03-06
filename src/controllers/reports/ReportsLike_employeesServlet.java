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

import models.Like_employees;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/like_employees")
public class ReportsLike_employeesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsLike_employeesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report report = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e){ }

        //該当の日報にいいねしてる人リスト
        List<Like_employees> like_employees = em.createNamedQuery("getAllLikeEmployees", Like_employees.class)
                                     .setParameter("report", report)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long like_employees_count = (long)em.createNamedQuery("getLikeEmployeesCount", Long.class)
                                       .setParameter("report", report)
                                       .getSingleResult();

        em.close();

        request.setAttribute("like_employees", like_employees);
        request.setAttribute("like_employees_count", like_employees_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/like_employees.jsp");
        rd.forward(request, response);
    }

}
