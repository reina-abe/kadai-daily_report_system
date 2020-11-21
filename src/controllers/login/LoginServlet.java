package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    // ログイン画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId()); //セッションidを"_token"名で渡す
        request.setAttribute("hasError", false); //falseを"hasError"で渡す
        if(request.getSession().getAttribute("flush") != null) {
          //セッションに「flush」という名前の値があれば
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            //リクエストスコープに「flush」という名前を付けて保存する
            request.getSession().removeAttribute("flush");
            //セッションスコープにあった「flush」という名前の値は取得済みなので削除しておく
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
        rd.forward(request, response);
    }

    // ログイン処理を実行
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 認証結果を格納する変数
        Boolean check_result = false; //認証結果は偽

        String code = request.getParameter("code"); //"code"の値を取得してcodeに代入
        String plain_pass = request.getParameter("password"); //"password"の値を取得してplain_passに代入

        Employee e = null; //eの初期値

        //codeにもplain_passも入力があったら
        if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("")) {
            EntityManager em = DBUtil.createEntityManager();

            //plain_passをペッパーで暗号化してpasswordに代入する
            String password = EncryptUtil.getPasswordEncrypt(
                    plain_pass,
                    (String)this.getServletContext().getAttribute("pepper")
                    );

            // 社員番号とパスワードが正しいかチェックする
            try {
                //codeとpasswordを条件に入れてcheckLoginCodeAndPasswordクエリを実行、eに入れる
                e = em.createNamedQuery("checkLoginCodeAndPassword", Employee.class)
                      .setParameter("code", code)
                      .setParameter("pass", password) //クエリのpassのところに暗号化したpasswordを入れる
                      .getSingleResult();
            } catch(NoResultException ex) {}

            em.close();

            if(e != null) { //eに値があれば（１件取得してたら）
                check_result = true; //認証結果は真(認証した)
            }
        }


        if(!check_result) { // 認証できなかったらログイン画面に戻る
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("hasError", true);  //hasError名でtrueを渡す
            request.setAttribute("code", code);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
            rd.forward(request, response);

        } else {  // 認証できたらログイン状態にしてトップページへリダイレクト

            request.getSession().setAttribute("login_employee", e);
            /*「セッションスコープに login_employee という名前で従業員情報の
            オブジェクトが保存されている状態」がログインしている状態*/

            request.getSession().setAttribute("flush", "ログインしました。"); //フラッシュメッセージ
            response.sendRedirect(request.getContextPath() + "/"); //最初の画面
        }
    }
}