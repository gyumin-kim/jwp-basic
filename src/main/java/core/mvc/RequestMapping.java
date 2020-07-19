package core.mvc;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 모든 요청 URL과 각 URL에 대한 서비스를 담당할 controller를 매핑한다.
 */
public class RequestMapping {

	private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);
	private final Map<String, Controller> mappings = new HashMap<>();

	void initMapping() {
		mappings.put("/", new HomeController());
		mappings.put("/users/form", new ForwardController("/user/form.jsp"));
		mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		mappings.put("/users", new ListUserController());
		mappings.put("/users/login", new LoginController());
		mappings.put("/users/profile", new ProfileController());
		mappings.put("/users/logout", new LogoutController());
		mappings.put("/users/create", new CreateUserController());
		mappings.put("/users/updateForm", new UpdateFormUserController());
		mappings.put("/users/update", new UpdateUserController());

		logger.info("Initialized Request Mapping!");
	}

	public Controller findController(final String url) {
		return mappings.get(url);
	}

	void put(final String url, final Controller controller) {
		mappings.put(url, controller);
	}
}
