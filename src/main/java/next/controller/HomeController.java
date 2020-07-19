package next.controller;

import core.db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
