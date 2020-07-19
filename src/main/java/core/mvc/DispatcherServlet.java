package core.mvc;

import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * front controller 패턴을 적용한 객체.
 * 클라이언트의 모든 요청을 받아 URL에 해당하는 controller로 작업을 위임하고, 실행된 결과 페이지로 이동하는 작업을 담당한다.
 *
 * urlPatterns: Tomcat 서버가 JSP 이외의 모든 요청을 처리하도록 한다.
 *
 * loadOnStartup: Servlet의 인스턴스를 생성하는 시점과 초기화를 담당하는 init() 메서드를 어느 시점에 호출할 것인가를 결정한다.
 * 별도의 설정이 없으면, Servlet container(ex. Tomcat)가 시작을 완료한 후 클라이언트의 요청이 최초로 발생하는 시점에 Servlet 인스턴스의 생성과 초기화가 일어난다.
 * loadOnStartup 설정을 적용하면, Servlet container가 시작하는 시점에 Servlet 인스턴스 생성과 초기화가 진행된다.
 */
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

	private RequestMapping requestMapping;

	@Override
	public void init() {
		requestMapping = new RequestMapping();
		requestMapping.initMapping();
	}

	@Override
	protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException {
		String requestURI = req.getRequestURI();
		logger.debug("Method: {}, Request URI: {}", req.getMethod(), requestURI);

		Controller controller = requestMapping.findController(requestURI);
		try {
			String viewName = controller.execute(req, resp);
			move(viewName, req, resp);
		} catch (Throwable e) {
			logger.error("Exception: {}", e);
			throw new ServletException(e.getMessage());
		}
	}

	private void move(final String viewName, final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
		if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return;
		}
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
		requestDispatcher.forward(req, resp);
	}
}
