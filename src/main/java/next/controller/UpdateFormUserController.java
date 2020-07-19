package next.controller;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormUserController implements Controller {

	private static final Logger logger = LoggerFactory.getLogger(UpdateFormUserController.class);

	@Override
	public String execute(final HttpServletRequest request, final HttpServletResponse response) {
		String userId = request.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}
		request.setAttribute("user", user);
		return "/user/updateForm.jsp";
	}
}
