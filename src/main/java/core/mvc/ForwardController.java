package core.mvc;

import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ForwardController implements Controller {

	private static final Logger logger = LoggerFactory.getLogger(ForwardController.class);
	private final String forwardUrl;

	public ForwardController(final String forwardUrl) {
		Objects.requireNonNull(forwardUrl, "forwardUrl is null. 이동할 URL을 입력하세요.");
		this.forwardUrl = forwardUrl;
	}

	@Override
	public String execute(final HttpServletRequest request, final HttpServletResponse response) {
		return forwardUrl;
	}
}
