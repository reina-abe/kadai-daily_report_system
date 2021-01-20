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
import models.Follow_employees;
import utils.DBUtil;

//フォローしている人を取得
@WebServlet("/reports/following")
public class ReportsFollowingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsFollowingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee follower = (Employee)request.getSession().getAttribute("login_employee");

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }
        List<Follow_employees> following = em.createNamedQuery("getAllFollowing", Follow_employees.class)
                                                 .setParameter("follower", follower)
                                                 .setFirstResult(15 * (page - 1))
                                                 .setMaxResults(15)
                                                 .getResultList();

        long ｆollowing_count = (long)em.createNamedQuery("getFollowingCount", Long.class)
                                               .setParameter("follower", follower)
                                               .getSingleResult();
        em.close();

        request.setAttribute("following", following);
        request.setAttribute("following_count",ｆollowing_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/following.jsp");
                rd.forward(request, response);

    }


}
