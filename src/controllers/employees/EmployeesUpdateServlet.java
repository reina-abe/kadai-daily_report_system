package controllers.employees;

import java.io.IOException;
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
import models.validators.EmployeeValidator;
import utils.DBUtil;
import utils.EncryptUtil;

@WebServlet("/employees/update")
public class EmployeesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesUpdateServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token"); //jspからきた_tokenデータを取得して_tokenに代入
        if(_token != null && _token.equals(request.getSession().getId())) { //ここで新たに取得して同じか確認
            EntityManager em = DBUtil.createEntityManager();

            Employee e = em.find(Employee.class, (Integer)(request.getSession().getAttribute("employee_id")));

            // 現在の値と異なる社員番号が入力されていたら重複チェックを行う指定をする
            Boolean code_duplicate_check = true;

            //DBのコードと取得したコードが同じなら重複チェックはしない
            if(e.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;

            //異なるならtrueのままなので重複チェックする(eは下のList<String> errorsところで使う)
            } else {
                e.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったらパスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;
            String password = request.getParameter("password"); //パスワードの値を取得
            if(password == null || password.equals("")) {  //空なら
                password_check_flag = false;  //パスワードチェックしない
            } else {  //空じゃないなら（入力があったら）
                e.setPassword(  //eに暗号化したパスワードをセット
                        EncryptUtil.getPasswordEncrypt( //暗号化する
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            //氏名、管理権限、更新日時、削除されてないか、の情報を設定する
            e.setName(request.getParameter("name"));
            e.setPosition(Integer.parseInt(request.getParameter("position")));
            e.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));
            e.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            e.setDelete_flag(0); //現役

            //EmployeeValidator.validateメソッドを行う
            List<String> errors = EmployeeValidator.validate(e, code_duplicate_check, password_check_flag);

            //エラーがあったら、セッションIDと社員データ、エラーをedit.jsp渡す
            if(errors.size() > 0) {
                em.close();
                //セッションID、eデータ、エラーをリクエスト
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("employee", e);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/edit.jsp");
                rd.forward(request, response);

            /*エラーがなかったら、DBに登録してflushメッセージを表示、
             employee_idを削除して、indexにリダイレクト*/
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("employee_id");

                response.sendRedirect(request.getContextPath() + "/employees/index");
            }
        }
    }

}