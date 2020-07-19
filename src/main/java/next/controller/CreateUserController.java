package next.controller;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getMethod().equals("GET")) {
            return "/user/form.jsp";
        }
        User user = User.builder()
                .userId(request.getParameter("userId"))
                .password(request.getParameter("password"))
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .build();
        log.debug("User : {}", user);
        DataBase.addUser(user);
        return "redirect:/";
    }
}
