package controllers.toppage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html") //localhost:8080でトップページにアクセスできるようにする
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public TopPageIndexServlet() {
        super();
    }

    //ログイン後のトップページにフラッシュメッセージを表示できるようにする
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //"flush"がセッションスコープにあったら
        if(request.getSession().getAttribute("flush") != null) {
            //"flush"を取得して"flush"名にセット
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            //取得した"flush"は除去する
            request.getSession().removeAttribute("flush");
        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response); //index.jspにフォワード
    }

}
