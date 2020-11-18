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

@WebServlet("/employees/create")
public class EmployeesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            //NewServlet→new.jspから取得した_tokenとここで取得する値が同じか
            EntityManager em = DBUtil.createEntityManager();

            Employee e = new Employee(); //インスタンス

            e.setCode(request.getParameter("code"));
            e.setName(request.getParameter("name"));
            e.setPassword(
                EncryptUtil.getPasswordEncrypt(
                    request.getParameter("password"),
                        (String)this.getServletContext().getAttribute("pepper")
                    )
                );
            e.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            e.setCreated_at(currentTime);
            e.setUpdated_at(currentTime);
            e.setDelete_flag(0); //0になっている従業員情報は現役

            List<String> errors = EmployeeValidator.validate(e, true, true);
            //社員番号の重複チェックとパスワードチェックをする
            if(errors.size() > 0) { //エラーがあったら
                em.close(); //emおわり

                request.setAttribute("_token", request.getSession().getId()); //id取得して_token名でjspに渡す
                request.setAttribute("employee", e); //インスタンスeに入ったデータをemployee名で渡す
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
                rd.forward(request, response); //new.jspへ

            } else { //エラーなかったらデータベースに保存
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "登録が完了しました。");//セッションスコープに入れる
                em.close();

                response.sendRedirect(request.getContextPath() + "/employees/index");
            }
        }
    }

}
