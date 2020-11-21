package controllers.employees;

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
import utils.DBUtil;

@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //ページネーション
        int page = 1; //初期値
        try{
            page = Integer.parseInt(request.getParameter("page")); //"page"を数値にしてpageに代入
        } catch(NumberFormatException e){ }
        //文字列を数値型に変換しようとしたとき、文字列の形式が正しくない場合にスローされる

        // 最大件数と開始位置を指定してメッセージを取得
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                       .getSingleResult();

        em.close();

        //リクエストスコープに保存する
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") !=null){ //セッションに flush が入っていたら
            request.setAttribute("flush",  request.getSession().getAttribute("flush")); //その情報をJSP側に渡してあげる
            request.getSession().removeAttribute("flush"); //JSPに渡したらもうセッションに残しておく必要はないので削除する
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}
