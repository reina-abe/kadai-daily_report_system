package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsUpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) { //セッションID同じなら
            EntityManager em = DBUtil.createEntityManager();
            //report_idの値から１件検索してｒに代入
            Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));
            //日報日時、タイトル、内容、更新日時をｒに設定
            r.setReport_date(Date.valueOf(request.getParameter("report_date")));
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            //ｒをバリデートする
            List<String> errors = ReportValidator.validate(r);
            //エラーがあったら、セッションID("_token")とｒ("report")と"errors"をedit.jspに渡す
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
                rd.forward(request, response);
            //エラーがなかったらDBに保存
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                //フラッシュメッセージ
                request.getSession().setAttribute("flush", "更新が完了しました。");
                //"report_id"は除去
                request.getSession().removeAttribute("report_id");
                //indexにリダイレクト
                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}