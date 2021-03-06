package controllers.reports;

import java.io.IOException;

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

@WebServlet("/reports/edit")
public class ReportsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsEditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        //1件取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        r.setApproval(0);

        em.getTransaction().begin();
        em.getTransaction().commit();

        em.close();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        //ここで取得したidとDBで取得したidが同じなら
        if(r != null && login_employee.getId() == r.getEmployee().getId()) {

            //取得した１件を"report"、セッションidを_token、idを"report_id"にしてedit.jspに渡す
            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("report_id", r.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
        rd.forward(request, response);
    }

}