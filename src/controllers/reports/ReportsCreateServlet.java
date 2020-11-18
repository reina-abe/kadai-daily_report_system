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

import models.Employee;
import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;

@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsCreateServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        //_tokenがセッションIDと同じなら
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = new Report();
            //作成者をEmployeeとしてセッションスコープに入れる
            r.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            Date report_date = new Date(System.currentTimeMillis());//インスタンス
            String rd_str = request.getParameter("report_date"); //日報日時を取得してrd_strに代入
            if(rd_str != null && !rd_str.equals("")) {//rd_strに値があれば
                report_date = Date.valueOf(request.getParameter("report_date"));
            } //Stringで受け取った日付を Date 型へ変換。日付欄が未入力の場合、当日の日付を入れる

            //日報日時とタイトル、内容をrに設定
            r.setReport_date(report_date);
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            //インスタンスを生成して、現在時刻で登録日時と更新日時をrに設定
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            //validate(r)メソッドを実行してエラーがあればセッションidとrの内容とエラーをnew.jspに渡す
            List<String> errors = ReportValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);

            //エラーがなければ、DBに登録
            } else {
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                //フラッシュメッセージ
                request.getSession().setAttribute("flush", "登録が完了しました。");
                //indexにリダイレクト
                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}
