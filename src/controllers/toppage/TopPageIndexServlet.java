package controllers.toppage;

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

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html") //localhost:8080でトップページにアクセスできるようにする
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public TopPageIndexServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //自分の日報一覧を表示する
        EntityManager em = DBUtil.createEntityManager();
        //loginした従業員情報をセッションスコープにいれる
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                                  .setParameter("employee", login_employee)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();
        //自分の日報の全件数を取得
        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                                     .setParameter("employee", login_employee)
                                     .getSingleResult();

        em.close();

        //自分のすべての日報と件数、ページ情報を渡す準備
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);


        //ログイン後のトップページにフラッシュメッセージを表示できるようにする
        if(request.getSession().getAttribute("flush") != null) {//"flush"がセッションスコープにあったら
            //"flush"を取得して"flush"名にセット
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            //取得した"flush"は除去する
            request.getSession().removeAttribute("flush");
        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response); //index.jspにフォワード
    }

}
