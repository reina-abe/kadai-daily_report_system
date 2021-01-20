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

//フォロワーを取得
@WebServlet("/reports/followed")
public class ReportsFollowedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsFollowedServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee followee = (Employee)request.getSession().getAttribute("login_employee");

        //自分をフォローしている人
        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }
        List<Follow_employees> followees = em.createNamedQuery("getAllFollowed", Follow_employees.class) //クラスの配列（要素数を無限に増やせる）
                                                 .setParameter("followee", followee)
                                                 .setFirstResult(15 * (page - 1))
                                                 .setMaxResults(15)
                                                 .getResultList();

        long followees_count = (long)em.createNamedQuery("getAllFollowedCount", Long.class)
                                               .setParameter("followee", followee)
                                               .getSingleResult();

        long[] followChecks = new long[followees.size()]; //データ型の配列（あとから増やせない）
        int i = 0;
        for (Follow_employees followEmployee : followees) { //followerは自分、followeeは相手、自分が相手をフォローしてなかったら０
            long follow_check = (long)em.createNamedQuery("checkFollowEmployee", Long.class)
                                            .setParameter("follower", followee)
                                            .setParameter("followee", followEmployee.getFollower())
                                            .getSingleResult();
            followChecks[i] = follow_check;
            i++;
        }

        em.close();

        request.setAttribute("follow_check", followChecks);

        request.setAttribute("followees", followees);
        request.setAttribute("followees_count",followees_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/followed.jsp");
                rd.forward(request, response);
    }

}
