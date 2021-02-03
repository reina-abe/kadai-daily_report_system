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
import models.Like_employees;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/like")
public class ReportsLikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsLikeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //該当の日報のいいね数に１加算する
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        int likes = r.getLike_count() + 1; // いいね数+1
        r.setLike_count(likes);

        Like_employees le = new Like_employees();

        le.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        le.setReport(r);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        le.setCreated_at(currentTime);
        le.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(le);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "「いいね」しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }


}
