package next.web;

import core.db.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet {

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		// TODO: 수정할 사용자 정보를 JSP로 전달 (변수 이름은 user)
//		req.setAttribute("user", DataBase.findUserById());
		RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		// TODO: 개인정보 수정 요청을 받고, 수정을 완료한 뒤 /user/list 서블릿으로 redirect

		resp.sendRedirect("/user/list");
	}
}
