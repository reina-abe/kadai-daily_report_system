package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow_employees;
import utils.DBUtil;

@WebServlet("/reports/destroy")
public class ReportsFollowDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsFollowDestroy() {
        super();
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
            {
            this.doPost(request, response);
            }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //フォロー解除
        EntityManager em = DBUtil.createEntityManager();

        Follow_employees f = em.find(Follow_employees.class, Integer.parseInt(request.getParameter("id")));

        em.getTransaction().begin();
        em.remove(f);       // データ削除
        em.getTransaction().commit();
        request.getSession().setAttribute("flush", "フォローを解除しました。");
        em.close();

        request.getSession().removeAttribute("id");

        response.sendRedirect(request.getContextPath() + "/reports/following");
    }

}
