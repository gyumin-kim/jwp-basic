package next.controller;

import core.db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }
        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
